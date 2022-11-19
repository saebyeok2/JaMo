from flask import Flask, jsonify, request, current_app
from sqlalchemy import create_engine, text
from flask import Flask, jsonify, request

from backend import pretest as p
from backend import theme as t
from machine import ml as m

import argparse
import torch
import torch.nn as nn
import numpy as np
import torchaudio
import librosa
import Levenshtein as Lev
from torch import Tensor
from werkzeug.utils import secure_filename

from kospeech.vocabs.ksponspeech import KsponSpeechVocabulary
from kospeech.data.audio.core import load_audio
from kospeech.models import (
    SpeechTransformer,
    Jasper,
    DeepSpeech2,
    ListenAttendSpell,
    Conformer,
)

def create_app():
    app = Flask(__name__)
    app.config.from_pyfile('config.py')
    database = create_engine(app.config['DB_URL'], encoding='utf-8')
    app.database = database

    #로그인
    @app.route('/login', methods=['POST'])      # 로그인한 회원정보 db에 추가 
    def login():
        user_info = request.json

        if check_overlap_userid(user_info['id']) == 'true' :
            insert_user(user_info)

        return '', 200

    #진단검사
    @app.route('/pretest-result', methods=['GET'])  # 진단검사 회차별 결과 가져오기 
    def pretest_result():
        user_id = request.headers['id'] 
        round = request.headers['round']
        flag = request.headers['flag']

        p.update_Pretest_result(user_id)

        if flag == 0:
            pretest_result = p.get_Pretest_result(user_id, round)
        else :
            pretest_result = p.get_Pretest_result_with_old(user_id, round)
            
        return jsonify(pretest_result)

    @app.route('/solved-prequestion', methods=['POST'])  # 유저가 푼 진단검사 문제 저장
    def insert_solved_prequestion():
        user_info = request.json                        
        prenum = user_info['prenum'] 
        category=p.get_precategory(prenum)                

        if category == "reading" or category == "listening" :
            answer=p.compare_multiplechoice_answer(user_info)
            user_info['answer'] = answer
        elif category == "word":
            answer=p.compare_word_answer(user_info)
            user_info['answer'] = answer
        else : 
            answer=p.compare_speaking_answer(user_info)
            user_info['answer'] = answer
    
        p.insert_Solved_prequestion(user_info)
        return '', 200

    @app.route('/word-prequestion', methods=['GET'])  # 진단검사 word 문제 가져오기
    def prequestion_word():
        user_id=request.headers['id']
        p.delete_Solved_prequestion(user_id)           # 학습 완료 문제 테이블 비우기 (진단검사 시작 시)
        result = p.get_prequestion_word(user_id)
        return jsonify(result)

    @app.route('/speaking-prequestion', methods=['GET'])  # 진단검사 speaking 문제 가져오기
    def prequestion_speaking():
        user_id=request.headers['id']
        result = p.get_prequestion_speaking(user_id)
        return jsonify(result)

    @app.route('/reading-prequestion', methods=['GET'])  # 진단검사 reading 문제 가져오기
    def prequestion_reading():
        user_id = request.headers['id']
        textnum = request.headers['textnum']
        result = p.get_prequestion_reading(user_id, textnum)
        return jsonify(result)

    @app.route('/listening-prequestion', methods=['GET'])  # 진단검사 listening 문제 가져오기
    def prequestion_listening():
        user_id = request.headers['id']
        textnum = request.headers['textnum']
        result = p.get_prequestion_listening(user_id, textnum)
        return jsonify(result)

    @app.route('/pretest-round', methods=['GET'])  # 진단검사 round 가져오기 (input - id)
    def get_pretest_round():
        user_id = request.headers['id']
        round = p.get_Pretest_round(user_id)

        result = {'round': round }
        return jsonify(result)

    #테마
    @app.route('/solved-tquestion', methods=['POST'])  # 유저가 푼 테마문제 저장하기
    def solved_theme_question():
        t_question = request.json
        output = 'false'
        if t.check_category_phonics(t_question['qnum']) :
            answer = t_question['answer']
            answertext, usertext = answer.split('/', maxsplit=1)
            answertext = answertext.replace(' ', ''); answertext = answertext.replace('.', ''); answertext = answertext.replace(',', '')
            usertext = usertext.replace(' ', ''); usertext = usertext.replace('.', ''); usertext = usertext.replace('?', ''); usertext = usertext.replace('!', ''); usertext = usertext.replace(',', '')
            dist = Lev.distance(usertext, answertext)
            length = len(answertext)
            
            if (round(1 - dist/length, 2) * 100) >= 55 :
                t_question['answer'] = 'true'
                output = 'true'
            else :
                t_question['answer'] = 'false'
                output = 'false'

        if t.check_overlap_qnum(t_question) == 'true':
            t.save_theme_question(t_question)

        result = {'output': output }

        return jsonify(result)

    @app.route('/theme-progress', methods=['GET'])  # 유저의 테마별 진행도 및 정답률 가져오기
    def theme_question_progress():
        user_id = request.headers['id']
        theme_id = request.headers['theme_id']
        result = compare_log(user_id)

        if result :                                 # result 값 수정여부 검토 (t/f)
            t.update_Theme_progress(user_id, theme_id)

        Progression_level = t.get_Theme_progress(user_id, theme_id)
        return jsonify(Progression_level)

    #머신러닝
    @app.route('/uploadfile',methods=['GET','POST'])
    def uploadfile():
        if request.method == 'POST':
            f = request.files['audio']   
            filePath = "./audiofolder/"+secure_filename(f.filename) 
            f.save(filePath)        # 파일을 bin/audiofolder에 저장 
            print(filePath)  
            inf_string = m.get_result("/home/ubuntu/22_pf041/JAMO/ML/trained_model/model_ds2.pt", filePath, "cpu")    # 저장된 경로에 있는 파일을 inference해서 결과값을 리턴
            return inf_string

    return app
   
# 로그인 함수 정의
def insert_user(user_info):  # 회원정보 db에 저장
    return current_app.database.execute(text("""
            INSERT INTO Users(
                id,
                email,
                nickname
            ) VALUES (
                :id,
                :email,
                :nickname
            )
    """), user_info).rowcount

def check_overlap_userid(user_id):  # 가입된 회원인지 확인
    result = current_app.database.execute(text("""
            select ifnull(count(*), 0) as output
            from Users
            where id = :user_id
    """), { 'user_id': user_id }).fetchone()
    if result['output'] == 0 :
        output = 'true'
    else :
        output = 'false'

    return output

def compare_log(user_id):  # 회원 로그 비교
    return current_app.database.execute(text("""
        select if (last_learning_date > last_progression_date , true , false)
        from User_log
        where id = :user_id;
    """), {
        'user_id': user_id
    }).fetchone()

if __name__ == '__main__':
    app = create_app()
    app.run(host='0.0.0.0')
from flask import Flask, jsonify, request, current_app
from sqlalchemy import create_engine, text
from flask import Flask, jsonify, request

def update_Theme_progress(user_id, theme_id):        # 테마 진행도 및 정답률 업데이트
    progress1 = update_Theme_progress_rate(user_id, "syllable_count")
    progress2 = update_Theme_progress_rate(user_id, "phoneme_recognition")
    progress3 = update_Theme_progress_rate(user_id, "length_comparison")
    progress4 = update_Theme_progress_rate(user_id, "same_syllable")
    progress5 = update_Theme_progress_rate(user_id, "phonological_count")
    progress6 = update_Theme_progress_rate(user_id, "phoneme_separation")
    progress7 = update_Theme_progress_rate(user_id, "phonics")
    progress8 = update_Theme_progress_rate(user_id, "literacy")
    answer_rate1 = update_Theme_answer_rate(user_id, "syllable_count")
    answer_rate2 = update_Theme_answer_rate(user_id, "phoneme_recognition")
    answer_rate3 = update_Theme_answer_rate(user_id, "length_comparison")
    answer_rate4 = update_Theme_answer_rate(user_id, "same_syllable")
    answer_rate5 = update_Theme_answer_rate(user_id, "phonological_count")
    answer_rate6 = update_Theme_answer_rate(user_id, "phoneme_separation")
    answer_rate7 = update_Theme_answer_rate(user_id, "phonics")
    answer_rate8 = update_Theme_answer_rate(user_id, "literacy")
    current_app.database.execute(text("""
        update Theme_progress SET syllable_count = :progress1, phoneme_recognition = :progress2, length_comparison = :progress3, same_syllable = :progress4, phonological_count = :progress5, phoneme_separation = :progress6, phonics = :progress7, literacy = :progress8,
        syllable_count_rate = :answer_rate1, phoneme_recognition_rate = :answer_rate2, length_comparison_rate = :answer_rate3, same_syllable_rate = :answer_rate4, phonological_count_rate = :answer_rate5, phoneme_separation_rate = :answer_rate6, phonics_rate = :answer_rate7, literacy_rate = :answer_rate8
        where id = :user_id and theme_id = :theme_id;
    """), {
        'progress1' : progress1, 'progress2' : progress2, 'progress3' : progress3, 'progress4' : progress4, 'progress5' : progress5, 'progress6' : progress6, 'progress7' : progress7, 'progress8' : progress8,
        'answer_rate1' : answer_rate1, 'answer_rate2' : answer_rate2, 'answer_rate3' : answer_rate3, 'answer_rate4' : answer_rate4, 'answer_rate5' : answer_rate5, 'answer_rate6' : answer_rate6, 'answer_rate7' : answer_rate7, 'answer_rate8' : answer_rate8,
        'user_id' : user_id, 'theme_id' : theme_id
    })

def update_Theme_progress_rate(user_id, category):      # 테마 진행도 가져오기
    result = current_app.database.execute(text(""" 
        select count(*) AS count
        from Question as q join Question_category as qc on q.cnum = qc.cnum join Solved_question as sq on sq.qnum = q.qnum
        where category = :category and id = :user_id;
    """), {
        'user_id' : user_id, 'category' : category
    }).fetchone()

    count = result['count']
    progress = count / 5 * 100
    
    return progress

def update_Theme_answer_rate(user_id, category):   # 테마 정답률 가져오기
    result = current_app.database.execute(text("""
	    select count(*) AS count
        from Question as q join Question_category as qc on q.cnum = qc.cnum join Solved_question as sq on sq.qnum = q.qnum
        where category = :category and id = :user_id and answer = 'true'
    """), {
        'user_id': user_id , 'category' : category
    }).fetchone()
    solved_count = result['count']
    answer_rate = solved_count / 5 * 100
    
    return answer_rate


def get_Theme_progress(user_id, theme_id):     # 유저의 테마진행도 가져오기
    result = current_app.database.execute(text("""
        SELECT
            syllable_count,
            phoneme_recognition,
            length_comparison,
            same_syllable,
            phonological_count,
            phoneme_separation,
            phonics,
            literacy,
            syllable_count_rate,
            phoneme_recognition_rate,
            length_comparison_rate,
            same_syllable_rate,
            phonological_count_rate,
            phoneme_separation_rate,
            phonics_rate,
            literacy_rate
        FROM Theme_progress
        WHERE id = :user_id and theme_id = :theme_id
    """), { 'user_id' : user_id, 'theme_id' : theme_id}).fetchone()

    return {
        'syllable_count': result['syllable_count'],
        'phoneme_recognition': result['phoneme_recognition'],
        'length_comparison': result['length_comparison'],
        'same_syllable': result['same_syllable'],
        'phonological_count': result['phonological_count'],
        'phoneme_separation': result['phoneme_separation'],
        'phonics' : result['phonics'],
        'literacy' : result['literacy'],
        'syllable_count_rate': result['syllable_count_rate'],
        'phoneme_recognition_rate': result['phoneme_recognition_rate'],
        'length_comparison_rate': result['length_comparison_rate'],
        'same_syllable_rate': result['same_syllable_rate'],
        'phonological_count_rate': result['phonological_count_rate'],
        'phoneme_separation_rate': result['phoneme_separation_rate'],
        'phonics_rate' : result['phonics_rate'],
        'literacy_rate' : result['literacy_rate']
    } if result else None

def save_theme_question(t_question):        # 유저가 푼 테마문제 db에 저장
    current_app.database.execute(text("""
        INSERT INTO Solved_question(
                id,
                qnum,
                answer
            ) VALUES (
                :id,
                :qnum,
                :answer
            )
    """), t_question)

def check_overlap_qnum(t_question):  # 풀었던 테마문제 번호 중복체크
    result = current_app.database.execute(text("""
            select ifnull(count(*), 0) as output
            from Solved_question
            where id = :id and qnum = :qnum;
    """),t_question).fetchone()
    if result['output'] == 0 :
        output = 'true'
    else :
        output = 'false'

    return output

def check_category_phonics(qnum):   # 테마 문제유형 파닉스인지 확인
    result = current_app.database.execute(text("""
        select if(category = "phonics", true , false) as result
        from Question as q inner join Question_category as qc on q.cnum = qc.cnum
        where qnum = :qnum;
    """), {
        'qnum': qnum
    }).fetchone()

    return result['result']   
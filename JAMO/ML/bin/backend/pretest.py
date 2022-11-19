from flask import Flask, jsonify, request, current_app
from sqlalchemy import create_engine, text
from flask import Flask, jsonify, request
import Levenshtein as Lev

def update_Pretest_result(user_id):  # 진단검사 결과 테이블 업데이트 (말하기 유형 추가 예정)
    round = get_Pretest_round(user_id)
    if empty_Solved_prequestion(user_id)!= 0 :
        init_Pretest_result(user_id, round)
        update_pretest_word(user_id, round)
        update_pretest_reading(user_id, round)
        update_pretest_listening(user_id, round)
        update_pretest_speaking(user_id, round)
        delete_Solved_prequestion(user_id) # 학습 완료 문제 테이블 비우기(업데이트 완료 후)

def empty_Solved_prequestion(user_id):
    result = current_app.database.execute(text("""
        select ifnull(count(*), 0) as count
        from Solved_prequestion
        where id = :user_id;
    """), {
        'user_id': user_id
    }).fetchone()
    return result['count']

def get_Pretest_round(user_id):  # id로 진단검사 회차(round) 가져오기
    result = current_app.database.execute(text("""
        SELECT IFNULL(MAX(round), 0)+1 AS round
        FROM Pretest_result
        WHERE id = :user_id
    """), {
        'user_id': user_id
    }).fetchone()
    return result['round']

def init_Pretest_result(user_id, round):     # 진단검사 결과 테이블 초기화
    current_app.database.execute(text("""
        insert into Pretest_result
        values (:user_id, :round, 0, 0, 0, 0, '', '' ,'' ,'')
    """), {
        'user_id': user_id, 'round' : round
    })

def update_pretest_word(user_id, round):  # 진단검사 word 항목 업데이트
    result = current_app.database.execute(text("""
	    select IFNULL(count(*), 0) AS solved_count
	    from Solved_prequestion AS sp inner JOIN Prequestion AS p on sp.prenum = p.prenum
	    where id = :user_id and answer = "true" and pre_category = "word"
    """), {
        'user_id': user_id 
    }).fetchone()
    solved_count = result['solved_count']

    progress = solved_count / 20 * 100

    current_app.database.execute(text("""
	    update Pretest_result set reading = :progress
        where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

    current_app.database.execute(text("""
        update Pretest_result
		SET word_risk = CASE 
			WHEN :progress >= 90 THEN '양호'
			WHEN :progress < 70 THEN '위험'
			ELSE '주의'
		END
        where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

def update_pretest_reading(user_id, round):   # 진단검사 reading 항목 업데이트
    result = current_app.database.execute(text("""
	    select IFNULL(count(*), 0) AS solved_count
	    from Solved_prequestion AS sp inner JOIN Prequestion AS p on sp.prenum = p.prenum
	    where id = :user_id and answer = "true" and pre_category = "reading"
    """), {
        'user_id': user_id 
    }).fetchone()
    solved_count = result['solved_count']

    progress = solved_count / 15 * 100 

    current_app.database.execute(text("""
	    update Pretest_result set reading = :progress
        where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

    current_app.database.execute(text("""
        update Pretest_result
		SET read_risk =
		CASE 
			WHEN :progress >= 80 THEN '양호'
			WHEN :progress < 40 THEN '위험'
		ELSE '주의'
		END
        where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

def update_pretest_listening(user_id, round): # 진단검사 listening 항목 업데이트
    result = current_app.database.execute(text("""
	    select IFNULL(count(*), 0) AS solved_count
	    from Solved_prequestion AS sp inner JOIN Prequestion AS p on sp.prenum = p.prenum
	    where id = :user_id and answer = "true" and pre_category = "listening"
    """), {
        'user_id': user_id 
    }).fetchone()
    solved_count = result['solved_count']

    progress = solved_count / 15 * 100

    current_app.database.execute(text("""
	    update Pretest_result set listening = :progress
        where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

    current_app.database.execute(text("""
        update Pretest_result
		SET listen_risk = CASE 
			WHEN :progress >= 80 THEN '양호'
			WHEN :progress < 40 THEN '위험'
			ELSE '주의'
		END
	    where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

def update_pretest_speaking(user_id, round):        # 진단검사 speaking 항목 업데이트
    result = current_app.database.execute(text("""
        select AVG(answer) AS score
        from Solved_prequestion AS sp inner JOIN Prequestion AS p on sp.prenum = p.prenum
        where id = :user_id and pre_category = "speaking"
    """), {
        'user_id': user_id 
    }).fetchone()
    progress = int(result['score'])
    progress += 20                                  # 말하기 보정치 +20 적용
    current_app.database.execute(text("""
	    update Pretest_result set speaking = :progress
        where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

    current_app.database.execute(text("""
        update Pretest_result
		SET speak_risk = CASE 
			WHEN :progress >= 75 THEN '양호'         
			WHEN :progress < 50 THEN '위험'
			ELSE '주의'
		END
	    where id = :user_id and round = :round
    """), {
        'user_id': user_id, 'round' : round, 'progress' : progress
    })

def delete_Solved_prequestion(user_id):   # 학습 완료 문제 테이블 초기화
    current_app.database.execute(text("""
        delete from Solved_prequestion
        WHERE id = :user_id;
    """), {
        'user_id': user_id
    })

def get_Pretest_result(user_id, round):  # 진단검사 회차별 결과 가져오기
    result = current_app.database.execute(text("""
        SELECT 
            word,
            reading,
            listening,
            speaking,
            word_risk,
            read_risk,
            listen_risk,
            speak_risk
        FROM Pretest_result
        WHERE id = :user_id and round = :round
    """), {
        'user_id' : user_id, 'round' : round 
    }).fetchone()

    return {
        'word': result['word'],
        'reading': result['reading'],
        'listening': result['listening'],
        'speaking': result['speaking'],
        'word_risk': result['word_risk'],
        'read_risk': result['read_risk'],
        'listen_risk': result['listen_risk'],
        'speak_risk': result['speak_risk']
    } if result else None

def get_Pretest_result_with_old(user_id, round):  # 진단검사 최신 and 이전 회차 결과 가져오기
    newresult = current_app.database.execute(text("""
        SELECT 
            word,
            reading,
            listening,
            speaking,
            word_risk,
            read_risk,
            listen_risk,
            speak_risk
        FROM Pretest_result
        WHERE id = :user_id and round = :round
    """), {
        'user_id' : user_id, 'round' : round 
    }).fetchone()

    round = int(round) - 1
    oldresult = current_app.database.execute(text("""
        SELECT 
            word,
            reading,
            listening,
            speaking,
            word_risk,
            read_risk,
            listen_risk,
            speak_risk
        FROM Pretest_result
        WHERE id = :user_id and round = :round
    """), {
        'user_id' : user_id, 'round' : round
    }).fetchone()

    return {
        'word': newresult['word'],
        'reading': newresult['reading'],
        'listening': newresult['listening'],
        'speaking': newresult['speaking'],
        'word_risk': newresult['word_risk'],
        'read_risk': newresult['read_risk'],
        'listen_risk': newresult['listen_risk'],
        'speak_risk': newresult['speak_risk'],
        'oldword': oldresult['word'],
        'oldreading': oldresult['reading'],
        'oldlistening': oldresult['listening'],
        'oldspeaking': oldresult['speaking'],
        'oldword_risk': oldresult['word_risk'],
        'oldread_risk': oldresult['read_risk'],
        'oldlisten_risk': oldresult['listen_risk'],
        'oldspeak_risk': oldresult['speak_risk']
    } if newresult and oldresult else None

def insert_Solved_prequestion(user_info):       #유저가 푼 진단검사 문제 저장
    return current_app.database.execute(text("""
        INSERT INTO Solved_prequestion (
            id,
            prenum,
            answer
        ) VALUES (
            :id,
            :prenum,
            :answer
        )
    """), user_info).rowcount

def get_precategory(prenum):        # 진단검사 문제 유형 가져오기
    result = current_app.database.execute(text("""
        select pre_category
        from Prequestion
        where prenum = :prenum;
    """),{
        'prenum': prenum
    }).fetchone()
    category = result['pre_category']
    return category

def compare_multiplechoice_answer(user_info):       # 진단검사 객관식 문제 정답비교
    result = current_app.database.execute(text("""
        select if(answer = :answer, "true" , "false") as answer
        from Prequestion AS p inner JOIN Multiple_choice AS m on p.mnum = m.mnum
        where prenum = :prenum;
    """), user_info).fetchone()
    return result['answer']

def compare_word_answer(user_info):     # 진단검사 word 문제 정답비교
    result = current_app.database.execute(text("""
        select if(pretext = :answer, "true" , "false") as answer
        from Prequestion
        where prenum = :prenum
    """), user_info).fetchone()
    return result['answer']

def compare_speaking_answer(user_info):     # 진단검사 speaking 문제 정답비교
    result = current_app.database.execute(text("""
        select pretext
        from Prequestion
        where prenum = :prenum
    """), user_info).fetchone()
    answer = result['pretext']
    user_answer = user_info['answer']
    answer = answer.replace(' ', ''); answer = answer.replace('.', ''); answer = answer.replace(',', '')
    user_answer = user_answer.replace(' ', ''); user_answer = user_answer.replace('.', ''); user_answer = user_answer.replace('?', ''); user_answer = user_answer.replace('!', ''); answer = answer.replace(',', '')

    dist = Lev.distance(user_answer, answer)
    length = len(answer)
    if (round(1 - dist/length, 2) * 100) >= 0 :
        score = (round(1 - dist/length, 2) * 100)
    else :
        score = 0
    return score

def get_prequestion_word(user_id):      # 진단검사 word 문제 가져오기
    result = current_app.database.execute(text(""" 
        SELECT pretext
        FROM Prequestion
        WHERE pre_category = 'word' and round = (select ifnull(max(round), 0)+1
											     from Pretest_result
											     where id = :user_id);
    """),{
        'user_id': user_id
    }).fetchmany(20)

    ary=[]
    for i in range(0, 20, 1):
        row=result[i]
        ary.append(row['pretext'])


    return {
        'word1' : ary[0], 'word2' : ary[1], 'word3' : ary[2], 'word4' : ary[3], 'word5' : ary[4], 'word6' : ary[5], 'word7' : ary[6], 'word8' : ary[7], 'word9' : ary[8], 'word10' : ary[9],
        'word11' : ary[10], 'word12' : ary[11], 'word13' : ary[12], 'word14' : ary[13], 'word15' : ary[14], 'word16' : ary[15], 'word17' : ary[16], 'word18' : ary[17], 'word19' : ary[18], 'word20' : ary[19] 
    } if result else None

def get_prequestion_speaking(user_id):      # 진단검사 speaking 문제 가져오기
    result = current_app.database.execute(text("""
        SELECT pretext 
        FROM Prequestion
        WHERE pre_category = 'speaking' and round = (select ifnull(max(round), 0)+1
                                                     from Pretest_result 
                                                     where id = :user_id);
    """),{
        'user_id': user_id
    }).fetchmany(3)
    row1=result[0]; row2=result[1]; row3=result[2]
    return {
        'speaking1' : row1['pretext'],
        'speaking2' : row2['pretext'],
        'speaking3' : row3['pretext']
    } if result else None

def get_prequestion_reading(user_id, textnum):      #진단검사 reading 문제 가져오기
    textnum = int(textnum) * 5
    round = get_Pretest_round(user_id)
    result = current_app.database.execute(text("""
        SELECT pretext, question, e1, e2, e3, e4
        FROM Prequestion AS p inner join Multiple_choice AS m on p.mnum = m.mnum
        WHERE pre_category = 'reading' and round = :round limit 5 offset :textnum;
    """),{
        'textnum' : textnum,
        'round' : round
        }).fetchmany(5)

    pretext = result[0]     # 지문 정보
    eq = [[0 for j in range(5)] for i in range(5)]      #문제당 4개의 보기

    for i in range (0,5,1):
        row = result[i]
        eq[i][0] = row['question']
        eq[i][1] = row['e1']
        eq[i][2] = row['e2']
        eq[i][3] = row['e3']
        eq[i][4] = row['e4']

    return{
        'text' : pretext['pretext'],
        'question1' : eq[0][0],
        'e11' : eq[0][1], 'e12' : eq[0][2], 'e13' : eq[0][3], 'e14' : eq[0][4],
        'question2' : eq[1][0],
        'e21' : eq[1][1], 'e22' : eq[1][2], 'e23' : eq[1][3], 'e24' : eq[1][4],
        'question3' : eq[2][0],
        'e31' : eq[2][1], 'e32' : eq[2][2], 'e33' : eq[2][3], 'e34' : eq[2][4],
        'question4' : eq[3][0],
        'e41' : eq[3][1], 'e42' : eq[3][2], 'e43' : eq[3][3], 'e44' : eq[3][4],
        'question5' : eq[4][0],
        'e51' : eq[4][1], 'e52' : eq[4][2], 'e53' : eq[4][3], 'e54' : eq[4][4]
    } if result else None

def get_prequestion_listening(user_id, textnum):        #진단검사 listening 문제 가져오기
    textnum = int(textnum) * 5
    round = get_Pretest_round(user_id)
    result = current_app.database.execute(text("""
        SELECT pretext, question, e1, e2, e3, e4
        FROM Prequestion AS p inner join Multiple_choice AS m on p.mnum = m.mnum
        WHERE pre_category = 'listening' and round = :round limit 5 offset :textnum;
    """),{
        'textnum' : textnum,
        'round' : round
        }).fetchmany(5)

    pretext = result[0]     # 지문 정보
    eq = [[0 for j in range(5)] for i in range(5)]      #문제당 4개의 보기

    for i in range (0,5,1):
        row = result[i]
        eq[i][0] = row['question']
        eq[i][1] = row['e1']
        eq[i][2] = row['e2']
        eq[i][3] = row['e3']
        eq[i][4] = row['e4']

    return{
        'text' : pretext['pretext'],
        'question1' : eq[0][0],
        'e11' : eq[0][1], 'e12' : eq[0][2], 'e13' : eq[0][3], 'e14' : eq[0][4],
        'question2' : eq[1][0],
        'e21' : eq[1][1], 'e22' : eq[1][2], 'e23' : eq[1][3], 'e24' : eq[1][4],
        'question3' : eq[2][0],
        'e31' : eq[2][1], 'e32' : eq[2][2], 'e33' : eq[2][3], 'e34' : eq[2][4],
        'question4' : eq[3][0],
        'e41' : eq[3][1], 'e42' : eq[3][2], 'e43' : eq[3][3], 'e44' : eq[3][4],
        'question5' : eq[4][0],
        'e51' : eq[4][1], 'e52' : eq[4][2], 'e53' : eq[4][3], 'e54' : eq[4][4]
    } if result else None
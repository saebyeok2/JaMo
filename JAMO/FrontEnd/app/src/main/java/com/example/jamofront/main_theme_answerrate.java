package com.example.jamofront;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.example.jamofront.Littleprince.Little_Prince_AnswerRate_Activity;
import com.example.jamofront.Littleprince.Little_Prince_CountTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_IncludeTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_LengthTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_NumberTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_PhanicsTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_ReadingTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_SamefindTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_SelectchooseTutorial_Activity;
import com.example.jamofront.PeterPan.PeterPan_AnswerRate_Acitivty;
import com.example.jamofront.RedHat.RedHat_Answerrate;

public class main_theme_answerrate extends AppCompatActivity {

    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_overcome);


        Button type3 = findViewById(R.id.cbtn);
        Button type4 = findViewById(R.id.dbtn);
        Button type5 = findViewById(R.id.ebtn);


        //어린왕자 정답률
        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_AnswerRate_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //피터팬 정답률
        type4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PeterPan_AnswerRate_Acitivty.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        type5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_Answerrate.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //미운아기오리 정답률  ->아직


    }

    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;


        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            // 훈련을 종료하는 코드

        }
        else
        {
            backPressedTime = tempTime;
            Intent intent = new Intent(getApplicationContext(), Home_main.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }
}

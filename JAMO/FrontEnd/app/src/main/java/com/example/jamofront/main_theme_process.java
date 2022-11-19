package com.example.jamofront;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Littleprince.Little_Prince_NumberTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_Progress_Activity;
import com.example.jamofront.PeterPan.PeterPan_Progress_Activity;
import com.example.jamofront.RedHat.RedHat_progress;

public class main_theme_process extends AppCompatActivity {
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_overcome);

        Button type3 = findViewById(R.id.cbtn);
        Button type4 = findViewById(R.id.dbtn);
        Button type5 = findViewById(R.id.ebtn);


        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_Progress_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PeterPan_Progress_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //헨젤과 그레텔 진행도 미정
        type5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_progress.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //미운 아기 오리 진행도 미정


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


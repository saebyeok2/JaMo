package com.example.jamofront;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Littleprince.Little_Prince_CountTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_IncludeTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_LengthTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_NumberTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_PhanicsTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_ReadingTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_SamefindTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_SelectchooseTutorial_Activity;

public class select_type1 extends AppCompatActivity {

    private long backPressedTime = 0;
    private Dialog dialog2;
    private final long FINISH_INTERVAL_TIME = 2000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_type1);

        Button type1 = findViewById(R.id.button1);
        Button type2 = findViewById(R.id.button2);
        Button type3 = findViewById(R.id.button3);
        Button type4 = findViewById(R.id.button4);
        Button type5 = findViewById(R.id.button5);
        Button type6 = findViewById(R.id.button6);
        Button type7 = findViewById(R.id.button7);
        Button type8 = findViewById(R.id.button8);

        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_CountTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_IncludeTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_LengthTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_SamefindTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_NumberTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_SelectchooseTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_PhanicsTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Little_Prince_ReadingTutorial_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });


    }

    public void showDialogExit(Intent intent)
    {
        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.dialog_exit);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yes_btn = (Button) dialog2.findViewById(R.id.yes_btn);
        Button no_btn = (Button) dialog2.findViewById(R.id.no_btn);

        yes_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog2.dismiss();
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog2.dismiss();
            }
        });
        dialog2.show();
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
            Intent intent = new Intent(getApplicationContext(), select_theme1.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }
}

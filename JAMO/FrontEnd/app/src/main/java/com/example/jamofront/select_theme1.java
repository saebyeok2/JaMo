package com.example.jamofront;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class select_theme1 extends AppCompatActivity {
    private int typenum = 0;
    private ImageButton selecttheme1;
    private ImageButton next1button;
    private ImageButton back1button;
    private TextView themename;
    private long backPressedTime = 0;
    private Dialog dialog2;
    private final long FINISH_INTERVAL_TIME = 2000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_theme1);

        next1button = findViewById(R.id.nextbtn1);
        back1button=findViewById(R.id.backbtn1);
        selecttheme1=findViewById(R.id.themeButton1);
        themename=findViewById(R.id.text1);

        next1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typenum == 2)
                {
                    typenum=0;
                }
                else
                {
                    typenum++;
                }
                setimage();


            }
        });
        back1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typenum == 0)
                {
                    typenum=2;
                }
                else
                {
                    typenum--;
                }
                setimage();

            }
        });
        //마지막 동화 선택 시 intent 로 연결
        selecttheme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typenum == 0) {
                    Intent intent = new Intent(getApplicationContext(), select_type1.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                if(typenum == 1){
                    Intent intent = new Intent(getApplicationContext(), select_peterpantype.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                if(typenum==2){
                    Intent intent = new Intent(getApplicationContext(), Select_redhoodtype.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            }
        });

    }
    private void setimage(){

        if(typenum == 0)
        {
            selecttheme1.setImageResource(R.drawable.theme1);
            themename.setText("어린왕자");
        }
        else if(typenum == 1)
        {
            selecttheme1.setImageResource(R.drawable.theme2);
            themename.setText("피터팬");

        }
        else if(typenum == 2)
        {
            selecttheme1.setImageResource(R.drawable.theme3);
            themename.setText("빨간모자");
        }
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
            Intent intent = new Intent(getApplicationContext(), Home_main.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }

}
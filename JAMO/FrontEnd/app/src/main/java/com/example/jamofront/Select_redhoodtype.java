package com.example.jamofront;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.RedHat.RedHat_count1_Activity;
import com.example.jamofront.RedHat.RedHat_include1_Activity;
import com.example.jamofront.RedHat.RedHat_length1_Activity;
import com.example.jamofront.RedHat.RedHat_number1_Activity;
import com.example.jamofront.RedHat.RedHat_phonics1_Activity;
import com.example.jamofront.RedHat.RedHat_reading1_Activity;
import com.example.jamofront.RedHat.RedHat_samefind1_Activity;
import com.example.jamofront.RedHat.RedHat_selectchoose1_Activity;

public class Select_redhoodtype extends AppCompatActivity {

    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private Dialog dialog2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_redhood_type);
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
                Intent intent = new Intent(getApplicationContext(), RedHat_count1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_include1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_length1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_samefind1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_number1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_selectchoose1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_phonics1_Activity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        type8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RedHat_reading1_Activity.class);
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

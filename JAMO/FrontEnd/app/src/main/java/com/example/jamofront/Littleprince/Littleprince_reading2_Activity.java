package com.example.jamofront.Littleprince;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.R;
import com.example.jamofront.Task.SubmitTrTasek;
import com.example.jamofront.Userinfo;
import com.example.jamofront.WordTest;
import com.example.jamofront.databinding.LittleprinceReading2Binding;
import com.example.jamofront.select_type1;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Littleprince_reading2_Activity extends AppCompatActivity implements View.OnClickListener {
    private SubmitTrTasek httpConn2 = SubmitTrTasek.getInstance();
    private String URl = "http://3.35.123.120:5000/solved-tquestion" ;
    private ImageView popupMenuBtn;
    private LittleprinceReading2Binding binding;
    private int count = 0;

    public boolean IS_MENU = false;
    private PopupWindow mPopupWindow;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private Dialog dialog;
    private Dialog dialog2;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.littleprince_reading2);
        binding = LittleprinceReading2Binding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        binding.answer1Btn.setOnClickListener(this);
        binding.answer2Btn.setOnClickListener(this);
        binding.answer3Btn.setOnClickListener(this);
        binding.answer4Btn.setOnClickListener(this);

        popupMenuBtn = findViewById(R.id.toolbar_menu_btn);
        popupMenuBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                IS_MENU = true;
                View popupView = getLayoutInflater().inflate(R.layout.popup, null);
                mPopupWindow = new PopupWindow(popupView, 700, 1700);
                mPopupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
                mPopupWindow.showAsDropDown(popupMenuBtn, -100, -100);

                Button progress_btn = (Button) popupView.findViewById(R.id.progress_btn);
                Button correctRate_btn = (Button) popupView.findViewById(R.id.correctRate_btn);
                Button doTest_btn = (Button) popupView.findViewById(R.id.doTest_btn);

                progress_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        // 훈련 진행도
                        Intent intent = new Intent(getApplicationContext(), Little_Prince_Progress_Activity.class);
                        startActivity(intent);
                    }
                });

                correctRate_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        // 훈련 정답률
                        Intent intent = new Intent(getApplicationContext(), Little_Prince_AnswerRate_Activity.class);
                        startActivity(intent);
                    }
                });

                doTest_btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        // 진단검사하기
                        Intent intent = new Intent(getApplicationContext(), WordTest.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    // dialog 함수
    // dialog 함수 overridePendingTransition(0,0);
    public void showDialog(String textView, Boolean isCorrect, Intent intent)
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_correct);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView answer_tv = (TextView) dialog.findViewById(R.id.isAnswer_tv);
        answer_tv.setText(textView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                if(isCorrect == true) // 정답이면 3초기다렸다가 intent
                {
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                else dialog.dismiss(); // 오답이면 3초 후 dismiss
            }
        };
        timer.schedule(timerTask, 1500); // delay 1.5초
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

        if(IS_MENU == true)
        {
            mPopupWindow.dismiss();
            IS_MENU = false;
        }
        else if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            // 훈련을 종료하는 코드

        }
        else
        {
            backPressedTime = tempTime;
            Intent intent = new Intent(getApplicationContext(), select_type1.class);
            showDialogExit(intent);
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.answer1_btn:
            case R.id.answer3_btn:
            case R.id.answer4_btn: {
                count ++;
                showDialog("오답입니다. 다시 시도해주세요.", false, null);
                if(count==1)
                {
                    sendData(Userinfo.id,"37","false",URl);
                }

                break;
            }
            case R.id.answer2_btn:
            {
                count++;
                if(count ==1)
                {
                    sendData(Userinfo.id,"37","true",URl);
                }
                Intent intent = new Intent(getApplicationContext(), Littleprince_reading3_Activity.class);
                showDialog("정답입니다. 다음 문제로 넘어갑니다.", true, intent); // 정답 Dialog
                break;
            }
        }

    }

    private void sendData(String id, String prenum, String answer, String URl) {
        new Thread() {
            public void run() {
                httpConn2.submitbody(id, prenum, answer, URl, callback2);
            }
        }.start();
    }

    private final Callback callback2 = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("TAG", "서버에서 응답한 Body:" + body);
        }
    };
}

package com.example.jamofront;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Littleprince.Little_Prince_CountTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_IncludeTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_LengthTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_NumberTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_PhanicsTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_ReadingTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_SamefindTutorial_Activity;
import com.example.jamofront.Littleprince.Little_Prince_SelectchooseTutorial_Activity;
import com.example.jamofront.Task.GetRoundTask;
import com.example.jamofront.line.Test_Result;
import com.example.jamofront.line.Test_Result_homeVer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Home_main extends AppCompatActivity {
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    Dialog dialog;
    private int round;
    private Button type1;
    private Button type2;
    private Button type3;
    private Button type4;
    private Button type5;
    private Button type6;
    private String url2 = "http://3.35.123.120:5000/pretest-round";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

        type1 = findViewById(R.id.abtn);
        type2 = findViewById(R.id.bbtn);
        type3 = findViewById(R.id.cbtn);
        type4 = findViewById(R.id.dbtn);
        type5 = findViewById(R.id.ebtn);
        type6 = findViewById(R.id.fbtn);

        //훈련 하기
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), select_theme1.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //진단 검사
        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordTest.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //훈련 테마별 정답률
        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_theme_answerrate.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //훈련 테마별 진행도
        type4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_theme_process.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //진단검사 유형별 결과
        type5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Test_Result_homeVer.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //진단검사 유형별 향상도
        type6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyenhenceHome.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });


    }

    public void showDialog(String textView, Boolean isCorrect, Intent intent)
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_test);
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
                    overridePendingTransition(0,0);
                }
                else dialog.dismiss(); // 오답이면 3초 후 dismiss
            }
        };
        timer.schedule(timerTask, 1500);
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

        }
    }
/*
    private void getRound(String url) {
        new Thread() {
            public void run() {
                httpConn3.getround(callback3, url);
            }
        }.start();

    }

    private final okhttp3.Callback callback3 = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                JSONObject jsonObject = new JSONObject(response.body().string());
                round = jsonObject.getInt("round");
                Log.d("round",""+round);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (round < 2 )
                        {
                            type5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDialog("진단검사를 먼저 시행해 주세요!!", false, null); //  Dialog

                                }
                            });
                        }
                        if (round < 3)
                        {
                            type6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDialog("진단검사를 먼저 시행해 주세요!!", false, null);
                                }
                            });
                        }


                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

 */

}

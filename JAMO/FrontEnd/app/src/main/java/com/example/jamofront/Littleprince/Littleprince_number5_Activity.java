package com.example.jamofront.Littleprince;

import static android.speech.tts.TextToSpeech.ERROR;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
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
import com.example.jamofront.Task.ClovaTTSTask;
import com.example.jamofront.Task.SubmitTrTasek;
import com.example.jamofront.Userinfo;
import com.example.jamofront.WordTest;
import com.example.jamofront.databinding.LittleprinceNumber5Binding;
import com.example.jamofront.select_type1;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Littleprince_number5_Activity extends AppCompatActivity implements View.OnClickListener {
    private LittleprinceNumber5Binding binding;
    private SubmitTrTasek httpConn2 = SubmitTrTasek.getInstance();
    private String URl = "http://3.35.123.120:5000/solved-tquestion" ;
    private String Answertext [] = {"비행기","송아지","솜사탕","무지개"};
    private int count = 0;
    private TextToSpeech tts;
    private ImageView popupMenuBtn;

    ClovaTTSTask clovaTTSTask;
    MediaPlayer mediaPlayer;

    private Dialog dialog;
    private Dialog dialog2;
    public boolean IS_MENU = false;
    private PopupWindow mPopupWindow;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.littleprince_number5);
        binding = LittleprinceNumber5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // 4번 나비가 정답이므로 4번뺴고는 다 틀렸다고 처리해야한다.

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    tts.setLanguage(Locale.KOREAN); // 언어 설정
                }
            }
        });

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

        binding.answer1Btn.setOnClickListener(this);
        binding.answer2Btn.setOnClickListener(this);
        binding.answer3Btn.setOnClickListener(this);
        binding.answer4Btn.setOnClickListener(this);
        binding.sound1Btn.setOnClickListener(this);
        binding.sound2Btn.setOnClickListener(this);
        binding.sound3Btn.setOnClickListener(this);
        binding.sound4Btn.setOnClickListener(this);
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
        switch(v.getId())
        {
            case R.id.answer1_btn:
            case R.id.answer2_btn:
            case R.id.answer4_btn: {
                count += 1;
                if (count == 1) {
                    // Post 하기위한 조건문 들어갈 자리
                    sendData(Userinfo.id,"25","false",URl);
                }
                showDialog("오답입니다. 다시 시도해주세요.", false, null);
                break;
            }
            case R.id.answer3_btn:
            {
                count += 1;
                if (count == 1) {
                    sendData(Userinfo.id,"25","true",URl);
                    // Post 하기위한 조건문 들어갈 자리

                }
                Intent intent = new Intent(getApplicationContext(), select_type1.class);
                showDialog("정답입니다.이제 다른 유형을 선택해보세요!", true, intent); // 정답 Dialog
                break;
            }
            case R.id.sound1_btn:
            {
                clovaTTSTask = new ClovaTTSTask(Answertext[0]);
                Thread tts = new Thread(clovaTTSTask);
                tts.start();
                try
                {
                    tts.join();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory() +"/" +Environment.DIRECTORY_DOWNLOADS + "/clova.mp3"));
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        mediaPlayer.release();
                    }
                });
                break;
            }
            case R.id.sound2_btn:
            {
                clovaTTSTask = new ClovaTTSTask(Answertext[1]);
                Thread tts = new Thread(clovaTTSTask);
                tts.start();
                try
                {
                    tts.join();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory() +"/" +Environment.DIRECTORY_DOWNLOADS + "/clova.mp3"));
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        mediaPlayer.release();
                    }
                });
                break;
            }
            case R.id.sound3_btn:
            {
                clovaTTSTask = new ClovaTTSTask(Answertext[2]);
                Thread tts = new Thread(clovaTTSTask);
                tts.start();
                try
                {
                    tts.join();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory() +"/" +Environment.DIRECTORY_DOWNLOADS + "/clova.mp3"));
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        mediaPlayer.release();
                    }
                });
                break;
            }
            case R.id.sound4_btn:
            {
                clovaTTSTask = new ClovaTTSTask(Answertext[3]);
                Thread tts = new Thread(clovaTTSTask);
                tts.start();
                try
                {
                    tts.join();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory() +"/" +Environment.DIRECTORY_DOWNLOADS + "/clova.mp3"));
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        mediaPlayer.release();
                    }
                });
                break;
            }
        }

    }
    private void sendData(String id ,String qnum, String answer, String URl) {
        new Thread() {
            public void run() {
                httpConn2.submitbody(id,qnum, answer, URl, callback2);
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

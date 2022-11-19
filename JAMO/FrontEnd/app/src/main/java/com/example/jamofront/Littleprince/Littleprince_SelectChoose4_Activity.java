package com.example.jamofront.Littleprince;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.R;
import com.example.jamofront.Task.ClovaTTSTask;
import com.example.jamofront.Task.SubmitTrTasek;
import com.example.jamofront.Userinfo;
import com.example.jamofront.WordTest;
import com.example.jamofront.databinding.LittleprinceSelectChoose4Binding;
import com.example.jamofront.select_type1;

import android.speech.tts.TextToSpeech;


import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Littleprince_SelectChoose4_Activity extends AppCompatActivity implements View.OnClickListener {
    private LittleprinceSelectChoose4Binding binding;
    private SubmitTrTasek httpConn2 = SubmitTrTasek.getInstance();
    private int count = 0;
    private String URl = "http://3.35.123.120:5000/solved-tquestion";
    private String ProblemAnswer[] = {"ㄱ", "ㅣ", "ㅈ","ㅓ","ㄱ"};
    private String UserAnswer[] = new String[5];
    private int Answers = 0;

    public boolean IS_MENU = false;
    private PopupWindow mPopupWindow;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private int BtnNum[] = new int[14];
    private String ProblemText = "기적";
    ClovaTTSTask clovaTTSTask;
    MediaPlayer mediaPlayer;
    private ImageView popupMenuBtn;
    private Dialog dialog;
    private Dialog dialog2;
    String Text;
    int checknum;
    int ButtonNum;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.littleprince_select_choose4);
        binding = LittleprinceSelectChoose4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

        binding.select1Btn.setOnClickListener(this);
        binding.select2Btn.setOnClickListener(this);
        binding.select3Btn.setOnClickListener(this);
        binding.select4Btn.setOnClickListener(this);
        binding.select5Btn.setOnClickListener(this);
        binding.select6Btn.setOnClickListener(this);
        binding.select7Btn.setOnClickListener(this);
        binding.select8Btn.setOnClickListener(this);
        binding.select9Btn.setOnClickListener(this);
        binding.select10Btn.setOnClickListener(this);
        binding.select11Btn.setOnClickListener(this);
        binding.select12Btn.setOnClickListener(this);
        binding.select13Btn.setOnClickListener(this);
        binding.select14Btn.setOnClickListener(this);
        binding.backbutton.setOnClickListener(this);
        binding.endBtn.setOnClickListener(this);
        binding.scSound.setOnClickListener(this);
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
    //버튼이 눌렸을떄 작동하는 함수
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.select1_Btn:
            {
                Text=binding.select1Btn.getText().toString(); //텍스트변수를 버튼의 택스트를 통해 받아온다.
                BtnNum[Answers]=1;
                inputAnswerText();
                binding.select1Btn.setVisibility(View.INVISIBLE);
                break;
            }

            case R.id.select2_Btn:
            {
                Text=binding.select2Btn.getText().toString();
                BtnNum[Answers]=2;
                inputAnswerText();
                binding.select2Btn.setVisibility(View.INVISIBLE);
                break;
            }

            case R.id.select3_btn:
            {
                Text=binding.select3Btn.getText().toString();
                BtnNum[Answers]=3;
                inputAnswerText();
                binding.select3Btn.setVisibility(View.INVISIBLE);
                break;
            }

            case R.id.select4_btn:
            {
                Text=binding.select4Btn.getText().toString();
                BtnNum[Answers]=4;
                inputAnswerText();
                binding.select4Btn.setVisibility(View.INVISIBLE);
                break;
            }

            case R.id.select5_btn:
            {
                Text=binding.select5Btn.getText().toString();
                BtnNum[Answers]=5;
                inputAnswerText();
                binding.select5Btn.setVisibility(View.INVISIBLE);
                break;
            }

            case R.id.select6_btn:
            {
                Text=binding.select6Btn.getText().toString();
                BtnNum[Answers]=6;
                inputAnswerText();
                binding.select6Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select7_btn:
            {
                Text=binding.select7Btn.getText().toString();
                BtnNum[Answers]=7;
                inputAnswerText();
                binding.select7Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select8_btn:
            {
                Text=binding.select8Btn.getText().toString();
                BtnNum[Answers]=8;
                inputAnswerText();
                binding.select8Btn.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.select9_btn:
            {
                Text=binding.select9Btn.getText().toString();
                BtnNum[Answers]=9;
                inputAnswerText();
                binding.select9Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select10_btn:
            {
                Text=binding.select10Btn.getText().toString();
                BtnNum[Answers]=10;
                inputAnswerText();
                binding.select10Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select11_btn:
            {
                Text=binding.select11Btn.getText().toString();
                BtnNum[Answers]=11;
                inputAnswerText();
                binding.select11Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select12_btn:
            {
                Text=binding.select12Btn.getText().toString();
                BtnNum[Answers]=12;
                inputAnswerText();
                binding.select12Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select13_btn:
            {
                Text=binding.select13Btn.getText().toString();
                BtnNum[Answers]=13;
                inputAnswerText();
                binding.select13Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.select14_btn:
            {
                Text=binding.select14Btn.getText().toString();
                BtnNum[Answers]=14;
                inputAnswerText();
                binding.select14Btn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.sc_sound:
            {
                clovaTTSTask = new ClovaTTSTask(ProblemText);
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
            case R.id.end_btn:
            {
                CheckAnswer();
                break;
            }
            case R.id.backbutton:
            {
                if(Answers == 1)
                {
                    VisibleBtn();
                    binding.answer1Btn.setText("");
                    UserAnswer[0]=null;
                    Answers--;
                }
                else if(Answers == 2)
                {
                    VisibleBtn();
                    binding.answer2Btn.setText("");
                    UserAnswer[1]=null;
                    Answers--;
                }
                else if (Answers == 3)
                {
                    VisibleBtn();
                    binding.answer3Btn.setText("");
                    UserAnswer[2]=null;
                    Answers--;
                }
                else if (Answers == 4)
                {
                    VisibleBtn();
                    binding.answer4Btn.setText("");
                    UserAnswer[3]=null;
                    Answers--;
                }
                else if (Answers == 5)
                {
                    VisibleBtn();
                    binding.answer5Btn.setText("");
                    UserAnswer[4]=null;
                    Answers--;
                }
            }
        }
    }
    // 버튼을 누르면 택스트 뷰에 표시해주는 함수 (Answers)값을 받아서 몇번쨰 글자에 썼는지 확인
    public void inputAnswerText()
    {
        switch (Answers)
        {
            case 0: {
                binding.answer1Btn.setText(Text);
                UserAnswer[0]=Text;
                Answers++;
                break;
            }
            case 1: {
                binding.answer2Btn.setText(Text);
                UserAnswer[1]=Text;
                Answers++;
                break;
            }
            case 2: {
                binding.answer3Btn.setText(Text);
                UserAnswer[2]=Text;
                Answers++;
                break;
            }
            case 3: {
                binding.answer4Btn.setText(Text);
                UserAnswer[3]=Text;
                Answers++;
                break;
            }
            case 4: {
                binding.answer5Btn.setText(Text);
                UserAnswer[4]=Text;
                Answers++;
                break;
            }
            case 5:{
                Toast.makeText(this.getApplicationContext(),"입력할 수 없습니다. 지우고 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void VisibleBtn()
    {
        if(Answers >0)
        {
            if(BtnNum[Answers-1]==1)
            {
                binding.select1Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==2)
            {
                binding.select2Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==3)
            {
                binding.select3Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==4)
            {
                binding.select4Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==5)
            {
                binding.select5Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==6)
            {
                binding.select6Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==7)
            {
                binding.select7Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==8)
            {
                binding.select8Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==9)
            {
                binding.select9Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==10)
            {
                binding.select10Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==11)
            {
                binding.select11Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==12)
            {
                binding.select12Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==13)
            {
                binding.select13Btn.setVisibility(View.VISIBLE);
            }
            else if(BtnNum[Answers-1]==14)
            {
                binding.select14Btn.setVisibility(View.VISIBLE);
            }
        }
    }
    // 답을 체크해주는 함수 유저가 입력한 답과 문제답을 비교해주는 함수 만약 틀렸다면 유저답배열을 초기화 후 텍스트뷰도 초기화한다.
    private void CheckAnswer()
    {
        count ++;
        if(Arrays.equals(UserAnswer, ProblemAnswer))
        {

            Intent intent = new Intent(getApplicationContext(), Littleprince_SelectChoose5_Activity.class);
            if (count == 1) {
                sendData(Userinfo.id,"29","true", URl);
            }
            showDialog("정답입니다. 다음문제로 넘어갑니다.", true, intent);
        }
        else if(!Arrays.equals(UserAnswer, ProblemAnswer)){
            if (count == 1) {
                sendData(Userinfo.id,"29","false", URl);
            }
            showDialog("오답입니다. 다시 시도해주세요.", false, null);
            binding.answer1Btn.setText("");
            binding.answer2Btn.setText("");
            binding.answer3Btn.setText("");
            binding.answer4Btn.setText("");
            binding.answer5Btn.setText("");
            for(int i = 0 ; i<Answers; Answers--)
            {
                VisibleBtn();
            }
            for(int i=0; i<4; i++)
            {
                UserAnswer[i]=null;
            }
        }

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
    private void sendData(String id, String qnum, String answer, String URl) {
        new Thread() {
            public void run() {
                httpConn2.submitbody(id, qnum, answer, URl, callback2);
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


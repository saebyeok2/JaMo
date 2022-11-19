package com.example.jamofront.PeterPan;

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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.R;
import com.example.jamofront.Task.ClovaTTSTask;
import com.example.jamofront.Task.SubmitTrTasek;
import com.example.jamofront.Userinfo;
import com.example.jamofront.databinding.PeterpanCount4Binding;
import com.example.jamofront.select_peterpantype;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PeterPan_Count4_Activity extends AppCompatActivity implements View.OnClickListener {
    private PeterpanCount4Binding binding;
    private SubmitTrTasek httpConn2 = SubmitTrTasek.getInstance();

    private String URl = "http://3.35.123.120:5000/solved-tquestion" ;
    private String Answer = " ";
    private int count = 0;
    private int ProblemAnswer = 2;
    private int MyAnswer = 0;
    private int BtnNum []= new int[14];
    private String ProblemText = "나무";
    ClovaTTSTask clovaTTSTask;
    MediaPlayer mediaPlayer;
    private Dialog dialog;
    private Dialog dialog2;

    public boolean IS_MENU = false;
    private PopupWindow mPopupWindow;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    //소리 버튼 클릭 시 -> tts.speak(sentence,TextToSpeech.QUEUE_FLUSH, null);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.peterpan_count4);
        binding = PeterpanCount4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.egg1.setOnClickListener(this);
        binding.egg2.setOnClickListener(this);
        binding.egg3.setOnClickListener(this);
        binding.egg4.setOnClickListener(this);
        binding.egg5.setOnClickListener(this);
        binding.egg6.setOnClickListener(this);
        binding.egg7.setOnClickListener(this);
        binding.egg8.setOnClickListener(this);
        binding.egg9.setOnClickListener(this);
        binding.egg10.setOnClickListener(this);
        binding.egg11.setOnClickListener(this);
        binding.egg12.setOnClickListener(this);
        binding.egg13.setOnClickListener(this);
        binding.egg14.setOnClickListener(this);
        binding.ctCheck.setOnClickListener(this);
        binding.ctSound.setOnClickListener(this);
        binding.Backbtn2.setOnClickListener(this);
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
            Intent intent = new Intent(getApplicationContext(), select_peterpantype.class);
            showDialogExit(intent);
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.egg1:
            {
                binding.egg1.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=1;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);

                break;
            }
            case R.id.egg2:
            {
                binding.egg2.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=2;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg3:
            {
                binding.egg3.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=3;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg4:
            {
                binding.egg4.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=4;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg5:
            {
                binding.egg5.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=5;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg6:
            {
                binding.egg6.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=6;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg7:
            {
                binding.egg7.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=7;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg8:
            {
                binding.egg8.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=8;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }

            case R.id.egg9:
            {
                binding.egg9.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=9;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg10:
            {
                binding.egg10.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=10;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg11:
            {
                binding.egg11.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=11;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg12:
            {
                binding.egg12.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=12;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg13:
            {
                binding.egg13.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=13;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }
            case R.id.egg14:
            {
                binding.egg14.setVisibility(View.INVISIBLE);
                BtnNum[MyAnswer]=14;
                MyAnswer+=1;
                Answer = Integer.toString(MyAnswer);
                binding.tvCount.setText(Answer);
                break;
            }

            case R.id.Backbtn2: {
                if (MyAnswer > 0) {
                    if (BtnNum[MyAnswer - 1] == 1) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg1.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 2) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg2.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 3) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg3.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 4) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg4.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 5) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg5.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 6) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg6.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 7) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg7.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    } else if (BtnNum[MyAnswer - 1] == 8) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg8.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    }
                    else if (BtnNum[MyAnswer - 1] == 9) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg9.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    }
                    else if (BtnNum[MyAnswer - 1] == 10) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg10.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText( Answer);
                    }
                    else if (BtnNum[MyAnswer - 1] == 11) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg11.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText( Answer);
                    }
                    else if (BtnNum[MyAnswer - 1] == 12) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg12.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    }
                    else if (BtnNum[MyAnswer - 1] == 13) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg13.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    }
                    else if (BtnNum[MyAnswer - 1] == 14) {
                        BtnNum[MyAnswer - 1] = 0;
                        binding.egg14.setVisibility(View.VISIBLE);
                        MyAnswer -= 1;
                        Answer = Integer.toString(MyAnswer);
                        binding.tvCount.setText(Answer);
                    }

                }
                else
                    Toast.makeText(this.getApplicationContext(),"잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.ct_check:
            {
                count ++;
                if (MyAnswer == ProblemAnswer) {
                    if(count == 1)
                    {
                        sendData(Userinfo.id,"44","true", URl);

                    }

                    Intent intent = new Intent(getApplicationContext(), PeterPan_Count5_Activity.class);
                    showDialog("정답입니다. 다음 문제로 넘어갑니다.", true, intent); // 정답 Dialog
                    break;
                }
                else if (MyAnswer != ProblemAnswer)
                {
                    binding.egg1.setVisibility(View.VISIBLE);
                    binding.egg2.setVisibility(View.VISIBLE);
                    binding.egg3.setVisibility(View.VISIBLE);
                    binding.egg4.setVisibility(View.VISIBLE);
                    binding.egg5.setVisibility(View.VISIBLE);
                    binding.egg6.setVisibility(View.VISIBLE);
                    binding.egg7.setVisibility(View.VISIBLE);
                    binding.egg8.setVisibility(View.VISIBLE);
                    binding.egg9.setVisibility(View.VISIBLE);
                    binding.egg10.setVisibility(View.VISIBLE);
                    binding.egg11.setVisibility(View.VISIBLE);
                    binding.egg12.setVisibility(View.VISIBLE);
                    binding.egg13.setVisibility(View.VISIBLE);
                    binding.egg14.setVisibility(View.VISIBLE);

                    showDialog("오답입니다. 다시 시도해주세요.", false, null);
                    MyAnswer=0;
                     binding.tvCount.setText("0");
                    if(count == 1)
                    {
                        sendData(Userinfo.id,"44","false", URl);

                    }

                }
                break;
            }
            case R.id.ct_sound:{
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

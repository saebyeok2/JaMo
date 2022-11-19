package com.example.jamofront;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.speech.tts.TextToSpeech;
import static android.speech.tts.TextToSpeech.ERROR;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Task.ClovaTTSTask;
import com.example.jamofront.Task.GetRoundTask;
import com.example.jamofront.Task.ProblemTask;
import com.example.jamofront.Task.SubmitRLTask;
import com.example.jamofront.databinding.TestListenBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Listen_Test extends AppCompatActivity implements View.OnClickListener{
    private TestListenBinding binding;
    private String Answers[] = new String[5];
    private int countnum =0;
    private String ProblemText [] = new String[5];
    private String AnswerText  [][] = new String[5][4];
    public static String url = "http://3.35.123.120:5000/listening-prequestion";
    public static String url2 = "http://3.35.123.120:5000/solved-prequestion";
    private ProblemTask httpConn = ProblemTask.getInstance();
    private SubmitRLTask httpConn2 = SubmitRLTask.getInstance();
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();
    private String Pretext;
    ClovaTTSTask clovaTTSTask;
    MediaPlayer mediaPlayer;

    private int submit;
    private int round;
    private String url3= "http://3.35.123.120:5000/pretest-round";
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_listen);
        binding =TestListenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getRound(url3);




        binding.ListenBtn.setOnClickListener(this);
        binding.A1.setOnClickListener(this);
        binding.B1.setOnClickListener(this);
        binding.C1.setOnClickListener(this);
        binding.D1.setOnClickListener(this);
        binding.A2.setOnClickListener(this);
        binding.B2.setOnClickListener(this);
        binding.C2.setOnClickListener(this);
        binding.D2.setOnClickListener(this);
        binding.A3.setOnClickListener(this);
        binding.B3.setOnClickListener(this);
        binding.C3.setOnClickListener(this);
        binding.D3.setOnClickListener(this);
        binding.A4.setOnClickListener(this);
        binding.B4.setOnClickListener(this);
        binding.C4.setOnClickListener(this);
        binding.D4.setOnClickListener(this);
        binding.A5.setOnClickListener(this);
        binding.B5.setOnClickListener(this);
        binding.C5.setOnClickListener(this);
        binding.D5.setOnClickListener(this);
        binding.skiplisten.setOnClickListener(this);
        getTest(url);
        binding.NextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Answers[0] != null && Answers[1] != null && Answers[2] != null && Answers[3] != null && Answers[4] != null) {
                    binding.A1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.B1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.C1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.D1.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.A2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.B2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.C2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.D2.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.A3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.B3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.C3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.D3.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.A4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.B4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.C4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.D4.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.A5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.B5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.C5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.D5.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    if(countnum == 0)
                    {
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[0]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[1]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[2]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[3]) ,url2);
                        submit++;
                        sendData(Userinfo.id  ,Integer.toString(submit) ,String.valueOf(Answers[4]) ,url2);
                        submit++;
                        binding.scroll.fullScroll(ScrollView.FOCUS_UP);
                        getTest(url);
                    }
                    if(countnum == 1)
                    {
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[0]) ,url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[1]) ,url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[2]) ,url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[3]) ,url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[4]) ,url2);
                        submit++;
                        binding.scroll.fullScroll(ScrollView.FOCUS_UP);
                        getTest(url);
                    }

                    for (int i = 0; i <= 4; i++) {
                        Answers[i] = null;
                    }
                    countnum += 1;

                    if (countnum >= 3) {
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[0]),url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[1]),url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[2]),url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[3]),url2);
                        submit++;
                        sendData(Userinfo.id, Integer.toString(submit) ,String.valueOf(Answers[4]),url2);
                        submit++;
                        Intent intent = new Intent(getApplicationContext(), SpeakTest.class);
                        startActivity(intent);

                    }

                }
                else
                    Toast.makeText(Listen_Test.this, "문제를 다풀지 않았습니다. 다시한번 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });



    }

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
                    overridePendingTransition(0,0);
                }
                else dialog.dismiss(); // 오답이면 3초 후 dismiss
            }
        };
        timer.schedule(timerTask, 1500);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.A_1: {
                binding.A1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.B1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[0] = "1";
                break;
            }
            case R.id.B_1: {
                binding.A1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.C1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[0] = "2";
                break;

            }
            case R.id.C_1: {
                binding.A1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.D1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[0] = "3";
                break;

            }
            case R.id.D_1: {
                binding.A1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[0] = "4";
                break;

            }
            case R.id.A_2: {
                binding.A2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.B2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[1] = "1";
                break;

            }
            case R.id.B_2: {
                binding.A2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.C2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[1] = "2";
                break;

            }
            case R.id.C_2: {
                binding.A2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.D2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[1] = "3";
                break;

            }
            case R.id.D_2: {
                binding.A2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[1] = "4";
                break;

            }
            case R.id.A_3: {
                binding.A3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.B3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[2] = "1";
                break;

            }
            case R.id.B_3: {
                binding.A3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.C3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[2] = "2";
                break;

            }
            case R.id.C_3: {
                binding.A3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[2] = "3";
                break;

            }
            case R.id.D_3: {
                binding.A3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[2] = "4";
                break;

            }
            case R.id.A_4: {
                binding.A4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.B4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[3] = "1";
                break;

            }
            case R.id.B_4: {
                binding.A4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.C4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[3] = "2";
                break;

            }
            case R.id.C_4: {
                binding.A4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[3] = "3";
                break;

            }
            case R.id.D_4: {
                binding.A4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[3] = "4";
                break;

            }
            case R.id.A_5: {
                binding.A5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.B5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[4] = "1";
                break;

            }
            case R.id.B_5: {
                binding.A5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.C5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[4] = "2";
                break;

            }
            case R.id.C_5: {
                binding.A5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[4] = "3";
                break;

            }
            case R.id.D_5: {
                binding.A5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.B5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.C5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.D5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[4] = "4";
                break;
            }
            case R.id.ListenBtn:{
                clovaTTSTask = new ClovaTTSTask(Pretext);
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
            case R.id.skiplisten:{
                Intent intent = new Intent(getApplicationContext(), SpeakTest.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }






        }

    }
    private void getTest(String url) {
        new Thread() {
            public void run() {
                httpConn.getTest(callback,url,countnum);
            }}.start();

    }
    private final okhttp3.Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                //콜백에 성공하면 settext를 이용해 문제 텍스트 지정
                JSONObject jsonObject = new JSONObject(response.body().string());

                for(int i=0; i<5; i++)
                {
                    ProblemText[i] = jsonObject.getString("question"+(i+1));
                    Log.d("Test",ProblemText[i]);
                }
                for(int i=0; i<5; i++)
                {
                    for(int j=0;j<4;j++)

                    {
                        AnswerText[i][j] = jsonObject.getString("e"+(i+1)+(j+1));
                        Log.d("AnswerText", AnswerText[i][j]);
                    }
                }
                Pretext = jsonObject.getString("text");




                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        binding.ListenTestPb1.setText(ProblemText[0]);
                        binding.ListenTestPb2.setText(ProblemText[1]);
                        binding.ListenTestPb3.setText(ProblemText[2]);
                        binding.ListenTestPb4.setText(ProblemText[3]);
                        binding.ListenTestPb5.setText(ProblemText[4]);

                        binding.A1.setText(AnswerText[0][0]);
                        binding.B1.setText(AnswerText[0][1]);
                        binding.C1.setText(AnswerText[0][2]);
                        binding.D1.setText(AnswerText[0][3]);

                        binding.A2.setText(AnswerText[1][0]);
                        binding.B2.setText(AnswerText[1][1]);
                        binding.C2.setText(AnswerText[1][2]);
                        binding.D2.setText(AnswerText[1][3]);

                        binding.A3.setText(AnswerText[2][0]);
                        binding.B3.setText(AnswerText[2][1]);
                        binding.C3.setText(AnswerText[2][2]);
                        binding.D3.setText(AnswerText[2][3]);

                        binding.A4.setText(AnswerText[3][0]);
                        binding.B4.setText(AnswerText[3][1]);
                        binding.C4.setText(AnswerText[3][2]);
                        binding.D4.setText(AnswerText[3][3]);

                        binding.A5.setText(AnswerText[4][0]);
                        binding.B5.setText(AnswerText[4][1]);
                        binding.C5.setText(AnswerText[4][2]);
                        binding.D5.setText(AnswerText[4][3]);


                    }
                });






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void sendData(String id, String prenum, String answer, String url) {
        new Thread() {
            public void run() {
                httpConn2.submitbody(id, prenum, answer, url, callback);
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
                submit = 106+(round-1)*15;


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}

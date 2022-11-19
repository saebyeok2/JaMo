package com.example.jamofront;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Task.GetRoundTask;
import com.example.jamofront.Task.ProblemTask;
import com.example.jamofront.Task.SubmitRLTask;
import com.example.jamofront.databinding.TestReadunderstandBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Test_ReadUnderstand extends AppCompatActivity implements View.OnClickListener {
    private String Answers[] = new String[5];

    private int countnum = 0;
    public int round;
    public int submit;
    private String Pretext;
    private String ProblemText[] = new String[5];
    private String AnswerText[][] = new String[5][4];
    private TestReadunderstandBinding binding;
    public static String url = "http://3.35.123.120:5000/reading-prequestion";
    private String url2= "http://3.35.123.120:5000/solved-prequestion";
    private String url3= "http://3.35.123.120:5000/pretest-round";
    private ProblemTask httpConn = ProblemTask.getInstance();
    private SubmitRLTask httpConn2 = SubmitRLTask.getInstance();
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_readunderstand);
        binding = TestReadunderstandBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.RpbA1.setOnClickListener(this);
        binding.RpbB1.setOnClickListener(this);
        binding.RpbC1.setOnClickListener(this);
        binding.RpbD1.setOnClickListener(this);

        binding.RpbA2.setOnClickListener(this);
        binding.RpbB2.setOnClickListener(this);
        binding.RpbC2.setOnClickListener(this);
        binding.RpbD2.setOnClickListener(this);

        binding.RpbA3.setOnClickListener(this);
        binding.RpbB3.setOnClickListener(this);
        binding.RpbC3.setOnClickListener(this);
        binding.RpbD3.setOnClickListener(this);

        binding.RpbA4.setOnClickListener(this);
        binding.RpbB4.setOnClickListener(this);
        binding.RpbC4.setOnClickListener(this);
        binding.RpbD4.setOnClickListener(this);

        binding.RpbA5.setOnClickListener(this);
        binding.RpbB5.setOnClickListener(this);
        binding.RpbC5.setOnClickListener(this);
        binding.RpbD5.setOnClickListener(this);

        binding.skipread.setOnClickListener(this);


        getTest(url);
        getRound(url3);






        binding.NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Answers[0] != null && Answers[1] != null && Answers[2] != null && Answers[3] != null && Answers[4] != null) {
                    binding.RpbA1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbB1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbC1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbD1.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.RpbA2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbB2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbC2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbD2.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.RpbA3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbB3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbC3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbD3.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.RpbA4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbB4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbC4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbD4.setBackgroundColor(Color.parseColor("#95FFEB3B"));

                    binding.RpbA5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbB5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbC5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                    binding.RpbD5.setBackgroundColor(Color.parseColor("#95FFEB3B"));

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
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[4]) ,url2);
                        submit++;
                        getTest(url);
                        binding.scroll.fullScroll(ScrollView.FOCUS_UP);
                    }
                    if(countnum == 1)
                    {
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[0]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[1]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[2]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[3]) ,url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[4]) ,url2);
                        submit++;
                        getTest(url);
                        binding.scroll.fullScroll(ScrollView.FOCUS_UP);
                    }
                    if (countnum == 2) {

                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[0]),url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[1]),url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[2]),url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[3]),url2);
                        submit++;
                        sendData(Userinfo.id,Integer.toString(submit) ,String.valueOf(Answers[4]),url2);

                    }

                    countnum += 1;
                    for (int i = 0; i <= 4; i++) {
                        Answers[i] = null;
                    }

                    if (countnum == 3) {
                        Intent intent = new Intent(getApplicationContext(), Listen_Test.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }




                } else
                    Toast.makeText(Test_ReadUnderstand.this, "문제를 다풀지 않았습니다. 다시한번 확인해주세요", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RpbA_1: {
                binding.RpbA1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbB1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[0] = "1";
                break;
            }
            case R.id.RpbB_1: {
                binding.RpbA1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbC1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[0] = "2";
                break;
            }
            case R.id.RpbC_1: {
                binding.RpbA1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbD1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[0] = "3";
                break;
            }
            case R.id.RpbD_1: {
                binding.RpbA1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC1.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD1.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[0] = "4";
                break;
            }
            case R.id.RpbA_2: {
                binding.RpbA2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbB2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[1] = "1";
                break;

            }
            case R.id.RpbB_2: {
                binding.RpbA2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbC2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[1] = "2";
                break;

            }
            case R.id.RpbC_2: {
                binding.RpbA2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbD2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[1] = "3";
                break;
            }
            case R.id.RpbD_2: {
                binding.RpbA2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC2.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD2.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[1] = "4";
                break;
            }
            case R.id.RpbA_3: {
                binding.RpbA3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbB3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[2] = "1";
                break;
            }
            case R.id.RpbB_3: {
                binding.RpbA3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbC3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[2] = "2";
                break;
            }
            case R.id.RpbC_3: {
                binding.RpbA3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbD3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[2] = "3";
                break;
            }
            case R.id.RpbD_3: {
                binding.RpbA3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC3.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD3.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[2] = "4";
                break;
            }
            case R.id.RpbA_4: {
                binding.RpbA4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbB4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[3] = "1";
                break;
            }
            case R.id.RpbB_4: {
                binding.RpbA4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbC4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[3] = "2";
                break;
            }
            case R.id.RpbC_4: {
                binding.RpbA4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbD4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[3] = "3";
                break;
            }
            case R.id.RpbD_4: {
                binding.RpbA4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC4.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD4.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[3] = "4";
                break;
            }
            case R.id.RpbA_5: {
                binding.RpbA5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbB5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[4] = "1";
                break;

            }
            case R.id.RpbB_5: {
                binding.RpbA5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbC5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[4] = "2";
                break;
            }
            case R.id.RpbC_5: {
                binding.RpbA5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                binding.RpbD5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                Answers[4] = "3";
                break;
            }
            case R.id.RpbD_5: {
                binding.RpbA5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbB5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbC5.setBackgroundColor(Color.parseColor("#95FFEB3B"));
                binding.RpbD5.setBackgroundColor(Color.parseColor("#FFFFC107"));
                Answers[4] = "4";
                break;
            }
            case R.id.skipread:{
                Intent intent = new Intent(getApplicationContext(), Listen_Test.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;

            }

        }
    }

    private void getTest(String url) {
        new Thread() {
            public void run() {
                httpConn.getTest(callback, url, countnum);
            }
        }.start();

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



                for (int i = 0; i < 5; i++) {
                    ProblemText[i] = jsonObject.getString("question" + (i + 1));
                    Log.d("Test", ProblemText[i]);
                }
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        AnswerText[i][j] = jsonObject.getString("e" + (i + 1) + (j + 1));
                        Log.d("AnswerText", AnswerText[i][j]);
                    }
                }

                Pretext = jsonObject.getString("text");
                Log.d("text", Pretext);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.readtestPbtext.setText(Pretext);
                        binding.readtestPb1.setText(ProblemText[0]);
                        binding.readtestPb2.setText(ProblemText[1]);
                        binding.readtestPb3.setText(ProblemText[2]);
                        binding.readtestPb4.setText(ProblemText[3]);
                        binding.readtestPb5.setText(ProblemText[4]);

                        binding.RpbA1.setText(AnswerText[0][0]);
                        binding.RpbB1.setText(AnswerText[0][1]);
                        binding.RpbC1.setText(AnswerText[0][2]);
                        binding.RpbD1.setText(AnswerText[0][3]);

                        binding.RpbA2.setText(AnswerText[1][0]);
                        binding.RpbB2.setText(AnswerText[1][1]);
                        binding.RpbC2.setText(AnswerText[1][2]);
                        binding.RpbD2.setText(AnswerText[1][3]);

                        binding.RpbA3.setText(AnswerText[2][0]);
                        binding.RpbB3.setText(AnswerText[2][1]);
                        binding.RpbC3.setText(AnswerText[2][2]);
                        binding.RpbD3.setText(AnswerText[2][3]);

                        binding.RpbA4.setText(AnswerText[3][0]);
                        binding.RpbB4.setText(AnswerText[3][1]);
                        binding.RpbC4.setText(AnswerText[3][2]);
                        binding.RpbD4.setText(AnswerText[3][3]);

                        binding.RpbA5.setText(AnswerText[4][0]);
                        binding.RpbB5.setText(AnswerText[4][1]);
                        binding.RpbC5.setText(AnswerText[4][2]);
                        binding.RpbD5.setText(AnswerText[4][3]);


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
                submit = 61+(round-1)*15;


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

package com.example.jamofront;

import static com.example.jamofront.MainActivity.dbHelper;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jamofront.Task.GetRoundTask;
import com.example.jamofront.Task.SubmitRLTask;
import com.example.jamofront.Task.WordProblemTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.POST;

public class WordTest extends AppCompatActivity {
    private String id = "2419288225";
    private TextView ProblemText;
    private int  round ;
    private int flag= 0;
    private String Url3 = "http://3.35.123.120:5000/pretest-round";
    private String AnswerText;
    private String Problems [] = new String[20];
    private ImageButton btn_startRecord;
    private ImageButton btn_stopRecord;
    private ImageButton skipBtn;
    private Button Next_Page;
    private TextView TextTest;
    private Button Seeword;
    private int seenum = 0;
    private ProgressBar progressBar;
    private TextView Pbnum;
    private int sepakcount = 0;
    private int showMic = 0;

    public int ProblemNum = 0;
    private String STTURL = "http://3.35.123.120:5000/uploadfile";
    public  String url2 = "http://3.35.123.120:5000/word-prequestion";
    public String url = "http://3.35.123.120:5000/solved-prequestion";
    private WordProblemTask httpConn = WordProblemTask.getInstance();
    private SubmitRLTask httpConn2 = SubmitRLTask.getInstance();
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();
    wavClass wavObj = new wavClass("sdcard/Download");
    public int submit;
    private String qmark ="";
    private int word_length ;
    private TimerTask timerTask = null;
    private Timer timer = null;
    Dialog dialog;
    TimerTask timerTask2 = null;
    Timer timer2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_word);
        Seeword = findViewById(R.id.button10);
        Pbnum = findViewById(R.id.Testnum);
        ProblemText = findViewById(R.id.problem);
        btn_startRecord = findViewById(R.id.start_speak);
        btn_stopRecord = findViewById(R.id.stop_speak);
        Next_Page = findViewById(R.id.next_btn);
        progressBar = findViewById(R.id.progressBar);
        skipBtn = findViewById(R.id.skipword);
        permissionCheck();
        getTest(url2);
        getRound(Url3);
        Log.d("url",""+round);
        ProblemText.setText("???");
        progressBar.setMax(100);
        Pbnum.setText(Integer.toString(ProblemNum+1)+"번문제");


        if(dbHelper == null)
        {

        }
        else if(dbHelper.getCount() == 3) { // 이미 진단검사를 한 상황에서 다시 하는 상황
            // 아무것도 안함
        }
        else dbHelper.UpdateDB(2); // 아직 진단검사 안한 상황이면 초기값 1을 2로 업데이트


        Seeword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seenum == 0) {
                    initProgress();
                    startTimerThread();

                    ProblemText.setText(Problems[ProblemNum]);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            word_length = Problems[ProblemNum].length();
                            Log.d("a", Integer.toString(word_length));

                            for (int a = 0; a < word_length; a++) {
                                qmark = qmark + "?";
                            }
                            ProblemText.setText(qmark);
                        }

                    }, 3000);
                    Timer timer2 = new Timer();
                    if(showMic == 0)
                    {
                        TimerTask timerTask2= new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        showDialog("이제 마이크를 누르고 단어를 소리내어 읽어보세요!", false, null); //  Dialog
                                    }
                                });
                            }
                        };
                        timer2.schedule(timerTask2, 3000);
                    }
                    showMic++;
                    seenum++;
                    qmark = "";
                }
                else
                    Toast.makeText(getApplicationContext(),"문제가 이미 제시됐습니다",Toast.LENGTH_SHORT).show();

            }
        });

        btn_startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sepakcount == 0)
                {
                    wavObj.startRecording();
                    btn_startRecord.setImageResource(R.drawable.mic_strp);
                    sepakcount =1;
                }
                else if (sepakcount == 1)
                {
                    wavObj.stopRecording();
                    String filePath = "/sdcard/Download/final_record.wav";
                    File file = new File(filePath);
                    audioReq("POST", file);
                    btn_startRecord.setImageResource(R.drawable.mic);
                    sepakcount = 0 ;
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Test_ReadUnderstand.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });


        Next_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ProblemNum<19 ) {
                    if(AnswerText != null) {

                        sendData(Userinfo.id, Integer.toString(submit), AnswerText, url);
                        submit++;
                        ProblemNum += 1;
                        AnswerText = null;
                        qmark = "";
                        word_length=Problems[ProblemNum].length();
                        for (int a = 0; a < word_length;a++)
                        {
                            qmark = qmark + "?";
                        }
                        ProblemText.setText(qmark);
                        Pbnum.setText(Integer.toString(ProblemNum+1)+"번문제");

                        Log.d("a",""+round);
                        seenum = 0;
                    }
                    else
                        Toast.makeText(getApplicationContext(),"단어를 말하지 않았습니다..",Toast.LENGTH_SHORT).show();


                }
                else if (ProblemNum==19){
                    Toast.makeText(getApplicationContext(),"마지막 문제입니다.",Toast.LENGTH_SHORT).show();
                    if(AnswerText != null) {
                        sendData(Userinfo.id, Integer.toString(submit), AnswerText, url);
                        AnswerText= null;
                        seenum = 0;
                        word_length=Problems[ProblemNum].length();
                        ProblemNum++;
                        Next_Page.setText("다음 검사 하러가기");
                    }
                    else Toast.makeText(getApplicationContext(),"단어를 말하지 않았습니다..",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), Test_ReadUnderstand.class);
                    startActivity(intent);
                }


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
                }
                else dialog.dismiss(); // 오답이면 3초 후 dismiss
            }
        };
        timer.schedule(timerTask, 1500);
    }
    private void getTest(String url) {
        new Thread() {
            public void run() {
                httpConn.getTest(callback,url);

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

                for(int i=0; i<20; i++)
                {
                    Problems[i] = jsonObject.getString("word"+(i+1));
                    Log.d("Test",Problems[i]);
                }

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

    private void sendData(String id, String prenum, String answer, String url) {
        new Thread() {
            public void run() {
                httpConn2.submitbody(id, prenum, answer, url, callback2);
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

    void audioReq(String type, File file){

        // POST 요청 시 RequestBody에 Multipart형식의 파일을 담아서 POST요청을 Flask서버에 보냄
        // Flask서버에선 파일을 받아서 특정경로에 저장한 뒤 저장경로를

        String fullURL = STTURL;
        Request request;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();

        if(type.equals("POST")){
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("audio", file.getName(),
                            RequestBody.create(MediaType.parse("audio/wav"), file))
                    .build();

            request=new Request.Builder()
                    .url(fullURL)
                    .post(body)
                    .build();
        }else{
            // GET 요청
            request = new Request.Builder()
                    .url(fullURL)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                final String responseData = response.body().string();

                AnswerText = responseData;
                Log.d("AnswerText",AnswerText);
                AnswerText = AnswerText.replace(" ", "");
                AnswerText = AnswerText.replace(".","");


            }
        });
    }
    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

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
                submit = ((round-1)*20)+1;


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
    public void initProgress()
    {
        progressBar.setProgress(100);
    }

    public void startTimerThread()
    {

        timerTask = new TimerTask() {
            @Override
            public void run() {
                decreaseBar();
            }
        };
        if (flag == 0)
        {
            timer = new Timer();
            timer.schedule(timerTask,0,30);
        }

        flag++;

    }
    public void decreaseBar()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int curr = progressBar.getProgress();
                if(curr>0)
                {
                    curr=curr-1;
                }

                progressBar.setProgress(curr);
            }
        });

    }

}

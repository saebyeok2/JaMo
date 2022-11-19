package com.example.jamofront;

import static com.example.jamofront.MainActivity.dbHelper;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jamofront.Task.GetRoundTask;
import com.example.jamofront.Task.SubmitRLTask;
import com.example.jamofront.Task.WordProblemTask;
import com.example.jamofront.databinding.TestSpeakBinding;
import com.example.jamofront.line.Test_Result;

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

public class SpeakTest extends AppCompatActivity implements View.OnClickListener{
    private TextView ProblemText;
    private int  round ;
    private int submit;
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();
    private String Url3 = "http://3.35.123.120:5000/pretest-round";
    private TestSpeakBinding binding;
    private ImageButton btn_startRecord;
    private ImageButton btn_stopRecord;
    private TextView tv_result;
    private String url = "http://3.35.123.120:5000/speaking-prequestion";
    private String STTURL = "http://3.35.123.120:5000/uploadfile";
    private String SURl = "http://3.35.123.120:5000/solved-prequestion";
    private SubmitRLTask httpConn2 = SubmitRLTask.getInstance();
    private String POST="POST";
    private String GET="GET";
    private String Problems[] = new String[3];
    private int ProblemNum = 0;
    private WordProblemTask httpConn = WordProblemTask.getInstance();
    private String Answer;
    Dialog dialog;

    wavClass wavObj = new wavClass("sdcard/Download");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_speak);
        binding = TestSpeakBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        btn_startRecord = findViewById(R.id.start_speak);
        btn_stopRecord = findViewById(R.id.stop_speak);
        ProblemText = findViewById(R.id.problem);
        binding.button.setOnClickListener(this);
        btn_startRecord.setOnClickListener(this);
        btn_stopRecord.setOnClickListener(this);
        binding.skipspeak.setOnClickListener(this);


        permissionCheck();
        getTest(url);
        getRound(Url3);


        showDialog("마이크를 누르고 문장을 소리내어 읽어보세요!", false, null); //  Dialog
    }

    // REST 통신 요청 함수
    // type = POST or GET
    // method = URL뒤에 값 ex)uploadfile
    // file = 오디오파일을 담을 file 객체
    void audioReq(String type,File file){

        // POST 요청 시 RequestBody에 Multipart형식의 파일을 담아서 POST요청을 Flask서버에 보냄

        Request request;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();

        if(type.equals(POST)){
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("audio", file.getName(),
                            RequestBody.create(MediaType.parse("audio/wav"), file))
                    .build();

            request=new Request.Builder()
                    .url(STTURL)
                    .post(body)
                    .build();
        }else{
            // GET 요청
            request = new Request.Builder()
                    .url(STTURL)
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

                Answer = responseData;
            }
        });
    }
    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
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

                for(int i=0; i<3; i++)
                {
                    Problems[i] = jsonObject.getString("speaking"+(i+1));
                    Log.d("Test",Problems[i]);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProblemText.setText(Problems[0]);
                        ProblemNum+=1;
                    }
                });





            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:{
                if(ProblemNum == 1){

                    sendData(Userinfo.id,Integer.toString(submit),Answer,SURl);
                    Log.d("써밋",""+submit);
                    submit++;
                    ProblemText.setText(Problems[ProblemNum]);
                    ProblemNum+=1;
                }
                else if(ProblemNum ==2)
                {
                    sendData(Userinfo.id,Integer.toString(submit),Answer,SURl);

                    submit++;
                    ProblemText.setText(Problems[ProblemNum]);
                    ProblemNum+=1;
                }
                else {
                    sendData(Userinfo.id,Integer.toString(submit),Answer,SURl);
                    dbHelper.UpdateDB(3); // 진단검사가 끝나면 count 값을 3으로 업데이트
                    Intent intent = new Intent(getApplicationContext(), Test_Result.class);
                    startActivity(intent);
                }
                break;
            }
            case  R.id.start_speak:{
                wavObj.startRecording();
                Toast.makeText(this.getApplicationContext(),"녹음 시작됨", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.stop_speak: {
                wavObj.stopRecording();
                Toast.makeText(this.getApplicationContext(),"녹음 중지됨", Toast.LENGTH_SHORT).show();
                String filePath = "/sdcard/Download/final_record.wav";
                File file = new File(filePath);
                audioReq("POST", file);
                break;
            }
            case R.id.skipspeak:{
                dbHelper.UpdateDB(3); // 진단검사가 끝나면 count 값을 3으로 업데이트
                Intent intent = new Intent(getApplicationContext(), Home_main.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            }
        }
    }

    private void sendData(String id, String prenum, String answer, String SURl) {
        new Thread() {
            public void run() {
                httpConn2.submitbody(id, prenum, answer, SURl, callback);
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
                submit = 151+(round-1)*3;


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

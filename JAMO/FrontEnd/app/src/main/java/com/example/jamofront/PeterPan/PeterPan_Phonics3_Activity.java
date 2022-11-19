package com.example.jamofront.PeterPan;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jamofront.R;
import com.example.jamofront.Task.ClovaTTSTask;
import com.example.jamofront.Task.SubmitTrTasek;
import com.example.jamofront.Userinfo;
import com.example.jamofront.select_peterpantype;
import com.example.jamofront.wavClass;

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

public class PeterPan_Phonics3_Activity extends AppCompatActivity {
    private SubmitTrTasek httpConn2 = SubmitTrTasek.getInstance();
    private String result ="a";
    private String URl = "http://3.35.123.120:5000/solved-tquestion" ;
    private String ProblemText = "우리가 간절하게 소원을 빈다면, 꿈은 이루어질거야.";
    private String UserAnswer ="";
    private String Answer ;
    private int Btn_num = 1;
    private String STTURL = "http://3.35.123.120:5000/uploadfile";
    private ImageButton speak_btn;
    private ImageButton sound_btn;
    private ImageView popupMenuBtn;
    private ImageButton Check_btn;
    private String POST="POST";
    private MediaPlayer mediaPlayer;
    public static String tempname;
    wavClass wavObj = new wavClass("sdcard/Download");
    ClovaTTSTask clovaTTSTask = new ClovaTTSTask(ProblemText); // 클로바 TTS 클래스




    private Dialog dialog;
    private Dialog dialog2;
    public boolean IS_MENU = false;
    private PopupWindow mPopupWindow;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peterpan_phonics3);
        permissionCheck();


        sound_btn = findViewById(R.id.imageButton);
        speak_btn = findViewById(R.id.speak_btn);
        popupMenuBtn = findViewById(R.id.toolbar_menu_btn);
        Check_btn = findViewById(R.id.imageButton5);

        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });

        Check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(Userinfo.id,"73",Answer,URl);
                Log.d("Answer",ProblemText+"/"+UserAnswer);
                Log.d("result","a"+result);
            }
        });

        speak_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Btn_num == 1)
                {
                    Btn_num = 0;
                    wavObj.startRecording();
                    Toast.makeText(getApplicationContext(),"녹음 시작됨", Toast.LENGTH_SHORT).show();
                    speak_btn.setImageResource(R.drawable.tellingno);

                }
                else if (Btn_num == 0)
                {

                    Btn_num=1;
                    wavObj.stopRecording();
                    speak_btn.setImageResource(R.drawable.telling);
                    Toast.makeText(getApplicationContext(),"녹음 중지됨", Toast.LENGTH_SHORT).show();
                    String filePath = "/sdcard/Download/final_record.wav";
                    File file = new File(filePath);
                    audioReq("POST", file);


                }



            }
        });
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

    void audioReq(String type, File file){

        // POST 요청 시 RequestBody에 Multipart형식의 파일을 담아서 POST요청을 Flask서버에 보냄
        // Flask서버에선 파일을 받아서 특정경로에 저장한 뒤 저장경로를

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

                UserAnswer = responseData;
                Answer = ProblemText+"/"+UserAnswer;


            }
        });
    }


    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    void sendData(String id ,String qnum, String answer, String URl) {
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
            result = body;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(result.contains("true"))
                    {
                        //
                        Intent intent = new Intent(getApplicationContext(), PeterPan_Phonics4_Activity.class);
                        showDialog("정답입니다. 다음 문제로 넘어갑니다.", true, intent); // 정답 Dialog
                    }
                    else if(result.contains("false"))
                    {
                        showDialog("오답입니다. 다시 시도해주세요.", false, null);
                        UserAnswer = "";
                    }



                }
            });

        }
    };
}

package com.example.jamofront.PeterPan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.jamofront.main_theme_process;
import com.example.jamofront.R;
import com.example.jamofront.Task.GetProgressTask;
import com.example.jamofront.databinding. PeterpanProgressBinding;
import com.example.jamofront.databinding.PeterpanProgressBinding;
import com.example.jamofront.main_theme_answerrate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PeterPan_Progress_Activity extends AppCompatActivity {
    private  PeterpanProgressBinding binding;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private final GetProgressTask httpConn = GetProgressTask.getInstance();
    private String url = "http://3.35.123.120:5000/theme-progress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peterpan_progress);
        binding =  PeterpanProgressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getProgress(url);

        Log.d("test","test");

    }
    private void getProgress(String url) {
        new Thread() {
            public void run() {
                httpConn.getPeterPanProgress(callback,url);

            }}.start();

    }
    private okhttp3.Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            try {

                JSONObject jsonObject = new JSONObject(response.body().string());
                int Count_Value = jsonObject.getInt("syllable_count");
                int phoneme_recognition = jsonObject.getInt("phoneme_recognition");
                int Same_Syllable = jsonObject.getInt("same_syllable_rate");
                int length_comparison = jsonObject.getInt("length_comparison");
                int phonological_count1 = jsonObject.getInt("phonological_count");
                int phoneme_separtion = jsonObject.getInt("phoneme_separation");
                int phonics = jsonObject.getInt("phonics");
                int literacy = jsonObject.getInt("literacy");


                Log.d("","로그확인");
                String a = Integer.toString(phoneme_recognition);
                Log.d("s",a);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.syllableCount.setMax((int)100);
                        binding.phonemeRecognition.setMax((int)100);
                        binding.sameSyllable.setMax((int)100);
                        binding.lengthComparison.setMax((int)100);
                        binding.phonologicalCount.setMax((int)100);
                        binding.phonemeSeparation.setMax((int)100);
                        binding.phonics.setMax((int)100);
                        binding.literacy.setMax((int)100);

                        binding.syllableCount.setProgress((int)Count_Value);
                        binding.syllableCountText.setText("음절 개수 세기 훈련 "+Count_Value+"%");
                        binding.phonemeRecognition.setProgress((int)phoneme_recognition);
                        binding.phonemeRecognitionText.setText("음운 인식 훈련 "+phoneme_recognition+"%");
                        binding.sameSyllable.setProgress((int)Same_Syllable);
                        binding.sameSyllableText.setText("동일한 음절 찾기 훈련 "+Same_Syllable+"%");
                        binding.lengthComparison.setProgress((int)length_comparison);
                        binding.lengthComparisonText.setText("길이 비교 훈련 "+length_comparison+"%");
                        binding.phonologicalCount.setProgress((int)phonological_count1);
                        binding.phonologicalCountText.setText("특정 음운 개수 단어 찾기 훈련 "+phonological_count1+"%");
                        binding.phonemeSeparation.setProgress((int)phoneme_separtion);
                        binding.phonemeSeparationText.setText("자소 분리 훈련 "+phoneme_separtion+"%");
                        binding.phonics.setProgress((int)phonics);
                        binding.phonicsText.setText("파닉스 훈련 "+phonics+"%");
                        binding.literacy.setProgress((int)literacy);
                        binding.literacyText.setText("문해력 훈련 "+literacy+"%");

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

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
            Intent intent = new Intent(getApplicationContext(), main_theme_process.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }
}

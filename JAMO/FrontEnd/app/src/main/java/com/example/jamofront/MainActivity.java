package com.example.jamofront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.view.View;
import android.widget.ImageButton;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.example.jamofront.Task.UserInfoTask;

//import org.apache.http.HttpConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public static String url = "http://3.35.123.120:5000";
    private UserInfoTask httpConn = UserInfoTask.getInstance();
    private static MainActivity instance= new MainActivity();
    public static MainActivity getInstance() {
        return instance;
    }
    public String id,email,nickname;
    public static DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(MainActivity.this);
        if(dbHelper.getCount() == 1 || dbHelper.getCount() == 2 || dbHelper.getCount() == 3) { // 만약 최초 로그인이 아닐 시
            // 아무것도 안함
        }
        else dbHelper.InsertDB(1); // 만약 최초 로그인이면 초기값인 1을 Insert

        getHashKey();
        ImageButton kakao_login_button = (ImageButton) findViewById(R.id.kakao_login_button);
        kakao_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountLogin();

            }
        });
    }

    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }



    public void accountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(dbHelper.getCount() != 3){ // count 값이 3(진단검사 완료값)이 아니면 진단검사 시작. ex) 초기값 1, 진단검사 시작값 2
                                    Intent intent = new Intent(getApplicationContext(), WordTest.class);
                                    startActivity(intent);
                                    overridePendingTransition(0,0);
                                }
                                else { // count 값이 3이면 훈련선택창으로
                                    Intent intent = new Intent(getApplicationContext(), Home_main.class);
                                    startActivity(intent);
                                    overridePendingTransition(0,0);
                                }
                            }
                        });


                    }
                },500);

            }
            return null;
        });
    }


    public void getUserInfo() {

        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: " + user.getId() +
                            "\n이메일: " + user.getKakaoAccount().getEmail() +
                            "\n이름: " + user.getKakaoAccount().getProfile().getNickname());
                }
                Account user1 = user.getKakaoAccount();

                id = user.getId().toString();
                email = user1.getEmail();
                nickname = user1.getProfile().getNickname();
                sendData(id,email,nickname,url+"/login");
                Userinfo userinfo = new Userinfo(id,email,nickname);
            }
            return null;
        });

    }

    private void sendData(String id,String email,String nickname,String url) {
        new Thread() {
            public void run() {
                httpConn.LoginBody(id, email, nickname,callback,url);
            }}.start();
    }
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("TAG", "서버에서 응답한 Body:"+body);
        }
    };
//테스트
}


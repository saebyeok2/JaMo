package com.example.jamofront;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KaKaoApplication extends Application {
    private static KaKaoApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        KakaoSdk.init(this,"e7a819018aa1f1669b3d0b26ce29caad");
        //qkralswndfs

    }
}
//토큰ㅇ 회원정보를 가져오기 받아와서 사용자가 로그인 -> 보내주기.
//api로 통신하는 법
//받는 주소로 요청하는 법 json파일 형식으로 네가지 net상상
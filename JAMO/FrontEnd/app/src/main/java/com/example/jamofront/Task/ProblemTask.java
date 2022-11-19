package com.example.jamofront.Task;

import com.example.jamofront.Userinfo;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ProblemTask {

    private static OkHttpClient client;
    private static ProblemTask instance= new ProblemTask();
    public static ProblemTask getInstance() {
        return instance;
    }


    private ProblemTask() {
        this.client = new OkHttpClient();
    }
    public static void getTest( Callback callback, String url,int count) {
        String a =String.valueOf(count);
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .addHeader("textnum",a)
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

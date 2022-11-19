package com.example.jamofront.Task;

import com.example.jamofront.Userinfo;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetRoundTask {
    private static OkHttpClient client;
    private static GetRoundTask instance= new GetRoundTask();
    public static GetRoundTask getInstance() {
        return instance;
    }


    private GetRoundTask() {
        this.client = new OkHttpClient();
    }
    public static void getround(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


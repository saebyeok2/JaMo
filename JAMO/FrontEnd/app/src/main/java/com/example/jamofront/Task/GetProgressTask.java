package com.example.jamofront.Task;

import com.example.jamofront.Userinfo;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetProgressTask {

    private static OkHttpClient client;
    private static GetProgressTask instance= new GetProgressTask();
    public static GetProgressTask getInstance() {
        return instance;
    }


    private GetProgressTask() {
        this.client = new OkHttpClient();
    }
    public static void getProgress(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id",Userinfo.id)
                    .addHeader("theme_id","1")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPeterPanProgress(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id",Userinfo.id)
                    .addHeader("theme_id","2")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getRedhoodProgress(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id",Userinfo.id)
                    .addHeader("theme_id","3")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



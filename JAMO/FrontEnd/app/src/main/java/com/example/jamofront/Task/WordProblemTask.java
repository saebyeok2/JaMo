package com.example.jamofront.Task;

import com.example.jamofront.Userinfo;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WordProblemTask {

    private static OkHttpClient client;
    private static WordProblemTask instance= new WordProblemTask();
    public static WordProblemTask getInstance() {return instance;}


    private WordProblemTask() {
        this.client = new OkHttpClient();
    }
    public static void getTest(Callback callback, String url) {

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

package com.example.jamofront.Task;

import com.example.jamofront.Userinfo;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetResultTask {

    private static OkHttpClient client;
    private static GetResultTask instance= new GetResultTask();
    public static GetResultTask getInstance() {
        return instance;
    }


    private GetResultTask() {
        this.client = new OkHttpClient();
    }
    public static void getResult(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .addHeader("round","1")
                    .addHeader("flag","0")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getResult2(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .addHeader("round","2")
                    .addHeader("flag","0")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getResult3(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .addHeader("round","3")
                    .addHeader("flag","0")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getenhence1(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .addHeader("round","2")
                    .addHeader("flag","1")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getenhence2(Callback callback, String url) {
        try {
            Request request = new Request.Builder()
                    .addHeader("id", Userinfo.id)
                    .addHeader("round","3")
                    .addHeader("flag","1")
                    .url(url)
                    .build();

            client.newCall(request).enqueue(callback);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

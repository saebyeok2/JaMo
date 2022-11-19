package com.example.jamofront.Task;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SubmitRLTask {
    private static OkHttpClient client;
    private static SubmitRLTask instance = new SubmitRLTask();
    public static SubmitRLTask getInstance() {
        return instance;
    }
    private SubmitRLTask() {
        this.client = new OkHttpClient();
    }
    public static void submitbody(String id,String prenum, String answer,String url,Callback callback)  {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("prenum", prenum);
            jsonObject.put("answer", answer);
            RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), jsonObject.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


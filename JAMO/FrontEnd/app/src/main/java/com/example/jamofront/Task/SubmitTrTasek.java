package com.example.jamofront.Task;

import com.example.jamofront.Userinfo;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SubmitTrTasek {
    private static OkHttpClient client;
    private static SubmitTrTasek instance = new SubmitTrTasek();
    public static SubmitTrTasek getInstance() {
        return instance;
    }
    private SubmitTrTasek() {
        this.client = new OkHttpClient();
    }
    public static void submitbody(String id, String qnum, String answer, String url, Callback callback)  {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("qnum", qnum);
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

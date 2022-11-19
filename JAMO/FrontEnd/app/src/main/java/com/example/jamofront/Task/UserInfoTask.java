package com.example.jamofront.Task;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserInfoTask {
    private static OkHttpClient client;
    private static UserInfoTask instance = new UserInfoTask();
    public static UserInfoTask getInstance() {
        return instance;
    }
    private UserInfoTask() {
        this.client = new OkHttpClient();
    }
    public static void LoginBody(String id, String email, String nickname, Callback callback,String url) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("email", email);
            jsonObject.put("nickname", nickname);
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

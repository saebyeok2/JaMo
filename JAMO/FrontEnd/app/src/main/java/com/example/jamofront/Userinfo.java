package com.example.jamofront;

public class Userinfo {
    public static String id;
    public static String email;
    public static String nickname;

    public Userinfo (String id, String email, String nickname){
        this.id=id;
        this.email=email;
        this.nickname=nickname;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}

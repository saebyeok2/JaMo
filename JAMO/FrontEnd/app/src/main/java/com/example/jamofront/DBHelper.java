package com.example.jamofront;


import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "testcount.db";

    public DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME,null, DB_VERSION);
    }

    //context = getApplicationContext?
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //DB 생성시 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS TestCount (count INT NOT NULL)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS TestCount");
        onCreate(db);
    }


    // DB에 데이터 넣기
    public void InsertDB(int _count)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TestCount(count) VALUES('" +_count +"');");
    }

    public void UpdateDB(int _count)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TestCount SET count = " + _count + "");
        db.close();
    }

    public int getCount() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int count = 0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TestCount", null);
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }

        return count;
    }


    // DB전체 삭제
    public void DeleteDB()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TestCount");
    }
}


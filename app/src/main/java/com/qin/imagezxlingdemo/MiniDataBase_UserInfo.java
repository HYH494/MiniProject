package com.qin.imagezxlingdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MiniDataBase_UserInfo extends SQLiteOpenHelper {
    public MiniDataBase_UserInfo(@Nullable Context context) {
        super(context, "miniDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement," +
                "phoneNum varchar(20), passWord varchar(20), nameSet varchar(20), " +
                "fquestion varchar(20), fqanswer varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
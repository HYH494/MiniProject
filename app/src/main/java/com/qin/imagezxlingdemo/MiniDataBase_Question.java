package com.qin.imagezxlingdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


public class MiniDataBase_Question extends SQLiteOpenHelper {
    public MiniDataBase_Question(@Nullable Context context) {
        super(context, "question.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement," +
                "phoneNum varchar(20), surveyName varchar(20)," +
                "Question varchar(20), Option1 varchar(20), Option2 varchar(20), " +
                "Option3 varchar(20), Option4 varchar(20), Option5 varchar(20), " +
                "Option6 varchar(20), Option7 varchar(20), Option8 varchar(20))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
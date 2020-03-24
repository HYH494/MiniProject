package com.example.miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.List;


public class MainFace extends AppCompatActivity implements View.OnClickListener {

    RadioButton rb_main,rb_recite,rb_translate,rb_newword,rb_mine;
    int rb_now = R.id.rb_mainface;  //用于记录目前所属的界面
    int rb_temp = 0;   //用于方便操作
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);
        rb_main = (RadioButton)findViewById(R.id.rb_mainface);
        rb_recite = (RadioButton)findViewById(R.id.rb_recite);
        rb_translate = (RadioButton)findViewById(R.id.rb_translate);
        rb_newword = (RadioButton)findViewById(R.id.rb_newword);
        rb_mine = (RadioButton)findViewById(R.id.rb_mine);
        rb_main.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.rb_mainface:
                setContentView(R.layout.activity_main_face);

                break;
            case R.id.rb_recite:
                break;
            case R.id.rb_translate:
                break;
            case R.id.rb_newword:
                break;
            case R.id.rb_mine:
                break;
        }
    }
}

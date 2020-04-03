package com.qin.imagezxlingdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText e_phone_login, e_pwd_login;
    Button b_login;
    TextView t_nocnt, t_fgetPwd,t_cntshow,t_getcnt,t_clearcnt,t_register;
    String phoneNum;
    String passWord;
    String nickname;
    MiniDataBase_UserInfo miniDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e_phone_login = (EditText) findViewById(R.id.ev_lphonenum);
        e_pwd_login = (EditText) findViewById(R.id.ev_lpassword);
        b_login = (Button) findViewById(R.id.bv_lLogin);
        b_login.setOnClickListener(this);
        t_nocnt = (TextView) findViewById(R.id.tv_lnoCnt);
        t_nocnt.setOnClickListener(this);
        t_fgetPwd = (TextView) findViewById(R.id.tv_lfgetPwd);
        t_fgetPwd.setOnClickListener(this);
        t_cntshow = (TextView)findViewById(R.id.tv_lcntshow);
        t_cntshow.setMovementMethod(ScrollingMovementMethod.getInstance());
        t_getcnt = (TextView) findViewById(R.id.tv_lcheckcnt);
        t_getcnt.setOnClickListener(this);
        t_clearcnt = (TextView) findViewById(R.id.tv_lclearcnt);
        t_clearcnt.setOnClickListener(this);
        t_register = (TextView) findViewById(R.id.tv_login_register);
        t_register.setOnClickListener(this);
        miniDB = new MiniDataBase_UserInfo(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    phoneNum = intent.getStringExtra("phoneNum");
                    passWord = intent.getStringExtra("passWord");
                    e_phone_login.setText(phoneNum);
                    e_pwd_login.setText(passWord);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    phoneNum = intent.getStringExtra("phoneNum");
                    passWord = intent.getStringExtra("passWord");
                    e_phone_login.setText(phoneNum);
                    e_pwd_login.setText(passWord);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String phone = e_phone_login.getText().toString();
        String password = e_pwd_login.getText().toString();
        String password_ture = "";
        Configuration config = getBaseContext().getResources().getConfiguration();
        SQLiteDatabase db;
        switch (v.getId()) {
            case R.id.bv_lLogin:
                db = miniDB.getWritableDatabase();
                Cursor cursor = db.query("info", null,
                        null, null, null, null, null);
                if (phone.length() > 0) {
                    if (cursor.getCount() == 0) {
                        if(config.locale == Locale.CHINESE) {
                            Toast.makeText(this, "您还没有创建账户，请先注册!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "You have no account, please go to register!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        cursor.moveToFirst();
                        String temp = cursor.getString(1);
                        int count = 0;
                        if (temp.equals(phone)) {
                            password_ture = cursor.getString(2);
                            nickname = cursor.getString(3);
                            count += 1;
                        }
                        while (cursor.moveToNext()) {
                            temp = cursor.getString(1);
                            if (temp.equals(phone)) {
                                password_ture = cursor.getString(2);
                                nickname = cursor.getString(3);
                                count += 1;
                            }
                        }
                        cursor.close();
                        if (count > 0) {
                            if(password.length()>0) {
                                if (password.equals(password_ture)) {
                                    if(config.locale == Locale.CHINESE) {
                                        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(Login.this,MainFace.class);
                                    intent.putExtra("nickname",nickname);
                                    intent.putExtra("phone",phone);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (config.locale == Locale.CHINESE) {
                                        Toast.makeText(this, "密码错误，请再次尝试或找回密码！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Password incorrect, please retry or go to find password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                if(config.locale == Locale.CHINESE) {
                                    Toast.makeText(this, "密码不可为空！", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(this, "Please input password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            if(config.locale == Locale.CHINESE) {
                                Toast.makeText(this, "账号不存在，请重试或注册账号！", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(this, "The account doesn't exist, please try again or go to register!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                else {
                    if (config.locale == Locale.CHINESE) {
                        Toast.makeText(this, "未输入账号信息！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No account information input!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_lnoCnt:
            case R.id.tv_login_register:
                Intent rintent = new Intent(Login.this, Register.class);
                startActivityForResult(rintent,1);
                break;
            case R.id.tv_lfgetPwd:
                db = miniDB.getWritableDatabase();
                cursor = db.query("info", null,
                        null, null, null, null, null);
                String question = "";
                if (phone.length() > 0) {
                    if (cursor.getCount() == 0) {
                        if (config.locale == Locale.CHINESE) {
                            Toast.makeText(this, "您还没有创建账户，请先注册!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "You have no account, please go to register!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        cursor.moveToFirst();
                        String temp = cursor.getString(1);
                        int count = 0;
                        if (temp.equals(phone)) {
                            question = cursor.getString(4);
                            count += 1;
                        }
                        while (cursor.moveToNext()) {
                            temp = cursor.getString(1);
                            if (temp.equals(phone)) {
                                question = cursor.getString(4);
                                count += 1;
                            }
                        }
                        cursor.close();
                        if (count > 0) {
                            Intent fintent = new Intent(Login.this, FindPwd.class);
                            fintent.putExtra("phoneNum",phone);
                            fintent.putExtra("fquestion",question);
                            startActivityForResult(fintent,2);
                        } else {
                            if(config.locale == Locale.CHINESE) {
                                Toast.makeText(this, "账号不存在，请重试或注册账号！", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(this, "The account doesn't exist, please try again or go to register!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                else{
                    if(config.locale == Locale.CHINESE) {
                        Toast.makeText(this, "请输入要找回密码的手机号！", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Please enter the phone you want to find pwd!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_lcheckcnt:
                db = miniDB.getWritableDatabase();
                cursor = db.query("info",null,null,null,null,null,null);
                if(cursor.getCount()==0)
                    Toast.makeText(this,"No record!",Toast.LENGTH_SHORT).show();
                else{
                    cursor.moveToFirst();
                    t_cntshow.setText("phone: "+cursor.getString(1)+"\npassword: "+
                            cursor.getString(2)+"\nnameset: "+
                            cursor.getString(3)+"\nfquestion: "+
                            cursor.getString(4)+"\nfqanswer: "+
                            cursor.getString(5));
                }
                while(cursor.moveToNext()){
                    t_cntshow.append("\n\nphone: "+cursor.getString(1)+"\npassword: "+
                            cursor.getString(2)+"\nnameset: "+
                            cursor.getString(3)+"\nfquestion: "+
                            cursor.getString(4)+"\nfqanswer: "+
                            cursor.getString(5));
                }
                Toast.makeText(this,"Read successfully!",Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
                break;
            case R.id.tv_lclearcnt:
                db = miniDB.getWritableDatabase();
                db.delete("info",null,null);
                Toast.makeText(this,"清除成功!",Toast.LENGTH_SHORT).show();
                db.close();
                t_cntshow.setText("--无记录--");
                break;
        }
    }
}
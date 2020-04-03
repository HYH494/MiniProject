package com.qin.imagezxlingdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText e_phone_register, e_pwd_register, e_pwd2_register,
            e_nameset_register, e_question_register, e_qanswer_register;
    Button b_register, b_setInfo;
    TextView t_phone_register, t_pwd_register, t_pwd2_register,
            t_nameset_register, t_question_register, t_qanswer_register, t_login;
    Configuration config;
    MiniDataBase_UserInfo miniDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e_phone_register = (EditText)findViewById(R.id.ev_rphonenum);
        e_pwd_register = (EditText)findViewById(R.id.ev_rpassword);
        e_pwd2_register = (EditText)findViewById(R.id.ev_rpassword2);
        e_nameset_register = (EditText)findViewById(R.id.ev_rnameSet);
        e_question_register = (EditText)findViewById(R.id.ev_rquestion);
        e_qanswer_register = (EditText)findViewById(R.id.ev_rqanswer);

        t_phone_register = (TextView) findViewById(R.id.tv_rphonenum);
        t_pwd_register = (TextView) findViewById(R.id.tv_rpassword);
        t_pwd2_register = (TextView) findViewById(R.id.tv_rpassword2);
        t_nameset_register = (TextView) findViewById(R.id.tv_rnameSet);
        t_question_register = (TextView) findViewById(R.id.tv_rquestion);
        t_qanswer_register = (TextView) findViewById(R.id.tv_rqanswer);
        t_login = (TextView) findViewById(R.id.tv_register_login);
        t_login.setOnClickListener(this);

        b_register = (Button)findViewById(R.id.bv_rRegister);
        b_setInfo = (Button)findViewById(R.id.bv_rsetInfo);
        b_register.setOnClickListener(this);
        b_setInfo.setOnClickListener(this);
        miniDB = new MiniDataBase_UserInfo(this);
        config = getBaseContext().getResources().getConfiguration();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bv_rRegister:
                register();
                break;
            case R.id.bv_rsetInfo:
                setInfo();
                break;
            case R.id.tv_register_login:
                if(config.locale == Locale.CHINESE) {
                    Toast.makeText(this, "完成注册流程将自动跳转到登录界面", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "It'll back to login page after you finishing the register!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    };

    public void register() {
        String phone = e_phone_register.getText().toString();
        String password = e_pwd_register.getText().toString();
        String password2 = e_pwd2_register.getText().toString();
        SQLiteDatabase db;
        ContentValues values;
        if ((phone.length() > 0) && (password.length() > 0) && (password2.length() > 0)) {
            if (phone.length() == 11) {
                db = miniDB.getWritableDatabase();
                Cursor cursor = db.query("info", null,
                        null, null, null, null, null);
                if (cursor.getCount() == 0) {
                    if (password.length() < 6) {
                        if (config.locale == Locale.CHINESE) {
                            Toast.makeText(this, "建议您使用6位或8位密码！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "The password length should better be 6 or 8!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!password2.equals(password)) {
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(this, "两次输入密码不一致，请重新确认密码！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "The two password is not equal, please check and input again!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            values = new ContentValues();
                            values.put("phoneNum", phone);
                            values.put("passWord", password);
                            values.put("nameSet", "");
                            values.put("fquestion", "");
                            values.put("fqanswer", "");
                            db.insert("info", null, values);
                            db.close();
                            b_register.setBackgroundResource(R.drawable.buttonclicked);
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(this, "注册成功，请设置个人信息！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Register success, please set your information!", Toast.LENGTH_SHORT).show();
                            }
                            changeLayout();
                        }
                    }
                } else {
                    cursor.moveToFirst();
                    String temp = cursor.getString(1);
                    int count = 0;
                    if (temp.equals(phone)) {
                        count += 1;
                    }
                    while (cursor.moveToNext()) {
                        temp = cursor.getString(1);
                        if (temp.equals(phone)) {
                            count += 1;
                        }
                    }
                    cursor.close();
                    if (count > 0) {
                        if (config.locale == Locale.CHINESE) {
                            Toast.makeText(this, "账号已存在，请登录或检查账号是否正确输入！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "The account is already exist, please go to login or check your input!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (password.length() < 6) {
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(this, "建议您使用6位或8位密码！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "The password length should better be 6 or 8!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (!password2.equals(password)) {
                                if (config.locale == Locale.CHINESE) {
                                    Toast.makeText(this, "两次输入密码不一致，请重新确认密码！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "The two password is not equal, please check and input again!", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                values = new ContentValues();
                                values.put("phoneNum", phone);
                                values.put("passWord", password);
                                //values.put("nameSet", "");
                                //values.put("fquestion", "");
                                //values.put("fqanswer", "");
                                db.insert("info", null, values);
                                db.close();
                                if (config.locale == Locale.CHINESE) {
                                    Toast.makeText(this, "注册成功，请设置个人信息！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Register success, please set your information!", Toast.LENGTH_SHORT).show();
                                }
                                changeLayout();
                            }
                        }
                    }
                }
            } else {
                if (config.locale == Locale.CHINESE) {
                    Toast.makeText(this, "请输入位数为11位的手机号！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "The phone's length should be 11!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            if (config.locale == Locale.CHINESE) {
                Toast.makeText(this, "内容不可为空！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The content can't be null!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void changeLayout(){

        e_phone_register.setVisibility(View.INVISIBLE);
        e_pwd_register.setVisibility(View.INVISIBLE);
        e_pwd2_register.setVisibility(View.INVISIBLE);
        t_pwd_register.setVisibility(View.INVISIBLE);
        t_phone_register.setVisibility(View.INVISIBLE);
        t_pwd2_register.setVisibility(View.INVISIBLE);
        b_register.setVisibility(View.INVISIBLE);

        e_phone_register.setEnabled(false);
        e_pwd_register.setEnabled(false);
        e_pwd2_register.setEnabled(false);
        t_phone_register.setEnabled(false);
        t_pwd_register.setEnabled(false);
        t_pwd2_register.setEnabled(false);
        b_register.setEnabled(false);

        e_nameset_register.setVisibility(View.VISIBLE);
        e_question_register.setVisibility(View.VISIBLE);
        e_qanswer_register.setVisibility(View.VISIBLE);
        t_nameset_register.setVisibility(View.VISIBLE);
        t_question_register.setVisibility(View.VISIBLE);
        t_qanswer_register.setVisibility(View.VISIBLE);
        b_setInfo.setVisibility(View.VISIBLE);

        e_nameset_register.setEnabled(true);
        e_question_register.setEnabled(true);
        e_qanswer_register.setEnabled(true);
        t_nameset_register.setEnabled(true);
        t_question_register.setEnabled(true);
        t_qanswer_register.setEnabled(true);
        b_setInfo.setEnabled(true);
    };

    public void setInfo(){
        String phone = e_phone_register.getText().toString();
        String password = e_pwd_register.getText().toString();
        String password2 = e_pwd2_register.getText().toString();
        String nameSet = e_nameset_register.getText().toString();
        String question = e_question_register.getText().toString();
        String qanswer = e_qanswer_register.getText().toString();

        SQLiteDatabase db = miniDB.getWritableDatabase();
        ContentValues values;
        if((phone.length()>0)&&(password.length()>0)&&(password2.length()>0)) {
            values = new ContentValues();
            values.put("phoneNum", phone);
            values.put("passWord", password);
            values.put("nameSet", nameSet);
            values.put("fquestion", question);
            values.put("fqanswer", qanswer);
            //Log.i("OnTouchListener", "question:" + question + "     question—text:" +
            //        e_question_register.getText().toString());
            db.update("info",values,"phoneNum = ?",new String[]{phone});
            if (config.locale == Locale.CHINESE) {
                Toast.makeText(this,"信息设置成功，正在跳转到登录页面!",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Set information success, please waiting for loading to the login page!", Toast.LENGTH_SHORT).show();
            }
            db.close();
            Intent intent = new Intent(Register.this,Login.class);
            intent.putExtra("phoneNum",phone);
            intent.putExtra("passWord",password);
            setResult(RESULT_OK,intent);
            finish();
        }
        else {
            if (config.locale == Locale.CHINESE) {
                Toast.makeText(this, "内容不可为空！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The content can't be null!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
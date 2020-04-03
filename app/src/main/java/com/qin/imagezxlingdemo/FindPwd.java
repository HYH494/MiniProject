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

public class FindPwd extends AppCompatActivity implements View.OnClickListener {

    EditText e_qanswer_findpwd, e_pwd_findpwd, e_pwd2_findpwd;
    Button b_findpwd, b_resetpwd;
    TextView t_phone_findpwd, t_phoneshow_findpwd, t_question_findpwd, t_questionshow_findpwd,
            t_qanswer_findpwd, t_pwd_findpwd, t_pwd2_findpwd;
    MiniDataBase_UserInfo miniDB;
    Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        e_qanswer_findpwd = (EditText) findViewById(R.id.ev_fqanswer);
        e_pwd_findpwd = (EditText) findViewById(R.id.ev_fpassword);
        e_pwd2_findpwd = (EditText) findViewById(R.id.ev_fpassword2);

        t_phone_findpwd = (TextView) findViewById(R.id.tv_fphonenum);
        t_phoneshow_findpwd = (TextView) findViewById(R.id.tv_fphonenumshow);
        t_question_findpwd = (TextView) findViewById(R.id.tv_fquestion);
        t_questionshow_findpwd = (TextView) findViewById(R.id.tv_fquestionshow);
        t_qanswer_findpwd = (TextView) findViewById(R.id.tv_fqanswer);
        t_pwd_findpwd = (TextView) findViewById(R.id.tv_fpassword);
        t_pwd2_findpwd = (TextView) findViewById(R.id.tv_fpassword2);

        b_findpwd = (Button) findViewById(R.id.bv_findpwd);
        b_resetpwd = (Button) findViewById(R.id.bv_fresetpwd);
        b_findpwd.setOnClickListener(this);
        b_resetpwd.setOnClickListener(this);
        miniDB = new MiniDataBase_UserInfo(this);
        config = getBaseContext().getResources().getConfiguration();
        Intent fintent = getIntent();
        t_phoneshow_findpwd.setText(fintent.getStringExtra("phoneNum"));
        t_questionshow_findpwd.setText(fintent.getStringExtra("fquestion"));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bv_findpwd:
                findPwd();
                break;
            case R.id.bv_fresetpwd:
                resetPwd();
                break;
        }
    }

    ;

    public void findPwd() {
        String fqanswer = e_qanswer_findpwd.getText().toString();
        SQLiteDatabase db;
        if (fqanswer.length() > 0) {
            db = miniDB.getWritableDatabase();
            Cursor cursor = db.query("info", null,
                    null, null, null, null, null);
            cursor.moveToFirst();
            String temp = cursor.getString(1);
            String qanswer = "";
            if (temp.equals(t_phoneshow_findpwd.getText().toString())) {
                qanswer = cursor.getString(5);
            }
            while (cursor.moveToNext()) {
                temp = cursor.getString(1);
                if (temp.equals(t_phoneshow_findpwd.getText().toString())) {
                    qanswer = cursor.getString(5);
                }
            }
            cursor.close();
            if (fqanswer.equals(qanswer)) {
                changeLayout();
                if (config.locale == Locale.CHINESE) {
                    Toast.makeText(this, "验证成功，请重新设置密码！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Verification success, please reset your password!", Toast.LENGTH_SHORT).show();
                }
            } else{
                if (config.locale == Locale.CHINESE) {
                    Toast.makeText(this, "答案错误，请重试或返回上一页！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "The answer isn't correct, please try again or back to previous page!", Toast.LENGTH_SHORT).show();
                }
            }
        } else{
            if (config.locale == Locale.CHINESE) {
                Toast.makeText(this, "内容不可为空！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The content can't be null!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeLayout() {
        t_questionshow_findpwd.setVisibility(View.INVISIBLE);
        t_question_findpwd.setVisibility(View.INVISIBLE);
        t_qanswer_findpwd.setVisibility(View.INVISIBLE);
        e_qanswer_findpwd.setVisibility(View.INVISIBLE);
        b_findpwd.setVisibility(View.INVISIBLE);

        e_qanswer_findpwd.setEnabled(false);
        t_question_findpwd.setEnabled(false);
        t_questionshow_findpwd.setEnabled(false);
        t_qanswer_findpwd.setEnabled(false);
        b_findpwd.setEnabled(false);

        e_pwd_findpwd.setVisibility(View.VISIBLE);
        e_pwd2_findpwd.setVisibility(View.VISIBLE);
        t_pwd_findpwd.setVisibility(View.VISIBLE);
        t_pwd2_findpwd.setVisibility(View.VISIBLE);
        b_resetpwd.setVisibility(View.VISIBLE);

        e_pwd_findpwd.setEnabled(true);
        e_pwd2_findpwd.setEnabled(true);
        t_pwd_findpwd.setEnabled(true);
        t_pwd2_findpwd.setEnabled(true);
        b_resetpwd.setEnabled(true);
    }

    ;

    public void resetPwd() {
        String password = e_pwd_findpwd.getText().toString();
        String password2 = e_pwd2_findpwd.getText().toString();
        SQLiteDatabase db = miniDB.getWritableDatabase();
        ContentValues values;
        if (password.length() > 0) {
            if (password.length() > 5) {
                if (password2.equals(password)) {
                    values = new ContentValues();
                    values.put("passWord", password);
                    db.update("info", values, "phoneNum = ?", new String[]{t_phoneshow_findpwd.getText().toString()});
                    if (config.locale == Locale.CHINESE) {
                        Toast.makeText(this, "密码重置成功，正在跳转到登录页面!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Reset password success, backing to the login page!", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                    Intent intent = new Intent(FindPwd.this, Login.class);
                    intent.putExtra("phoneNum", t_phoneshow_findpwd.getText().toString());
                    intent.putExtra("passWord", password);
                    setResult(RESULT_OK, intent);
                    finish();
                } else{
                    if (config.locale == Locale.CHINESE) {
                        Toast.makeText(this, "两次输入密码不一致，请检查后重新输入！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "The two password is not equal, please check and input again!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else{
                if(config.locale == Locale.CHINESE) {
                    Toast.makeText(this, "建议您使用6位或8位密码！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "The password length should better be 6 or 8!", Toast.LENGTH_SHORT).show();
                }
            }
        } else{
            if (config.locale == Locale.CHINESE) {
                Toast.makeText(this, "密码不可为空！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The password can't be null!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

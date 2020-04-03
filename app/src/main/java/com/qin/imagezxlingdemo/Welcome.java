package com.qin.imagezxlingdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

public class Welcome extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    RadioGroup rg_welcome;
    Button b_confirm;
    CheckBox cb_sure;
    int count_Language = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        rg_welcome = (RadioGroup)findViewById(R.id.rg_welcome);
        rg_welcome.setOnCheckedChangeListener(this);
        b_confirm = (Button)findViewById(R.id.bv_welcome_confirm);
        b_confirm.setOnClickListener(this);
        cb_sure = (CheckBox)findViewById(R.id.cb_welcome_sure);
    }

    public void shiftLanguage(String sta){

        if(sta.equals("zh")){
        Locale.setDefault(Locale.ENGLISH);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = Locale.ENGLISH;
        getBaseContext().getResources().updateConfiguration(config
                     , getBaseContext().getResources().getDisplayMetrics());
        }else{
            Locale.setDefault(Locale.CHINESE);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = Locale.CHINESE;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        switch (group.getCheckedRadioButtonId()){
            case R.id.rb_welcome_chinese:
                Locale.setDefault(Locale.CHINESE);
                config.locale = Locale.CHINESE;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                count_Language += 1;
                break;
            case R.id.rb_welcome_english:
                Locale.setDefault(Locale.ENGLISH);
                config.locale = Locale.ENGLISH;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                count_Language += 1;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bv_welcome_confirm:
                if(count_Language > 0 && cb_sure.isChecked()) {
                    Intent intent = new Intent(Welcome.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this,"请选择语言并勾选同意(Please choose your language and check agree)!",Toast.LENGTH_SHORT).show();
                }
        }
    }
}

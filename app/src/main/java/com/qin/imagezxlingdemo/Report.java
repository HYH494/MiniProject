package com.qin.imagezxlingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Report extends AppCompatActivity implements View.OnClickListener{

    TextView txt_rep;
    Button b_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        txt_rep=findViewById(R.id.txt_rep);
        txt_rep.setText(Show_json.save_data+"\n"+TimeActivity.time1);
        b_finish = (Button)findViewById(R.id.btn_finish);
        b_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Report.this,MainFace.class);
        startActivity(intent);
        finish();
    }
}

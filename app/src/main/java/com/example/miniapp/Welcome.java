package com.example.miniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_welcome);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP://抬起的时候
                        onSingleTap(motionEvent);
                        break;
                }
                return true;
            }
        });
    }

    public void onSingleTap(MotionEvent e) {
        // 获取触摸点的坐标 x, y
        float x = e.getX();
        float y = e.getY();
        // 目标点的坐标
        float dst[] = new float[2];
        // 获取到ImageView的matrix
        Matrix imageMatrix = imageView.getImageMatrix();
        // 创建一个逆矩阵
        Matrix inverseMatrix = new Matrix();
        // 求逆，逆矩阵被赋值
        imageMatrix.invert(inverseMatrix);
        // 通过逆矩阵映射得到目标点 dst 的值
        inverseMatrix.mapPoints(dst, new float[]{x, y});
        // 判断dstX, dstY在Bitmap上的位置即可
        int dstX = (int) dst[0];
        int dstY = (int) dst[1];
        //Log.i("OnTouchListener", "dstX:" + dstX + "+++ dstY" + dstY);
        if((dstX>=340)&&(dstX<=720)&&(dstY>=1420)&&(dstY<=1520)) {
            imageView.setImageResource(R.mipmap.welcome_clicked);
            Intent intent = new Intent(Welcome.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

}

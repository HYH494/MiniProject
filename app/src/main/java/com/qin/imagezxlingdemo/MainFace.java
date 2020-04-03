package com.qin.imagezxlingdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.king.zxing.Intents;

import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainFace extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    RadioGroup rg_main,rg_scan,rg_create,rg_mine,rg_mine_change;
    TextView tv_create,tv_scan,tv_mine_questionnaire,tv_create_question,tv_create_option1,
            tv_create_option2,tv_create_option3,tv_create_option4,tv_create_option5,
            tv_mine_phone, tv_mine_nickname;
    EditText ev_question,ev_option1,ev_option2,ev_option3,ev_option4,ev_option5;
    Button bv_main_scan, bv_create_add, bv_create_create, bv_mine_change, bv_create_createqr;
    ImageView iv_scan_scan, iv_create_qr, iv_mine_setting;
    private String question;
    private String created;
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";

    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    public static final int RC_CAMERA = 0X01;

    public static final int RC_READ_PHOTO = 0X02;

    public static String result;

    private Class<?> cls;
    private String title;
    private boolean isContinuousScan;
    Configuration config;

    private int w,h;        //图片宽度w,高度h
    int clicktemp;
    int languegetemp;
    String qr;
    String nickname, phone;
    String nicknametemp, phonetemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face);
        rg_main = (RadioGroup)findViewById(R.id.rg_mainface);
        rg_main.setOnCheckedChangeListener(RadioClick);
        tv_create = (TextView)findViewById(R.id.tv_main_createQuenair);
        tv_create.setOnClickListener(clicked);
        tv_scan = (TextView)findViewById(R.id.tv_main_getQuenair);
        tv_scan.setOnClickListener(clicked);
        bv_main_scan = (Button)findViewById(R.id.bv_main_scan);
        bv_main_scan.setOnClickListener(clicked);
        nickname = getIntent().getStringExtra("nickname");
        nicknametemp = getIntent().getStringExtra("nickname");
        phone = getIntent().getStringExtra("phone");
        phonetemp = getIntent().getStringExtra("phone");
        created = "";
        question = "{\"survey\":{\"id\":\"12345678\",\"len\":\"1\",\"questions\":[{\"type\":\"single\",\"question\":\"";
        clicktemp = 0;
        languegetemp = 0;
        config = getBaseContext().getResources().getConfiguration();
        if (config.locale == Locale.CHINESE) {
            created = "--问卷创建记录--";
        } else {
            created = "--Questionnaire Created Record--";
        }
        qr = "{\"survey\":{\"id\":\"12345678\",\"len\":\"1\",\"questions\":[{\"type\":\"single\",\"question\":\"What mobile phone do you have?\",\"options\":[{\"1\":\"Iphone\"},{\"2\":\"Android\"}]},{\"type\":\"single\",\"question\":\"How well do the professors teach at this university?\",\"options\":[{\"1\":\"Extremely well\"},{\"2\":\"Very well\"}]},{\"type\":\"single\",\"question\":\"How effective is the teaching outside yur major at the univesrity?\",\"options\":[{\"1\":\"Extremetly effective\"},{\"2\":\"Very effective\"},{\"3\":\"Somewhat effective\"},{\"4\":\"Not so effective\"},{\"5\":\"Not at all effective\"}]}]}}\n";
    }
    private RadioGroup.OnCheckedChangeListener RadioClick=new RadioGroup.OnCheckedChangeListener () {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.rb_main_mainface:
                case R.id.rb_mine_mainface:
                case R.id.rb_createquenair_mainface:
                case R.id.rb_scan_mainface:
                    setContentView(R.layout.activity_main_face);
                    rg_main = (RadioGroup)findViewById(R.id.rg_mainface);
                    rg_main.setOnCheckedChangeListener(RadioClick);
                    tv_create = (TextView)findViewById(R.id.tv_main_createQuenair);
                    tv_create.setOnClickListener(clicked);
                    tv_scan = (TextView)findViewById(R.id.tv_main_getQuenair);
                    tv_scan.setOnClickListener(clicked);
                    bv_main_scan = (Button)findViewById(R.id.bv_main_scan);
                    bv_main_scan.setOnClickListener(clicked);
                    break;
                case R.id.rb_main_scan:
                case R.id.rb_mine_scan:
                case R.id.rb_createquenair_scan:
                case R.id.rb_scan_scan:
                    setContentView(R.layout.activity_scan);
                    iv_scan_scan = (ImageView)findViewById(R.id.iv_scan_scanbg);
                    iv_scan_scan.setOnClickListener(clicked);
                    rg_scan = (RadioGroup)findViewById(R.id.rg_scan);
                    rg_scan.setOnCheckedChangeListener(RadioClick);
                    break;
                case R.id.rb_main_create:
                case R.id.rb_scan_create:
                case R.id.rb_createquenair_create:
                case R.id.rb_mine_create:
                    setContentView(R.layout.activity_createquenair);
                    bv_create_add = (Button)findViewById(R.id.bv_create_addque);
                    bv_create_add.setOnClickListener(clicked);
                    bv_create_create = (Button)findViewById(R.id.bv_create_create);
                    bv_create_create.setOnClickListener(clicked);
                    bv_create_createqr = (Button)findViewById(R.id.bv_create_createqr);
                    bv_create_createqr.setOnClickListener(clicked);
                    tv_create_question = (TextView)findViewById(R.id.tv_create_question);
                    tv_create_option1 = (TextView)findViewById(R.id.tv_create_option1);
                    tv_create_option2 = (TextView)findViewById(R.id.tv_create_option2);
                    tv_create_option3 = (TextView)findViewById(R.id.tv_create_option3);
                    tv_create_option4 = (TextView)findViewById(R.id.tv_create_option4);
                    tv_create_option5 = (TextView)findViewById(R.id.tv_create_option5);
                    iv_create_qr = (ImageView)findViewById(R.id.iv_create_qr);
                    ev_question = (EditText) findViewById(R.id.ev_create_question);
                    ev_option1 = (EditText)findViewById(R.id.ev_create_option1);
                    ev_option2 = (EditText)findViewById(R.id.ev_create_option2);
                    ev_option3 = (EditText)findViewById(R.id.ev_create_option3);
                    ev_option4 = (EditText)findViewById(R.id.ev_create_option4);
                    ev_option5 = (EditText)findViewById(R.id.ev_create_option5);
                    rg_create = (RadioGroup)findViewById(R.id.rg_createquenair);
                    rg_create.setOnCheckedChangeListener(RadioClick);
                    break;
                case R.id.rb_main_mine:
                case R.id.rb_scan_mine:
                case R.id.rb_createquenair_mine:
                case R.id.rb_mine_mine:
                    setContentView(R.layout.activity_mine);
                    iv_mine_setting = (ImageView)findViewById(R.id.iv_mine_setting);
                    iv_mine_setting.setOnClickListener(clicked);
                    bv_mine_change = (Button) findViewById(R.id.bv_mine_changelanguage);
                    bv_mine_change.setOnClickListener(clicked);
                    tv_mine_questionnaire = (TextView) findViewById(R.id.tv_mine_questionnaire);
                    tv_mine_questionnaire.setMovementMethod(ScrollingMovementMethod.getInstance());
                    tv_mine_questionnaire.setText(created);
                    tv_mine_phone = (TextView)findViewById(R.id.tv_mine_userid);
                    phone = tv_mine_phone.getText().toString() + phone;
                    tv_mine_phone.setText(phone);
                    tv_mine_nickname = (TextView)findViewById(R.id.tv_mine_usernameset);
                    nickname = tv_mine_nickname.getText().toString() + nickname;
                    tv_mine_nickname.setText(nickname);
                    rg_mine_change = (RadioGroup)findViewById(R.id.rg_mine_language);
                    rg_mine_change.setOnCheckedChangeListener(RadioClick);
                    rg_mine = (RadioGroup)findViewById(R.id.rg_mine);
                    rg_mine.setOnCheckedChangeListener(RadioClick);
                    break;
                case R.id.rb_mine_chinese:
                    languegetemp += 1;
                    Locale.setDefault(Locale.CHINESE);
                    config.locale = Locale.CHINESE;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    break;
                case R.id.rb_mine_english:
                    languegetemp += 1;
                    Locale.setDefault(Locale.ENGLISH);
                    config.locale = Locale.ENGLISH;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    break;
            }
        }
    };

    private View.OnClickListener clicked = new View.OnClickListener() {

        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.tv_main_getQuenair:
                case R.id.bv_main_scan:
                    setContentView(R.layout.activity_scan);
                    iv_scan_scan = (ImageView)findViewById(R.id.iv_scan_scanbg);
                    iv_scan_scan.setOnClickListener(clicked);
                    rg_scan = (RadioGroup) findViewById(R.id.rg_scan);
                    rg_scan.setOnCheckedChangeListener(RadioClick);
                    break;
                case R.id.tv_main_createQuenair:
                    setContentView(R.layout.activity_createquenair);
                    bv_create_add = (Button)findViewById(R.id.bv_create_addque);
                    bv_create_add.setOnClickListener(clicked);
                    bv_create_create = (Button)findViewById(R.id.bv_create_create);
                    bv_create_create.setOnClickListener(clicked);
                    bv_create_createqr = (Button)findViewById(R.id.bv_create_createqr);
                    bv_create_createqr.setOnClickListener(clicked);
                    tv_create_question = (TextView)findViewById(R.id.tv_create_question);
                    tv_create_option1 = (TextView)findViewById(R.id.tv_create_option1);
                    tv_create_option2 = (TextView)findViewById(R.id.tv_create_option2);
                    tv_create_option3 = (TextView)findViewById(R.id.tv_create_option3);
                    tv_create_option4 = (TextView)findViewById(R.id.tv_create_option4);
                    tv_create_option5 = (TextView)findViewById(R.id.tv_create_option5);
                    iv_create_qr = (ImageView)findViewById(R.id.iv_create_qr);
                    ev_question = (EditText) findViewById(R.id.ev_create_question);
                    ev_option1 = (EditText)findViewById(R.id.ev_create_option1);
                    ev_option2 = (EditText)findViewById(R.id.ev_create_option2);
                    ev_option3 = (EditText)findViewById(R.id.ev_create_option3);
                    ev_option4 = (EditText)findViewById(R.id.ev_create_option4);
                    ev_option5 = (EditText)findViewById(R.id.ev_create_option5);
                    rg_create = (RadioGroup) findViewById(R.id.rg_createquenair);
                    rg_create.setOnCheckedChangeListener(RadioClick);
                    break;
                case R.id.iv_scan_scanbg:
                    isContinuousScan = false;
                    cls = EasyCaptureActivity.class;
                    title = "Scan to fill in the questionnaire";
                    checkCameraPermissions();
                    break;
                case R.id.bv_create_addque:
                    int optiontemp = 0;
                    String question1 = ev_question.getText().toString();
                    String option1 = ev_option1.getText().toString();
                    String option2 = ev_option2.getText().toString();
                    String option3 = ev_option3.getText().toString();
                    String option4 = ev_option4.getText().toString();
                    String option5 = ev_option5.getText().toString();

                    if(question1.length()!=0){
                        question =  question + question1 + "\",\"options\":[{" ;
                        created = created + "\nQuestion: " + question1;
                        if(option1.length()!=0){
                            optiontemp += 1;
                        }
                        if(option2.length()!=0){
                            optiontemp += 1;
                        }
                        if(option3.length()!=0){
                            optiontemp += 1;
                        }
                        if(option4.length()!=0){
                            optiontemp += 1;
                        }
                        if(option5.length()!=0){
                            optiontemp += 1;
                        }
                        if(optiontemp>1){
                            question =  question +
                                    "\"1\":\"" + option1 + "\"},{" +
                                    "\"2\":\"" + option2 + "\"},{" +
                                    "\"3\":\"" + option3 + "\"},{" +
                                    "\"4\":\"" + option4 + "\"},{" +
                                    "\"5\":\"" + option5 + "\"}]},{\"type\":\"single\",\"question\":\"";
                            created = created + "\nOption1: " + option1+
                                    "\nOption2: " + option2+
                                    "\nOption3: " + option3+
                                    "\nOption4: " + option4+
                                    "\nOption5: " + option5 + "\n";
                            ev_question.setText("");
                            ev_option1.setText("");
                            ev_option2.setText("");
                            ev_option3.setText("");
                            ev_option4.setText("");
                            ev_option5.setText("");
                        }
                        else{
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(MainFace.this,"请设置至少两个选项！",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainFace.this, "Please set at least 2 options!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        if (config.locale == Locale.CHINESE) {
                            Toast.makeText(MainFace.this,"请输入问题！",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainFace.this, "Please input the question!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.bv_create_create:
                    int optiontemp2 = 0;
                    String question1temp = ev_question.getText().toString();
                    String option1temp = ev_option1.getText().toString();
                    String option2temp = ev_option2.getText().toString();
                    String option3temp = ev_option3.getText().toString();
                    String option4temp = ev_option4.getText().toString();
                    String option5temp = ev_option5.getText().toString();
                    if(question1temp.length()!=0){
                        question =  question + question1temp + "\",\"options\":[{" ;
                        created = created + "\nQuestion: " + question1temp;
                        if(option1temp.length()!=0){
                            optiontemp2 += 1;
                        }
                        if(option2temp.length()!=0){
                            optiontemp2 += 1;
                        }
                        if(option3temp.length()!=0){
                            optiontemp2 += 1;
                        }
                        if(option4temp.length()!=0){
                            optiontemp2 += 1;
                        }
                        if(option5temp.length()!=0){
                            optiontemp2 += 1;
                        }
                        if(optiontemp2>1){
                            question =  question +
                                    "\"1\":\"" + option1temp + "\"},{" +
                                    "\"2\":\"" + option2temp + "\"},{" +
                                    "\"3\":\"" + option3temp + "\"},{" +
                                    "\"4\":\"" + option4temp + "\"},{" +
                                    "\"5\":\"" + option5temp + "\"}]}]}}\n";
                            created = created + "\nOption1: " + option1temp +
                                    "\nOption2: " + option2temp +
                                    "\nOption3: " + option3temp +
                                    "\nOption4: " + option4temp +
                                    "\nOption5: " + option5temp + "\n";
                            ev_question.setText("");
                            ev_option1.setText("");
                            ev_option2.setText("");
                            ev_option3.setText("");
                            ev_option4.setText("");
                            ev_option5.setText("");
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(MainFace.this,"问卷创建成功，您可以生成该问卷的二维码！",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainFace.this, "Create questionnaire success, you can choose to create QR code of it!", Toast.LENGTH_SHORT).show();
                            }
                            tv_create_question.setVisibility(View.INVISIBLE);
                            tv_create_option1.setVisibility(View.INVISIBLE);
                            tv_create_option2.setVisibility(View.INVISIBLE);
                            tv_create_option3.setVisibility(View.INVISIBLE);
                            tv_create_option4.setVisibility(View.INVISIBLE);
                            tv_create_option5.setVisibility(View.INVISIBLE);
                            ev_question.setVisibility(View.INVISIBLE);
                            ev_option1.setVisibility(View.INVISIBLE);
                            ev_option2.setVisibility(View.INVISIBLE);
                            ev_option3.setVisibility(View.INVISIBLE);
                            ev_option4.setVisibility(View.INVISIBLE);
                            ev_option5.setVisibility(View.INVISIBLE);
                            bv_create_add.setVisibility(View.INVISIBLE);
                            bv_create_create.setVisibility(View.INVISIBLE);
                            bv_create_createqr.setVisibility(View.VISIBLE);
                            iv_create_qr.setVisibility(View.VISIBLE);
                        }
                        else{
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(MainFace.this,"请设置至少两个选项！",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainFace.this, "Please set at least 2 options!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        if (config.locale == Locale.CHINESE) {
                            Toast.makeText(MainFace.this,"请输入问题！",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainFace.this, "Please input the question!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.bv_mine_changelanguage:
                    clicktemp += 1;
                    if (clicktemp == 1) {
                            rg_mine_change.setVisibility(View.VISIBLE);
                            bv_mine_change.setText("更改(Change)");
                        }
                    else if(clicktemp > 1){
                        if( languegetemp > 0) {
                            clicktemp = 0;
                            Intent intent = new Intent(MainFace.this,MainFace.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            nickname = nicknametemp;
                            phone = phonetemp;
                            intent.putExtra("nickname",nickname);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(MainFace.this,"修改成功，正在重新设置!",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainFace.this, "Changed success, waiting to reload!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            if (config.locale == Locale.CHINESE) {
                                Toast.makeText(MainFace.this,"请选择语言!",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainFace.this, "Please choose your language!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                case R.id.bv_create_createqr:
                    clicktemp += 1;
                    if(clicktemp == 1){
                        createQRcodeImage(question,iv_create_qr);
                        bv_create_createqr.setText("确定(Confirm)");
                    }
                    else if(clicktemp == 2){
                        clicktemp = 0;
                        tv_create_question.setVisibility(View.VISIBLE);
                        tv_create_option1.setVisibility(View.VISIBLE);
                        tv_create_option2.setVisibility(View.VISIBLE);
                        tv_create_option3.setVisibility(View.VISIBLE);
                        tv_create_option4.setVisibility(View.VISIBLE);
                        tv_create_option5.setVisibility(View.VISIBLE);
                        ev_question.setVisibility(View.VISIBLE);
                        ev_option1.setVisibility(View.VISIBLE);
                        ev_option2.setVisibility(View.VISIBLE);
                        ev_option3.setVisibility(View.VISIBLE);
                        ev_option4.setVisibility(View.VISIBLE);
                        ev_option5.setVisibility(View.VISIBLE);
                        bv_create_add.setVisibility(View.VISIBLE);
                        bv_create_create.setVisibility(View.VISIBLE);
                        bv_create_createqr.setVisibility(View.INVISIBLE);
                        iv_create_qr.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.iv_mine_setting:
                    bv_mine_change.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data!=null){
            switch (requestCode){
                case REQUEST_CODE_SCAN:
                    result = data.getStringExtra(Intents.Scan.RESULT);
                    Log.v("Log",result);

                    break;
            }

            Intent intent = new Intent(MainFace.this,
                    Show_json.class);
            startActivity(intent);
        }
    }


    private void asyncThread(Runnable runnable){
        new Thread(runnable).start();
    }
    private Context getContext(){
        return this;
    }


    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions(){
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls,title);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }
    /**
     * 扫码
     * @param cls
     * @param title
     */
    private void startScan(Class<?> cls,String title){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE,title);
        intent.putExtra(KEY_IS_CONTINUOUS,isContinuousScan);
        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }

    public void createQRcodeImage(String url, ImageView im1)
    {
        w=im1.getWidth();
        h=im1.getHeight();
        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++)
            {
                for (int x = 0; x < w; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * w + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            //显示到我们的ImageView上面
            im1.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }
}

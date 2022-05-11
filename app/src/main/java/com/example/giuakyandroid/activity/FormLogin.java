package com.example.giuakyandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.SqliteHelper;

public class FormLogin extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    Button btnLogin,btnSignIn;
    TextView txtViewForgot;
    ImageView generatorDB;
    private long time;
    Animation scaleUp, scaleDown;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        setControl();
        event();
    }

    public void setControl(){
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtViewForgot=findViewById(R.id.txtViewForgot);
        generatorDB = findViewById(R.id.imageView3);
        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);
//        db.dropAllTableAndCreateDataSet();
    }

    public void event(){
        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_UP){
                    btnLogin.startAnimation(scaleUp);

                }else if(event.getAction()==event.ACTION_DOWN){
                    btnLogin.startAnimation(scaleDown);
                }
                return false;
            }
        });
        btnSignIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_UP){
                    btnSignIn.startAnimation(scaleUp);

                }else if(event.getAction()==event.ACTION_DOWN){
                    btnSignIn.startAnimation(scaleDown);
                }
                return false;
            }
        });
        txtViewForgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_UP){
                    txtViewForgot.startAnimation(scaleUp);


                }else if(event.getAction()==event.ACTION_DOWN){
                    txtViewForgot.startAnimation(scaleDown);
                }
                return false;
            }
        });
        generatorDB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_UP){
                    txtViewForgot.startAnimation(scaleUp);
                    db.dropAllTableAndCreateDataSet();

                }else if(event.getAction()==event.ACTION_DOWN){
                    txtViewForgot.startAnimation(scaleDown);
                }
                return false;
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), FormRegister.class);
                    startActivity(i);

            }
        });
        txtViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FormForgetPassword.class);
                startActivity(i);

            }
        });
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username =  edtUsername.getText().toString().trim();
//                String password =  edtPassword.getText().toString().trim();
//
//                if(username.equals("") || username==null){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
//                    builder.setMessage("Chưa nhập username").
//                            setNegativeButton("OK",new DialogInterface.OnClickListener(){
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
//                }
//                else if(password.equals("") || password==null){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
//                    builder.setMessage("Chưa nhập password").
//                            setNegativeButton("OK",new DialogInterface.OnClickListener(){
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
//                }
//                else{
//                    if(db.checkUsername(username)){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
//                        builder.setMessage("Tài khoản đã đăng kí").
//                                setNegativeButton("OK",new DialogInterface.OnClickListener(){
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                }).show();
//                    }else {
//                        boolean insert = db.addAccount(username,password,"Guest");
//                        if (insert == true) {
//                            Account newAcc=db.getAccount(username,password);
//                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                            intent.putExtra("account",newAcc);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else{
//                            AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
//                            builder.setMessage("Thất bại! hãy thử lại").
//                                    setNegativeButton("OK",new DialogInterface.OnClickListener(){
//
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//                                    }).show();
//                        }
//                    }
//                }
//            }
//        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edtUsername.getText().toString().trim();
                String password=edtPassword.getText().toString().trim();
                if(username.equals("") || username==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
                    builder.setMessage("Chưa nhập username").
                            setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }
                else if(password.equals("") || password==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
                    builder.setMessage("Chưa nhập Password").
                            setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else{
                    boolean check=db.checkAccount(username,password);
                    if(check==false){
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormLogin.this);
                        builder.setMessage("Tài khoản không tồn tại Hoặc sai mật khẩu").
                                setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                    else{
                        Account account=db.getAccount(username,password);
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(time+2000>System.currentTimeMillis()){
            super.onBackPressed();
            finish();
        }
        Toast.makeText(getApplicationContext(),"Nhấn lần nữa để thoát",Toast.LENGTH_SHORT).show();
        time=System.currentTimeMillis();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        edtUsername.setText(null);
        edtPassword.setText(null);
    }
}
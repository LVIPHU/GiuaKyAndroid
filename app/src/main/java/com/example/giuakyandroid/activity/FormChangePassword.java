package com.example.giuakyandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormChangePassword extends AppCompatActivity {
    EditText edtEmail,edtPassword,edtConfirmPassword,edtVerifyCode;
    Button btnUpdatePassword,btnSignIn;
    String verifyCode;
    String email;
    private long time;
    Animation scaleUp, scaleDown;
    SqliteHelper db=new SqliteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_change_password);

        Intent intent = getIntent();
        verifyCode = intent.getStringExtra("verifyCode");
        email = intent.getStringExtra("email");

        setControl();
        event();
    }
    public void setControl(){
        edtVerifyCode = findViewById(R.id.edtCodeChangePW);
        edtEmail = findViewById(R.id.edtEmailChangePW);
        edtEmail.setText(email);

        edtPassword = findViewById(R.id.edtPasswordReset);
        edtConfirmPassword = findViewById(R.id.edtPasswordResetConfirm);
        btnSignIn = findViewById(R.id.btnSignInChangePW);
        btnUpdatePassword = findViewById(R.id.btnUpdatePasswordWhenReset);
        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);
//        db.dropAllTableAndCreateDataSet();


    }

    public void event() {
        btnSignIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == event.ACTION_UP) {
                    btnSignIn.startAnimation(scaleUp);
                } else if (event.getAction() == event.ACTION_DOWN) {
                    btnSignIn.startAnimation(scaleDown);
                }
                return false;
            }
        });
        btnUpdatePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == event.ACTION_UP) {
                    btnUpdatePassword.startAnimation(scaleUp);

                } else if (event.getAction() == event.ACTION_DOWN) {
                    btnUpdatePassword.startAnimation(scaleDown);
                }
                return false;
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FormLogin.class);
                startActivity(i);

            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputVerifyCode =  edtVerifyCode.getText().toString().trim();
                String inputPassword = edtPassword.getText().toString().trim();
                String inputConfirmPassword = edtConfirmPassword.getText().toString().trim();

                if(inputVerifyCode.equals("") || edtVerifyCode==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormChangePassword.this);
                    builder.setMessage("Chưa nhập code").
                            setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }else if(!inputVerifyCode.equals(verifyCode)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormChangePassword.this);
                    builder.setMessage("Code không hợp lệ.").
                            setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }else if(edtPassword.equals("") || edtPassword==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormChangePassword.this);
                    builder.setMessage("Chưa nhập password").
                            setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                } else if(edtConfirmPassword.equals("") || edtConfirmPassword==null ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormChangePassword.this);
                    builder.setMessage("Chưa confirm password").
                            setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(!inputConfirmPassword.equals(inputPassword) ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormChangePassword.this);
                    builder.setMessage("Password không trùng khớp").
                            setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else{

                    boolean check= db.checkEmail(email);
                    if(check==true) {
                        try {
                            db.updatePassword(edtConfirmPassword.getText().toString().trim(),email);
                            Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                            Account account=db.getAccountWithEmail(email,edtConfirmPassword.getText().toString().trim());
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("account",account);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }
}
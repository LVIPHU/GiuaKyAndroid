package com.example.giuakyandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.SqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class FormForgetPassword extends AppCompatActivity {
    EditText edtEmail;
    Button btnResetPassword,btnSignIn;

    private long time;
    Animation scaleUp, scaleDown;
    SqliteHelper db=new SqliteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_forget_password);
        setControl();
        event();
    }
    public void setControl(){
        edtEmail = findViewById(R.id.edtEmailResetPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnSignIn = findViewById(R.id.btnSignInReset);

        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);
//        db.dropAllTableAndCreateDataSet();
    }
    private String randomCode(){
        int min = 100000;
        int max = 999999;
        int b = (int)(Math.random()*(max-min+1)+min);
        return String.valueOf(b);
    }
    private void sendEmailToReset(String email){

        String verifyCode = randomCode();
//        SharedPreferences sharedPreferences = getSharedPreferences("ResetCode",MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//
//        String resetCode = verifyCode+","+email;
//        // write all the data entered by the user in SharedPreference and apply
//        myEdit.putString("resetcode", resetCode);
//
//        myEdit.commit();



        final String username="datletrong215@gmail.com";
        final String password="duongvu21052000";
        String messageToSend="Verify code:"+verifyCode;
        MailSender sender = new MailSender(username,
                password);
        try {
            sender.sendMail("RESET PASSWORD", messageToSend,
                    username, email);

            Toast.makeText(getApplicationContext(), "Đã gửi code reset password", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), FormChangePassword.class);
            i.putExtra("verifyCode", verifyCode);
            i.putExtra("email",email);
            startActivity(i);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Lỗi k gửi được email:"+e.toString(), Toast.LENGTH_SHORT).show();
        }
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
        btnResetPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == event.ACTION_UP) {
                    btnResetPassword.startAnimation(scaleUp);

                } else if (event.getAction() == event.ACTION_DOWN) {
                    btnResetPassword.startAnimation(scaleDown);
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

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edtEmail.getText().toString().trim();

                if(email.equals("") || email==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormForgetPassword.this);
                    builder.setMessage("Chưa nhập email").
                            setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }
                else{
                    boolean check=db.checkEmail(email);
                    if(check==false){
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormForgetPassword.this);
                        builder.setMessage("Email không tồn tại trong hệ thống").
                                setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                    else{

                        sendEmailToReset(email);

                    }
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
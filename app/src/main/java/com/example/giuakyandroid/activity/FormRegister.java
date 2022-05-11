package com.example.giuakyandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;



import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.SqliteHelper;

public class FormRegister extends AppCompatActivity {


    EditText edtUsername,edtPassword,edtEmail,edtName;
    Button btnSignUp,btnSignIn;

    private long time;
    Animation scaleUp, scaleDown;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register);
        setControl();
        event();
    }
    public void setControl(){
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtNameRegister);
        edtUsername=findViewById(R.id.edtUsernameRegister);
        edtPassword=findViewById(R.id.edtPasswordRegister);
        btnSignUp=findViewById(R.id.btnSignUpRegister);
        btnSignIn = findViewById(R.id.btnSignInRegister);

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
        btnSignUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == event.ACTION_UP) {
                    btnSignUp.startAnimation(scaleUp);

                } else if (event.getAction() == event.ACTION_DOWN) {
                    btnSignUp.startAnimation(scaleDown);
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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =  edtUsername.getText().toString().trim();
                String password =  edtPassword.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();



                if(email.equals("") || email==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                    builder.setMessage("Chưa nhập email").
                            setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else if(name.equals("") || name==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                    builder.setMessage("Chưa nhập tên").
                            setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else if(username.equals("") || username==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                    builder.setMessage("Chưa nhập username").
                            setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else if(password.equals("") || password==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                    builder.setMessage("Chưa nhập password").
                            setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else{
                    if(db.checkEmail(email)){

                        AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                        builder.setMessage("Email đã đăng kí").
                                setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                    else if(db.checkUsername(username)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                        builder.setMessage("Tài khoản đã đăng kí").
                                setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }else {
                        boolean insert = false;
                        try {
                            insert = db.addAccount(username,password,"Guest",email,name);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if (insert == true) {
                            Account newAcc=db.getAccount(username,password);
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("account",newAcc);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(FormRegister.this);
                            builder.setMessage("Thất bại! hãy thử lại").
                                    setNegativeButton("OK",new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }
                }
            }
       });
    }
}
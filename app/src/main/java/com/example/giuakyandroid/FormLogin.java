package com.example.giuakyandroid;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.SQLOutput;

public class FormLogin extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    Button btnLogin,btnSignIn;
    TextView txtViewForgot;
    private long time;
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
    }

    public void event(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =  edtUsername.getText().toString().trim();
                String password =  edtPassword.getText().toString().trim();

                if(username.equals("") || username==null){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập username", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("") || password==null){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.checkUsername(username)){
                        Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }else {
                        boolean insert = db.insertAccount(username,password);
                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        edtUsername.setText("");
                        edtPassword.setText("");
                    }
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edtUsername.getText().toString().trim();
                String password=edtPassword.getText().toString().trim();
                if(username.equals("") || username==null){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập username", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("") || password==null){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Account account=db.getAccount(username,password);
                    if(account==null){
                        System.out.println("Null");
                        Toast.makeText(getApplicationContext(),"Tài khoản không tồn tại hoặc sai mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        System.out.println("Not");
                        if(db.checkAccount(username,password)){
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("account",account);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Tài khoản không đúng. Vui lòng nhập lại!",Toast.LENGTH_SHORT).show();
                        }

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
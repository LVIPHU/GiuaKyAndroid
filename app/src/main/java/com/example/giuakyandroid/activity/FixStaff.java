package com.example.giuakyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class FixStaff extends AppCompatActivity {
    EditText edtStaffUsername, edtStaffPassword, edtStaffChucvu, edtStaffName, edtStaffEmail;

    Button btnLuu,btnHuy;
    private int position;
    private Account account;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chinhsua_staff);
        setup();
    }
    public void setup(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("NUMBER_OF_LIST");
            //The key argument here must match that used in the other activity
        }
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String usernameToGet = sharedPreferences.getString("USERNAME", null);
        Account getAccount = db.getAccountWithUsername(usernameToGet);

        ArrayList<Account> list = db.getAnAccount(getAccount);
        account=list.get(0);
        edtStaffUsername=findViewById(R.id.edtStaffUsername);
        edtStaffEmail=findViewById(R.id.edtStaffnEmail);
        edtStaffPassword=findViewById(R.id.edtStaffPassword);
        edtStaffChucvu=findViewById(R.id.edtStaffChucvu);
        edtStaffName=findViewById(R.id.edtStaffName);


        btnHuy=findViewById(R.id.btnHuy);
        btnLuu=findViewById(R.id.btnLuu);
        edtStaffUsername.setText(account.getUsername());
        edtStaffEmail.setText(account.getEmail());
        edtStaffPassword.setText(account.getPassword());
        edtStaffChucvu.setText(account.getChucVu());
        edtStaffName.setText(String.valueOf(account.getName()));
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(account);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void save(Account account){
        String fixEmail = edtStaffEmail.getText().toString();
        String fixName = edtStaffName.getText().toString();
        String fixChucVu = edtStaffChucvu.getText().toString();
        String fixPassword = edtStaffPassword.getText().toString();
        String username = edtStaffUsername.getText().toString();


        if(fixName==null || fixName.equals("")){
            Toast.makeText(this,"Vui lòng nhập tên",Toast.LENGTH_SHORT).show();
            return;
        }
        if(fixChucVu==null || fixChucVu.equals("")){
            Toast.makeText(this,"Vui lòng nhập chức vụ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(fixPassword==null || fixPassword.equals("")){
            Toast.makeText(this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }
        if(fixEmail==null || fixEmail.equals("")){
            Toast.makeText(this,"Vui lòng nhập email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(db.checkAccountWithEmailNotPassword(fixEmail) && !fixEmail.equals(account.getEmail())){
            Toast.makeText(this,"email đã có người đăng ký",Toast.LENGTH_SHORT).show();
            return;
        }




        Account findAccount = db.getAccountWithUsername(username);
        findAccount.setEmail(fixEmail);
        findAccount.setName(fixName);
        findAccount.setChucVu(fixChucVu);
        findAccount.setPassword(fixPassword);
        boolean check=checkAccount(findAccount);
        if(check==true) {
            try {
                db.updateAccountByUsername(findAccount);
                Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(){
        Toast.makeText(getApplicationContext(), "Đã hủy thao tác", Toast.LENGTH_SHORT).show();
        finish();
    }



    public boolean checkAccount(Account account){
        if(account.getName()==null || account.getName().equals("")){
        Toast.makeText(this,"Vui lòng nhập tên",Toast.LENGTH_SHORT).show();
        return false;
        }
        if(account.getChucVu()==null || account.getChucVu().equals("")){
            Toast.makeText(this,"Vui lòng nhập chức vụ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(account.getPassword()==null || account.getPassword().equals("")){
            Toast.makeText(this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

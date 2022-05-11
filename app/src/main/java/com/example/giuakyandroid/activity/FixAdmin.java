package com.example.giuakyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class FixAdmin extends AppCompatActivity {
    EditText edtAdminPassword, edtAdminChucvu, edtAdminName, edtAdminEmail;
    //edtAdminPassword edtAdminChucvu edtAdminName edtAdminEmail
    Button btnLuu,btnHuy;
    private int position;
    private Account account;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chinhsua_admin);
        setup();
    }
    public void setup(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("NUMBER_OF_LIST");
            //The key argument here must match that used in the other activity
        }
        ArrayList<Account> list = db.getAllAccount();
        account=list.get(position);
        edtAdminEmail=findViewById(R.id.edtAdminEmail);
        edtAdminPassword=findViewById(R.id.edtAdminPassword);
        edtAdminChucvu=findViewById(R.id.edtAdminChucvu);
        edtAdminName=findViewById(R.id.edtAdminName);


        btnHuy=findViewById(R.id.btnHuy);
        btnLuu=findViewById(R.id.btnLuu);
        edtAdminEmail.setText(account.getEmail());
        edtAdminPassword.setText(account.getPassword());
        edtAdminChucvu.setText(account.getChucVu());
        edtAdminName.setText(String.valueOf(account.getName()));
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
        String fixName = edtAdminName.getText().toString();
        String fixChucVu = edtAdminChucvu.getText().toString();
        String fixPassword = edtAdminPassword.getText().toString();



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

        String email = edtAdminEmail.getText().toString().trim();
        Account findAccount = db.getAccountWithEmail(email,account.getPassword());
        findAccount.setName(fixName);
        findAccount.setChucVu(fixChucVu);
        findAccount.setPassword(fixPassword);
        boolean check=checkAccount(findAccount);
        if(check==true) {
            try {
                db.updateAccount(findAccount);
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

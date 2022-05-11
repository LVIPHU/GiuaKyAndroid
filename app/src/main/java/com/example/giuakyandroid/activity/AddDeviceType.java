package com.example.giuakyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.DeviceType;
import com.example.giuakyandroid.model.SqliteHelper;

public class AddDeviceType extends AppCompatActivity {
    EditText edtMaLoai, edtTenLoai;
    Button btnLuu,btnHuy;
    SqliteHelper db= new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_them_loai_thiet_bi);
        setup();
    }

    public void setup() {
        edtMaLoai=findViewById(R.id.edt_maLoai);
        edtTenLoai=findViewById(R.id.edt_tenLoai);
        btnLuu=findViewById(R.id.btnLuu);
        btnHuy=findViewById(R.id.btnHuy);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    void save() {
        DeviceType deviceType=new DeviceType(edtMaLoai.getText().toString().trim(),
                edtTenLoai.getText().toString().trim());
        boolean check=checkDeviceType(deviceType);
        if(check==true) {
            try {
                db.addDeviceType(deviceType);
                Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                resetField();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void cancel(){
        Toast.makeText(getApplicationContext(), "Hủy thêm loại thiết bị", Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean checkDeviceType(DeviceType deviceType){
        if(deviceType.maLoai==null || deviceType.maLoai.equals("")){
            Toast.makeText(this,"Vui lòng nhập mã loại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(db.checkMaLoaiThietBi(deviceType.maLoai)){
            Toast.makeText(this,"Mã loại đã tồn tại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(deviceType.tenLoai==null || deviceType.tenLoai.equals("")){
            Toast.makeText(this,"Vui lòng nhập tên loại",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void resetField(){
        edtMaLoai.setText("");
        edtTenLoai.setText("");
    }
}
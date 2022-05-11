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

import java.util.ArrayList;

public class FixDeviceType extends AppCompatActivity {
    EditText edtMaLoai, edtTenLoai;
    Button btnLuu,btnHuy;
    SqliteHelper db= new SqliteHelper(this);
    private int position;
    private DeviceType deviceType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chinhsua_loai_thiet_bi);
        setup();
    }

    public void setup() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("NUMBER_OF_LIST");
            //The key argument here must match that used in the other activity
        }
        ArrayList<DeviceType> list = db.getAllDeviceType();
        deviceType=list.get(position);
        edtMaLoai=findViewById(R.id.edtMaLoai_DT);
        edtTenLoai=findViewById(R.id.edtTenLoai_DT);
        btnHuy=findViewById(R.id.btnHuy);
        btnLuu=findViewById(R.id.btnLuu);

        edtMaLoai.setText(deviceType.getMaLoai());
        edtTenLoai.setText(deviceType.getTenLoai());

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
                db.updateDeviceType(deviceType);
                Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                finish();
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
        if(deviceType.tenLoai==null || deviceType.tenLoai.equals("")){
            Toast.makeText(this,"Vui lòng nhập tên loại",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

package com.example.giuakyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

public class AddRoom extends AppCompatActivity {
    EditText edtMaPhong, edtTenPhong, edtTang;
    Button btnLuu,btnHuy;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_them_phong);
        setup();
    }

    public void setup(){
        edtMaPhong=findViewById(R.id.edt_maPhong);
        edtTenPhong=findViewById(R.id.edt_tenPhong);
        edtTang=findViewById(R.id.edt_tang);
        btnHuy=findViewById(R.id.btnHuy);
        btnLuu=findViewById(R.id.btnLuu);
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

    public void save(){
        if(edtTang.getText().toString()==null || edtTang.getText().toString().equals("")){
            Toast.makeText(this,"Vui lòng nhập tầng",Toast.LENGTH_SHORT).show();
            return;
        }
        int soTang;
        try{
            soTang = Integer.parseInt(edtTang.getText().toString().trim());
        }
        catch(Exception e){
            Toast.makeText(this,"Lỗi định dạng",Toast.LENGTH_SHORT).show();
            return;
        }
        if(soTang<0 || soTang>5){
            Toast.makeText(this,"Số tầng không hợp lệ",Toast.LENGTH_SHORT).show();
            return;
        }
        Room room=new Room(edtMaPhong.getText().toString().trim(),
                edtTenPhong.getText().toString().trim(),
                soTang);
        boolean check=checkRoom(room);
        if(check==true) {
            try {
                db.addRoom(room);
                Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                resetField();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(){
        Toast.makeText(getApplicationContext(), "Hủy thêm phòng học", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void resetField(){
        edtMaPhong.setText("");
        edtTenPhong.setText("");
        edtTang.setText("");
    }

    public boolean checkRoom(Room room){
        if(room.maPhong==null || room.maPhong.equals("")){
            Toast.makeText(this,"Vui lòng nhập mã phòng",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(db.checkMaPhong(room.maPhong)){
            Toast.makeText(this,"Mã phòng đã tồn tại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(room.loaiPhong==null || room.loaiPhong.equals("")){
            Toast.makeText(this,"Vui lòng nhập loại phòng",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(room.tang<0 || room.tang>10){
            Toast.makeText(this,"Tầng không hợp lệ",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
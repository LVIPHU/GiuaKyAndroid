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

import java.util.ArrayList;

public class FixRoom extends AppCompatActivity {
    EditText edtMaPhong, edtTenPhong, edtTang;
    Button btnLuu,btnHuy;
    private int position;
    private Room room;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chinhsua_phong);
        setup();
    }
    public void setup(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("NUMBER_OF_LIST");
            //The key argument here must match that used in the other activity
        }
        ArrayList<Room> list = db.getAllRoom();
        room=list.get(position);
        edtMaPhong=findViewById(R.id.edtMaPhong_R);
        edtTenPhong=findViewById(R.id.edtTenPhong_R);
        edtTang=findViewById(R.id.edtTang_R);

        btnHuy=findViewById(R.id.btnHuy);
        btnLuu=findViewById(R.id.btnLuu);

        edtMaPhong.setText(room.getMaPhong());
        edtTenPhong.setText(room.getLoaiPhong());
        edtTang.setText(String.valueOf(room.getTang()));
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
            Toast.makeText(this,"Vui l??ng nh???p t???ng",Toast.LENGTH_SHORT).show();
            return;
        }
        int soTang;
        try{
            soTang = Integer.parseInt(edtTang.getText().toString().trim());
        }
        catch(Exception e){
            Toast.makeText(this,"L???i ?????nh d???ng",Toast.LENGTH_SHORT).show();
            return;
        }
        if(soTang<0 || soTang>5){
            Toast.makeText(this,"S??? t???ng kh??ng h???p l???",Toast.LENGTH_SHORT).show();
            return;
        }
        Room room=new Room(edtMaPhong.getText().toString().trim(),
                edtTenPhong.getText().toString().trim(),
                soTang);
        boolean check=checkRoom(room);
        if(check==true) {
            try {
                db.updateRoom(room);
                Toast.makeText(this, "L??u th??nh c??ng", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(){
        Toast.makeText(getApplicationContext(), "H???y th??m ph??ng h???c", Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean checkRoom(Room room){
        if(room.loaiPhong==null || room.loaiPhong.equals("")){
            Toast.makeText(this,"Vui l??ng nh???p lo???i ph??ng",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(room.tang<0 || room.tang>10){
            Toast.makeText(this,"T???ng kh??ng h???p l???",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

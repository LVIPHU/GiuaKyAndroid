package com.example.giuakyandroid.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Device;
import com.example.giuakyandroid.model.DeviceType;
import com.example.giuakyandroid.model.SqliteHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FixDevice extends AppCompatActivity {
    EditText edtFixMaTB,edtFixTenTB,edtFixXuatXu;
    ImageView imgView;
    Spinner spinnerLoaiThietBi;
    ArrayList<DeviceType> listType;
    ArrayList<String> listName;
    AppCompatButton btnHuy, btnLuu;
    SqliteHelper db=new SqliteHelper(this);
    private int position;
    private Device device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chinhsua_thiet_bi);
        setup();
    }

    public void setup(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("NUMBER_OF_LIST");
            //The key argument here must match that used in the other activity
        }
        ArrayList<Device> list = db.getAllDevice();
        device=list.get(position);
        listType=db.getAllDeviceType();
        listName=new ArrayList<>();
        for(int i=0;i<listType.size();i++){
            listName.add(listType.get(i).tenLoai);
        }
        spinnerLoaiThietBi=findViewById(R.id.spnLoaiTB);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listName);
        spinnerLoaiThietBi.setAdapter(adapter);
        imgView=findViewById(R.id.imgViewSuaTB);
        edtFixMaTB = findViewById(R.id.edtFixMaTB);
        edtFixTenTB = findViewById(R.id.edtFixTenTB);
        edtFixXuatXu = findViewById(R.id.edtFixXuatXu);
        btnHuy=findViewById(R.id.btnHuy_Fix);
        btnLuu=findViewById(R.id.btnLuu_Fix);
        edtFixMaTB.setText(device.getMaTB());
        edtFixTenTB.setText(device.getTenTB());
        edtFixXuatXu.setText(device.getXuatXu());
        byte[] imgByte=device.getImg();
        if(imgByte!=null) {
            try {
                Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                imgView.setImageBitmap(imgBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                imgView.setImageResource(R.drawable.question_mark);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDeviceImage();
            }
        });
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
        Device dev = new Device();
        dev.setMaTB(edtFixMaTB.getText().toString());
        dev.setTenTB(edtFixTenTB.getText().toString().trim());
        dev.setXuatXu(edtFixXuatXu.getText().toString().trim());
        byte[] imgByteArray=null;
        if(!imgView.getDrawable().equals(R.drawable.question_mark)){
            Bitmap imgBitmap=((BitmapDrawable) imgView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            imgByteArray=byteArrayOutputStream.toByteArray();
        }
        dev.setImg(imgByteArray);
        dev.setMaLoai(listType.get(spinnerLoaiThietBi.getSelectedItemPosition()).getMaLoai());
        boolean check=checkDevice(dev);
        if(check==true){
            db.updateDevice(dev);
            System.out.println("Lưu");
            Toast.makeText(this.getApplicationContext(),"Sửa thành công",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void cancel(){
        Toast.makeText(getApplicationContext(), "Hủy sửa thiết bị", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void selectDeviceImage(){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if (grantResults.length>0 || grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                System.out.println("Đang chọn");
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2 && resultCode== Activity.RESULT_OK && data!=null){
            Uri imgSelected=data.getData();
            try{
                Bitmap imgBitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),imgSelected);
                imgView.setImageBitmap(imgBitmap);
            }
            catch(Exception e){
                Toast.makeText(this,"Lỗi hình ảnh",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public boolean checkDevice(Device device){
        if(device.maTB==null || device.maTB.equals("")){
            Toast.makeText(this.getApplicationContext(), "Vui lòng nhập mã thiết bị", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.tenTB==null || device.tenTB.equals("")){
            Toast.makeText(this.getApplicationContext(),"Vui lòng nhập tên thiết bị",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.xuatXu==null || device.xuatXu.equals("")){
            Toast.makeText(this.getApplicationContext(),"Vui lòng nhập xuất xứ",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.maLoai==null || device.maLoai.equals("")){
            Toast.makeText(this.getApplicationContext(),"Vui lòng chọn loại thiết bị",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}

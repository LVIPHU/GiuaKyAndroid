package com.example.giuakyandroid.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

public class AddDevice extends AppCompatActivity {
    EditText edtThemMaTB,edtThemTenTB,edtThemXuatXu;
    ImageView imgView;
    Spinner spinnerLoaiThietBi;
    ArrayList<DeviceType> listType;
    ArrayList<String> listName;
    AppCompatButton btnHuy, btnLuu;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_them_thiet_bi);
        setup();
    }


    public void setup(){
        listType=db.getAllDeviceType();
        listName=new ArrayList<>();
        for(int i=0;i<listType.size();i++){
            listName.add(listType.get(i).tenLoai);
        }
        spinnerLoaiThietBi=findViewById(R.id.spinnerLoaiThietBi);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listName);
        spinnerLoaiThietBi.setAdapter(adapter);
        imgView=findViewById(R.id.imgViewThemTB);
        imgView.setImageResource(R.drawable.question_mark);
        edtThemMaTB = findViewById(R.id.edtThemMaTB);
        edtThemTenTB = findViewById(R.id.edtThemTenTB);
        edtThemXuatXu = findViewById(R.id.edtThemXuatXu);
        btnHuy=findViewById(R.id.btnHuy);
        btnLuu=findViewById(R.id.btnLuu);
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
        int position = spinnerLoaiThietBi.getSelectedItemPosition();
        DeviceType deviceType = listType.get(position);
        imgView = findViewById(R.id.imgViewThemTB);
        byte[] imgByteArray=null;
        if(!imgView.getDrawable().equals(R.drawable.question_mark)){
            Bitmap imgBitmap=((BitmapDrawable) imgView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            imgByteArray=byteArrayOutputStream.toByteArray();
        }

        Device device=new Device(edtThemMaTB.getText().toString().trim(),
                edtThemTenTB.getText().toString().trim(),
                edtThemXuatXu.getText().toString().trim(),
                (deviceType.getMaLoai()), imgByteArray);
        boolean check=checkDevice(device);
        if(check==true) {
            try {
                db.addDevice(device);
                Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                resetField();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(){
        Toast.makeText(getApplicationContext(), "Hủy thêm thiết bị", Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean checkDevice(Device device){
        if(device.maTB==null || device.maTB.equals("")){
            Toast.makeText(this, "Vui lòng nhập mã thiết bị", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(db.checkMaThietBi(device.maTB)){
            Toast.makeText(this,"Mã thiết bị đã tồn tại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.tenTB==null || device.tenTB.equals("")){
            Toast.makeText(this,"Vui lòng nhập tên thiết bị",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.xuatXu==null || device.xuatXu.equals("")){
            Toast.makeText(this,"Vui lòng nhập xuất xứ",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.maLoai==null || device.maLoai.equals("")){
            Toast.makeText(this,"Vui lòng chọn loại thiết bị",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void resetField(){
        edtThemMaTB.setText("");
        edtThemTenTB.setText("");
        edtThemXuatXu.setText("");
        imgView.setImageResource(R.drawable.question_mark);
        spinnerLoaiThietBi.setSelection(0);
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
}

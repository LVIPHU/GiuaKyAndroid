package com.example.giuakyandroid;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private long time;
    SqliteHelper db=new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=this.getIntent();
        Account account=intent.getParcelableExtra("account");
        TextView txtViewUsername=findViewById(R.id.txtViewUsername);
        TextView txtViewChucVu=findViewById(R.id.txtViewChucVu);
        txtViewUsername.setText("Xin chào: "+account.username);
        txtViewChucVu.setText("Chức vụ: "+account.chucVu);

        bottomNav=findViewById(R.id.bottmNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch(item.getItemId()){
                    case R.id.device:{
                        fragment=new DeviceFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.type:{
                        fragment=new TypeFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.room:{
                        fragment=new RoomFragment();
                        loadFragment(fragment);
                        return true;
                    }
                }
                return false;
            }
        });

<<<<<<< Updated upstream
=======
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Mượn thiết bị");
        builder.setView(view);

        Spinner spinnerMaPhongMuonTB=findViewById(R.id.spinnerMaPhongMuonTB);
        Spinner spinnerTenTBMuonTB=findViewById(R.id.spinnerTenTBMuonTB);

        ArrayList<Device> listDevice=db.getAllDevice();
        ArrayList<String> listTemp1=new ArrayList<>();
        for(int i=0;i<listDevice.size();i++){
            listTemp1.add(listDevice.get(i).tenTB);
        }

        ArrayList<Room> listRoom=db.getAllRoom();
        ArrayList<String> listTemp2=new ArrayList<>();
        for(int i=0;i<listRoom.size();i++){
            listTemp2.add(listRoom.get(i).maPhong);
        }
        ArrayAdapter adapterDevice=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listTemp1);
        ArrayAdapter adapterRoom=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listTemp2);

        spinnerMaPhongMuonTB.setAdapter(adapterRoom);
        spinnerTenTBMuonTB.setAdapter(adapterDevice);

        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edtTextSLMuonTB=findViewById(R.id.edtTextSLMuonTB);

                if(edtTextSLMuonTB.getText().toString()==null || edtTextSLMuonTB.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập số lượng",Toast.LENGTH_SHORT).show();
                    return;
                }
                int SL=Integer.parseInt(edtTextSLMuonTB.getText().toString());
                if (SL<0 || SL>5){
                    Toast.makeText(MainActivity.this,"Số lượng không hợp lệ",Toast.LENGTH_SHORT).show();
                    return;
                }

                Detail detail=new Detail();
                detail.setMaPhong(spinnerMaPhongMuonTB.getSelectedItem().toString());
                detail.setMaTB(listDevice.get(spinnerTenTBMuonTB.getSelectedItemPosition()).maTB);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    detail.setNgayMuon(LocalDate.now().toString());
                }
                detail.setSoLuong(Integer.parseInt(edtTextSLMuonTB.getText().toString()));
                detail.setUsername(username);

                try {
                    db.addDetail(detail);
                    rec_detail_adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Hủy mượn thiết bị",Toast.LENGTH_SHORT).show();
            }
        });
>>>>>>> Stashed changes
    }

<<<<<<< Updated upstream
    private void loadFragment(Fragment fragment){
        FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.frameLayout,fragment);
        trans.addToBackStack(null);
        trans.commit();
=======

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.corner_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_device:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                builder.setTitle("Thêm thiết bị");
                View view = inflater.inflate(R.layout.dialog_them_thiet_bi, null);
                Spinner spinnerLoaiThietBi=view.findViewById(R.id.spinnerLoaiThietBi);

                ArrayList<DeviceType> listType=db.getAllDeviceType();
                ArrayList<String> listName=new ArrayList<>();
                for(int i=0;i<listType.size();i++){
                    listName.add(listType.get(i).tenLoai);
                }
                ArrayList<DeviceType> temp=new ArrayList<>();
                ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,listName);
                spinnerLoaiThietBi.setAdapter(adapter);

                builder.setView(view);
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtThemMaTB,edtThemTenTB,edtThemXuatXu;
                        edtThemMaTB=view.findViewById(R.id.edtThemMaTB);
                        edtThemTenTB=view.findViewById(R.id.edtThemTenTB);
                        edtThemXuatXu=view.findViewById(R.id.edtThemXuatXu);
                        DeviceType deviceType=(DeviceType) spinnerLoaiThietBi.getSelectedItem();

                        Device device=new Device(edtThemMaTB.getText().toString().trim(),
                                                edtThemTenTB.getText().toString().trim(),
                                                edtThemXuatXu.getText().toString().trim(),
                                                ((DeviceType) spinnerLoaiThietBi.getSelectedItem()).maLoai.toString());
                        boolean check=checkDevice(device);
                        if(check==true) {
                            try {
                                db.addDevice(device);
                                rec_device_adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Huỷ thêm thiết bị",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
            case R.id.menu_add_deviceType:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                builder.setTitle("Thêm Loại thiết bị");
                View view = inflater.inflate(R.layout.dialog_them_loai_thiet_bi, null);
                builder.setView(view);
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtMaLoai, edtTenLoai;
                        edtMaLoai=view.findViewById(R.id.edt_maLoai);
                        edtTenLoai=view.findViewById(R.id.edt_tenLoai);
                        DeviceType deviceType=new DeviceType(edtMaLoai.getText().toString().trim(),
                                                            edtTenLoai.getText().toString().trim());
                        boolean check=checkDeviceType(deviceType);
                        if(check==true) {
                            try {
                                db.addDeviceType(deviceType);
                                rec_deviceType_adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Huỷ thêm loại thiết bị",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
            case R.id.menu_add_room:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                builder.setTitle("Thêm phòng");
                View view = inflater.inflate(R.layout.dialog_them_phong, null);
                builder.setView(view);
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtMaPhong, edtTenPhong, edtTang;
                        edtMaPhong=view.findViewById(R.id.edt_maPhong);
                        edtTenPhong=view.findViewById(R.id.edt_tenPhong);
                        edtTang=view.findViewById(R.id.edt_tang);

                        String maPhong, tenPhong, tang;
                        maPhong = edtMaPhong.getText().toString().trim();
                        tenPhong = edtTenPhong.getText().toString().trim();
                        tang = edtTang.getText().toString().trim();



                        if(edtTang.getText().toString()==null || edtTang.getText().toString().equals("")){
                            Toast.makeText(builder.getContext(),"Vui lòng nhập tầng",Toast.LENGTH_SHORT).show();
                            System.out.println("bla bla");
                            return;
                        }
                        int soTang = Integer.parseInt(edtTang.getText().toString().trim());
                        Room room=new Room(edtMaPhong.getText().toString().trim(),
                                            edtTenPhong.getText().toString().trim(),
                                            soTang);
                        System.out.println(room.maPhong);
                        System.out.println(room.tang);
                        System.out.println(room.loaiPhong);
                        boolean check=checkRoom(room);
                        if(check==true) {
                            try {
                                db.addRoom(room);
                                rec_room_adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                                System.out.println("đã lưu");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Huỷ thêm phòng học",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
        }
        return true;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======

    public boolean checkDevice(Device device){
        if(device.maTB==null || device.maTB.equals("")){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mã thiết bị", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(db.getDevice(device.maTB).maTB!=null || !db.getDevice(device.maTB).maTB.equals("")){
            Toast.makeText(getApplicationContext(),"Mã thiết bị đã tồn tại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.tenTB==null || device.tenTB.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập tên thiết bị",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.xuatXu==null || device.xuatXu.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập xuất xứ",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(device.maLoai==null || device.maLoai.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng chọn loại thiết bị",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean checkDeviceType(DeviceType deviceType){
        if(deviceType.maLoai==null || deviceType.maLoai.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mã loại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(db.getDeviceType(deviceType.maLoai).maLoai!=null || !db.getDeviceType(deviceType.maLoai).equals("")){
            Toast.makeText(getApplicationContext(),"Mã loại đã tồn tại",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(deviceType.tenLoai==null || deviceType.tenLoai.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập tên loại",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean checkRoom(Room room){
        if(room.maPhong==null || room.maPhong.equals("")){
            Toast.makeText(this,"Vui lòng nhập mã phòng",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(db.getRoom(room.maPhong)!=null || !db.getRoom(room.maPhong).equals("")){
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
>>>>>>> Stashed changes
}
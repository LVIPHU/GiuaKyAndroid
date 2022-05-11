package com.example.giuakyandroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.example.giuakyandroid.R;
import com.example.giuakyandroid.adapter.Rec_Admin_Adapter;
import com.example.giuakyandroid.adapter.Rec_Detail_Adapter;
import com.example.giuakyandroid.adapter.Rec_DeviceType_Adapter;
import com.example.giuakyandroid.adapter.Rec_Device_Adapter;
import com.example.giuakyandroid.adapter.Rec_Room_Adapter;
import com.example.giuakyandroid.adapter.Rec_Setting;
import com.example.giuakyandroid.adapter.SpinnerDevice;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.Detail;
import com.example.giuakyandroid.model.Device;
import com.example.giuakyandroid.model.DeviceType;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private RecyclerView recyclerView;
    private Rec_Device_Adapter rec_device_adapter;
    private Rec_DeviceType_Adapter rec_deviceType_adapter;
    private Rec_Room_Adapter rec_room_adapter;
    private Rec_Detail_Adapter rec_detail_adapter;
    private Rec_Admin_Adapter rec_admin_adapter;
    private Rec_Setting rec_setting;

    private long time;
    private String username;
    SqliteHelper db=new SqliteHelper(this);
    int current=0;
    private String uriTemp=null;
    Account mainAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        Account account = intent.getParcelableExtra("account");
        mainAccount = account;
        TextView txtViewUsername = findViewById(R.id.txtViewUsername);
        TextView txtViewChucVu = findViewById(R.id.txtViewChucVu);
        recyclerView = findViewById(R.id.recView);
        txtViewUsername.setText("Xin chào: "+account.name);
        txtViewChucVu.setText("Chức vụ: "+account.chucVu);
        username = account.username;
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERNAME",username);
        editor.apply();



        getListDevice();
        bottomNav=findViewById(R.id.bottmNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.device:{
                        getListDevice();
                        current=1;
                        return true;
                    }
                    case R.id.type:{
                        getListDeviceType();
                        current=2;
                        return true;
                    }
                    case R.id.room:{
                        getListRoom();
                        current=3;
                        return true;
                    }
                    case R.id.borrow:{
                        getListDetail();
                        current=4;
                        return true;
                    }
                    case R.id.setting:{
                        if(mainAccount.getChucVu().equals("Sys admin")){
                            getAdmin();
                        }else{
                            getSetting();
                        }
                        current=5;
                        return true;

                    }
                }
                return false;
            }
        });
        final Activity activity = this;

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, FormLogin.class);
               activity.startActivities(new Intent[]{intent});


            }
        });



        Button btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_muon_thiet_bi,null);

                Spinner spinnerMaPhongMuonTB = view.findViewById(R.id.spinnerMaPhongMuonTB);
                Spinner spinnerTenTBMuonTB = view.findViewById(R.id.spinnerTenTBMuonTB);

                ArrayList<Device> listDevice=db.getAllDevice();
                ArrayList<Room> listRoom=db.getAllRoom();
                ArrayList<String> listTemp2=new ArrayList<>();

                for(int i=0;i<listRoom.size();i++){
                    listTemp2.add(listRoom.get(i).maPhong);
                }

                SpinnerDevice adapterDevice=new SpinnerDevice(getApplicationContext(),listDevice);
                ArrayAdapter adapterRoom=new ArrayAdapter(MainActivity.this,android.R.layout.simple_dropdown_item_1line,listTemp2);
                spinnerMaPhongMuonTB.setAdapter(adapterRoom);
                spinnerTenTBMuonTB.setAdapter(adapterDevice);

                AlertDialog alert=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Mượn thiết bị")
                        .setView(view)
                        .setPositiveButton("Lưu",null)
                        .setNegativeButton("Hủy",null)
                        .create();

                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alert.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText edtTextSLMuonTB = view.findViewById(R.id.edtTextSLMuonTB);

                                if(edtTextSLMuonTB.getText().toString()==null || edtTextSLMuonTB.getText().toString().equals("")){
                                    Toast.makeText(MainActivity.this,"Vui lòng nhập số lượng",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                int SL=Integer.parseInt(edtTextSLMuonTB.getText().toString());
                                if (SL<0 || SL>6){
                                    Toast.makeText(MainActivity.this,"Số lượng không hợp lệ",Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Detail detail=new Detail();
                                detail.setMaPhong(spinnerMaPhongMuonTB.getSelectedItem().toString());
                                Device deviceSelected=(Device)spinnerTenTBMuonTB.getSelectedItem();
                                System.out.println(deviceSelected);
                                detail.setMaTB(deviceSelected.getMaTB());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    detail.setNgayMuon(LocalDate.now().toString());
                                }
                                detail.setSoLuong(Integer.parseInt(edtTextSLMuonTB.getText().toString()));
                                detail.setUsername(username);

                                try {
                                    db.addDetail(detail);
                                    Toast.makeText(MainActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    bottomNav.setSelectedItemId(R.id.borrow);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Hủy mượn thiết bị",Toast.LENGTH_SHORT).show();
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();
            }
        });


        Button btnChart=findViewById(R.id.btnChart);
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChartActivity.class);
                activity.startActivities(new Intent[]{intent});
            }
        });

        Button btnMail=findViewById(R.id.btnMail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MailActivity.class);
                activity.startActivities(new Intent[]{intent});
            }
        });
    }




    public void getSetting(){
        ArrayList<Account> listAccount=new ArrayList<>();
        listAccount=db.getAnAccount(mainAccount);
        rec_setting = new Rec_Setting(MainActivity.this,listAccount);
        recyclerView.setAdapter(rec_setting);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    public void getAdmin(){
        ArrayList<Account> listAccount=new ArrayList<>();
        listAccount=db.getAllAccount();
        rec_admin_adapter = new Rec_Admin_Adapter(MainActivity.this,listAccount);
        recyclerView.setAdapter(rec_admin_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    public void getListDevice(){
        ArrayList<Device> listDevice=new ArrayList<>();
        listDevice=db.getAllDevice();
        rec_device_adapter = new Rec_Device_Adapter(MainActivity.this,listDevice);
        recyclerView.setAdapter(rec_device_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void getListDeviceType(){
        ArrayList<DeviceType> listType=new ArrayList<>();
        listType=db.getAllDeviceType();
        rec_deviceType_adapter = new Rec_DeviceType_Adapter(MainActivity.this,listType);
        recyclerView.setAdapter(rec_deviceType_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void getListRoom(){
        ArrayList<Room> listRoom=new ArrayList<>();
        listRoom=db.getAllRoom();
        rec_room_adapter = new Rec_Room_Adapter(MainActivity.this,listRoom);
        recyclerView.setAdapter(rec_room_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void getListDetail(){
        ArrayList<Detail> listDetail=new ArrayList<>();
        listDetail=db.getAllDetailNotYetReturn();
        rec_detail_adapter = new Rec_Detail_Adapter(MainActivity.this,listDetail);
        recyclerView.setAdapter(rec_detail_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


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
                final Activity activity = this;
                Intent intent = new Intent(activity, AddDevice.class);
                startActivity(intent);
                current=1;
                return true;
            }
            case R.id.menu_add_deviceType:{
                final Activity activity = this;
                Intent intent = new Intent(activity, AddDeviceType.class);
                startActivity(intent);
                current=2;
                return true;
            }
            case R.id.menu_add_room:{
                final Activity activity = this;
                Intent intent = new Intent(activity, AddRoom.class);
                startActivity(intent);
                current=3;
                return true;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(time+2000>System.currentTimeMillis()){
            super.onBackPressed();
            finish();

        }
        Toast.makeText(MainActivity.this,"Nhấn lần nữa để thoát",Toast.LENGTH_SHORT).show();
        time=System.currentTimeMillis();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        switch(current){
            case 1:{
                getListDevice();
                bottomNav.setSelectedItemId(R.id.device);
                break;
            }
            case 2:{
                getListDeviceType();
                bottomNav.setSelectedItemId(R.id.type);
                break;
            }
            case 3:{
                getListRoom();
                bottomNav.setSelectedItemId(R.id.room);
                break;
            }
            case 4:{
                getListDetail();
                bottomNav.setSelectedItemId(R.id.borrow);
                break;
            }
        }
    }
}
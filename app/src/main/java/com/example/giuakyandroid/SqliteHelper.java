package com.example.giuakyandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int dbVer=1;

    public SqliteHelper(Context context)  {
        super(context, "QLTB", null, dbVer);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS LoaiThietBi (MALOAI NVARCHAR PRIMARY KEY, TENLOAI NVARCHAR)");
            db.execSQL("CREATE TABLE IF NOT EXISTS ThietBi (MATB NVARCHAR PRIMARY KEY, TENTB NVARCHAR," +
                    "XUATXU NVARCHAR, MALOAI NVARCHAR," +
                    "FOREIGN KEY (MALOAI) REFERENCES LoaiThietBi(MALOAI))");
            db.execSQL("CREATE TABLE IF NOT EXISTS ChiTietSuDung (MAPHONG NVARCHAR, MATB NVARCHAR," +
                    "NGAYSUDUNG NVARCHAR, NGAYTRA NVARCHAR, SOLUONG INT, USERNAME NVARCHAR," +
                    "PRIMARY KEY (MAPHONG,MATB)," +
                    "FOREIGN KEY (MAPHONG) REFERENCES PhongHoc(MAPHONG)," +
                    "FOREIGN KEY (MATB) REFERENCES ThietBi(MATB)," +
                    "FOREIGN KEY (USERNAME) REFERENCES TaiKhoan(USERNAME))");
            db.execSQL("CREATE TABLE IF NOT EXISTS PhongHoc (MAPHONG NVARCHAR PRIMARY KEY, LOAIPHONG NVARCHAR," +
                    "TANG INT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS TaiKhoan (USERNAME NVARCHAR PRIMARY KEY, PASSWORD NVARCHAR," +
                    "CHUCVU NVARCHAR)");
        }
        catch(Exception e){
            System.out.println("Lỗi tạo DB");
            System.out.println(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LOAITHIETBI");
        db.execSQL("DROP TABLE IF EXISTS THIETBI");
        db.execSQL("DROP TABLE IF EXISTS PHONGHOC");
        db.execSQL("DROP TABLE IF EXISTS CHITIETSUDUNG");
        db.execSQL("DROP TABLE IF EXISTS ACCOUNT");

        // Recreate
        onCreate(db);
    }

    public Account getAccount(String username, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From TaiKhoan where USERNAME=? AND PASSWORD=?",
                new String[]{username,password});
        System.out.println("Ready");
        cursor.moveToFirst();
        Account account=new Account();
        if(cursor.getCount()<1){
            return account;
        }
        else{
            account.setUsername(cursor.getString(0));
            account.setPassword(cursor.getString(1));
            account.setChucVu(cursor.getString(2));
            return account;
        }
    }
    public boolean insertAccount(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",username);
        contentValues.put("PASSWORD",password);
        long result = myDB.insert("TaiKhoan",null,contentValues);
        if(result==-1) return false;
        else return true;
    }

    public boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where USERNAME = ?",new String []{username});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkMaThietBi(String maTB){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from ThietBi where MATB = ?",new String []{maTB});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkMaLoaiThietBi(String maLoaiTB){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from LoaiThietBi where MALOAI = ?",new String []{maLoaiTB});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkMaPhong(String maPhong){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from PhongHoc where MAPHONG = ?",new String []{maPhong});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkAccount(String username,String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where USERNAME = ? and PASSWORD = ?",new String []{username,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public void addDevice (Device device){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MATB",device.maTB);
        values.put("TENTB",device.tenTB);
        values.put("XUATXU",device.xuatXu);
        values.put("MALOAI",device.maLoai);
        db.insert("THIETBI",null,values);
        db.close();
    }

    public void addDeviceType (DeviceType deviceType){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MALOAI",deviceType.maLoai);
        values.put("TENLOAI",deviceType.tenLoai);
        db.insert("LOAITHIETBI",null,values);
        db.close();
    }

    public void addDetail (Detail detail){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MAPHONG",detail.maPhong);
        values.put("MATB",detail.maTB);
        values.put("NGAYMUON",detail.ngayMuon);
        values.put("NGAYTRA",detail.ngayTra);
        values.put("SOLUONG",detail.soLuong);
        values.put("USERNAME",detail.username);
        db.insert("ChiTietSuDung",null,values);
        db.close();
    }

    public void addRoom (Room room){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MAPHONG",room.maPhong);
        values.put("LOAIPHONG",room.loaiPhong);
        values.put("TANG",room.tang);
        db.insert("PhongHoc",null,values);
        db.close();
    }

    public Device getDevice(String maTB){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From ThietBi where MATB=?",new String[]{maTB});
        cursor.moveToFirst();
        Device device = new Device();
        device.maTB=cursor.getString(cursor.getColumnIndex("MATB"));
        device.tenTB=cursor.getString(cursor.getColumnIndex("TENTB"));
        device.xuatXu=cursor.getString(cursor.getColumnIndex("XUATXU"));
        device.maLoai=cursor.getString(cursor.getColumnIndex("MALOAI"));
        return device;
    }

    public ArrayList<Detail> getAllDetail(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Detail> list=new ArrayList<>();
        Detail detail=new Detail();
        Cursor cursor=db.rawQuery("Select * From ChiTietSuDung",null);
        cursor.moveToFirst();
        while(cursor!=null){
            detail.maPhong=cursor.getString(cursor.getColumnIndex("MAPHONG"));
            detail.maTB=cursor.getString(cursor.getColumnIndex("MATB"));
            detail.ngayMuon=cursor.getString(cursor.getColumnIndex("NGAYMUON"));
            detail.ngayTra=cursor.getString(cursor.getColumnIndex("NGAYTRA"));
            detail.soLuong=Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG")));
            detail.username=cursor.getString(cursor.getColumnIndex("USERNAME"));
            list.add(detail);
            detail=null;
            cursor.moveToNext();
        }
        return list;
    }
}

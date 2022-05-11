package com.example.giuakyandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int dbVer=2;

    public SqliteHelper(Context context)  {
        super(context, "QLTB", null, dbVer);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS LoaiThietBi (MALOAI NVARCHAR PRIMARY KEY, TENLOAI NVARCHAR)");
            db.execSQL("CREATE TABLE IF NOT EXISTS ThietBi (MATB NVARCHAR PRIMARY KEY, TENTB NVARCHAR," +
                    "XUATXU NVARCHAR, MALOAI NVARCHAR,IMG BLOB," +
                    "FOREIGN KEY (MALOAI) REFERENCES LoaiThietBi(MALOAI))");
            db.execSQL("CREATE TABLE IF NOT EXISTS ChiTietSuDung (ID INT PRIMARY KEY,MAPHONG NVARCHAR, MATB NVARCHAR," +
                    "NGAYMUON NVARCHAR, NGAYTRA NVARCHAR, SOLUONG INT, USERNAME NVARCHAR," +
                    "FOREIGN KEY (MAPHONG) REFERENCES PhongHoc(MAPHONG)," +
                    "FOREIGN KEY (MATB) REFERENCES ThietBi(MATB)," +
                    "FOREIGN KEY (USERNAME) REFERENCES TaiKhoan(USERNAME))");
            db.execSQL("CREATE TABLE IF NOT EXISTS PhongHoc (MAPHONG NVARCHAR PRIMARY KEY, LOAIPHONG NVARCHAR," +
                    "TANG INT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS TaiKhoan (USERNAME NVARCHAR PRIMARY KEY, PASSWORD NVARCHAR," +
                    "CHUCVU NVARCHAR,EMAIL NVARCHAR,NAME NVARCHAR)");




        }
        catch(Exception e){
            System.out.println("Lỗi tạo DB");
            System.out.println(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LoaiThietBi");
        db.execSQL("DROP TABLE IF EXISTS ThietBi");
        db.execSQL("DROP TABLE IF EXISTS PhongHoc");
        db.execSQL("DROP TABLE IF EXISTS ChiTietSuDung");
        db.execSQL("DROP TABLE IF EXISTS TaiKhoan");

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
            account.setEmail(cursor.getString(3));
            account.setName(cursor.getString(4));
            return account;
        }
    }





    public Account getAccountWithEmail(String email, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From TaiKhoan where EMAIL=? AND PASSWORD=?",
                new String[]{email,password});
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
            account.setEmail(cursor.getString(3));
            account.setName(cursor.getString(4));
            return account;
        }
    }

    public Account getAccountWithUsername(String username){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From TaiKhoan where USERNAME=?",
                new String[]{username});
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
            account.setEmail(cursor.getString(3));
            account.setName(cursor.getString(4));
            return account;
        }
    }





    public boolean addAccount(String username, String password, String chucvu,String email, String name){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",username);
        contentValues.put("PASSWORD",password);
        contentValues.put("CHUCVU",chucvu);
        contentValues.put("EMAIL",email);
        contentValues.put("NAME",name);
        long result = myDB.insert("TaiKhoan",null,contentValues);
        if(result==-1) return false;
        else return true;
    }
    public void updatePassword(String password, String email){
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor cursor=db.rawQuery("Select * from TaiKhoan where EMAIL = ?",new String []{email});
        cursor.moveToFirst();
        Account accountOld = new Account();
        if(cursor.getCount()>0) {

            accountOld.setUsername(cursor.getString(0));
            accountOld.setPassword(cursor.getString(1));
            accountOld.setChucVu(cursor.getString(2));
            accountOld.setEmail(cursor.getString(3));
            accountOld.setName(cursor.getString(4));
        }
        cursor.close();
        ContentValues values=new ContentValues();
        values.put("USERNAME",accountOld.getUsername());
        values.put("PASSWORD",password);
        values.put("CHUCVU",accountOld.getChucVu());
        values.put("EMAIL",accountOld.getEmail());
        values.put("NAME",accountOld.getName());
        db.update("TaiKhoan",values,"EMAIL=?",new String[]{email});
        db.close();




    }

    public void updateAccountByUsername(Account account){
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor cursor=db.rawQuery("Select * from TaiKhoan where USERNAME = ?",new String []{account.getUsername()});
        cursor.moveToFirst();
        Account accountOld = new Account();
        if(cursor.getCount()>0) {

            accountOld.setUsername(cursor.getString(0));
            accountOld.setPassword(cursor.getString(1));
            accountOld.setChucVu(cursor.getString(2));
            accountOld.setEmail(cursor.getString(3));
            accountOld.setName(cursor.getString(4));
        }
        cursor.close();
        ContentValues values=new ContentValues();
        values.put("USERNAME",accountOld.getUsername());
        values.put("PASSWORD",account.getPassword());
        values.put("CHUCVU",account.getChucVu());
        values.put("EMAIL",account.getEmail());
        values.put("NAME",account.getName());
        db.update("TaiKhoan",values,"EMAIL=?",new String[]{account.getEmail()});
        db.close();



    }



    public void updateAccount(Account account){
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor cursor=db.rawQuery("Select * from TaiKhoan where EMAIL = ?",new String []{account.getEmail()});
        cursor.moveToFirst();
        Account accountOld = new Account();
        if(cursor.getCount()>0) {

            accountOld.setUsername(cursor.getString(0));
            accountOld.setPassword(cursor.getString(1));
            accountOld.setChucVu(cursor.getString(2));
            accountOld.setEmail(cursor.getString(3));
            accountOld.setName(cursor.getString(4));
        }
        cursor.close();
        ContentValues values=new ContentValues();
        values.put("USERNAME",accountOld.getUsername());
        values.put("PASSWORD",account.getPassword());
        values.put("CHUCVU",account.getChucVu());
        values.put("EMAIL",account.getEmail());
        values.put("NAME",account.getName());
        db.update("TaiKhoan",values,"EMAIL=?",new String[]{account.getEmail()});
        db.close();



    }


    public boolean checkEmail(String email){
        SQLiteDatabase myDB = this.getWritableDatabase();
        try{
            Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where EMAIL = ?",new String []{email});
            if(cursor.getCount()>0) return true;
           // else return false;
        }catch (Exception E){
            E.printStackTrace();
            return false;
        }
        return false;


    }


    public boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where USERNAME = ?",new String []{username});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkAccount(String username,String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where USERNAME = ? and PASSWORD = ?",new String []{username,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public boolean checkAccountWithEmail(String email,String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where EMAIL = ? and PASSWORD = ?",new String []{email,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkAccountWithEmailNotPassword(String email){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from TaiKhoan where EMAIL = ? ",new String []{email});
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
        values.put("IMG",device.getImg());
        db.insert("ThietBi",null,values);
        db.close();
    }

    public void addDeviceType (DeviceType deviceType){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MALOAI",deviceType.maLoai);
        values.put("TENLOAI",deviceType.tenLoai);
        db.insert("LoaiThietBi",null,values);
        db.close();
    }

    public void addDetail (Detail detail){
        SQLiteDatabase db=this.getWritableDatabase();

        Detail detailCheck=new Detail();
        detailCheck=getDetailNotReturn(detail);

        ContentValues values=new ContentValues();
        values.put("MAPHONG",detail.maPhong);
        values.put("MATB",detail.maTB);
        values.put("NGAYMUON",detail.ngayMuon);
        values.put("NGAYTRA",detail.ngayTra);
        if(detailCheck.maTB!=null){
            values.put("SOLUONG",detail.soLuong+detailCheck.getSoLuong());
            values.put("USERNAME",detail.username);
            db.update("ChiTietSuDung",values,"MATB=? and MAPHONG =? and NGAYTRA is null",
                                        new String[] {detail.getMaTB(),detail.getMaPhong()});
        }
        else{
            values.put("SOLUONG",detail.soLuong);
            values.put("USERNAME",detail.username);
            db.insert("ChiTietSuDung",null,values);
        }
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
        if(cursor.getCount()>0) {
            device.setMaTB(cursor.getString(cursor.getColumnIndex("MATB")));
            device.setTenTB(cursor.getString(cursor.getColumnIndex("TENTB")));
            device.setXuatXu(cursor.getString(cursor.getColumnIndex("XUATXU")));
            device.setMaLoai(cursor.getString(cursor.getColumnIndex("MALOAI")));
            device.setImg(cursor.getBlob(cursor.getColumnIndex("IMG")));
        }
        cursor.close();
        return device;
    }

    public DeviceType getDeviceType(String maLoai){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From LoaiThietBi where MALOAI=?",new String[]{maLoai});
        cursor.moveToFirst();
        DeviceType deviceType = new DeviceType();
        if(cursor.getCount()>0) {
            deviceType.setMaLoai(cursor.getString(cursor.getColumnIndex("MALOAI")));
            deviceType.setTenLoai(cursor.getString(cursor.getColumnIndex("TENLOAI")));
        }
        cursor.close();
        return deviceType;
    }

    public Room getRoom(String maPhong){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From PhongHoc where MAPHONG=?",new String[]{maPhong});
        cursor.moveToFirst();
        Room room = new Room();
        if(cursor.getCount()>0) {
            room.setMaPhong(cursor.getString(cursor.getColumnIndex("MAPHONG")));
            room.setLoaiPhong(cursor.getString(cursor.getColumnIndex("LOAIPHONG")));
            room.setTang(cursor.getInt(cursor.getColumnIndex("TANG")));
        }
        cursor.close();
        return room;
    }

    public ArrayList<Device> getAllDevice(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Device> list =new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From ThietBi",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {
            Device device=new Device();
            device.setMaTB(cursor.getString(cursor.getColumnIndex("MATB")));
            device.setTenTB(cursor.getString(cursor.getColumnIndex("TENTB")));
            device.setXuatXu(cursor.getString(cursor.getColumnIndex("XUATXU")));
            device.setMaLoai(cursor.getString(cursor.getColumnIndex("MALOAI")));
            device.setImg(cursor.getBlob(cursor.getColumnIndex("IMG")));
            list.add(device);
            } while (cursor.moveToNext() && cursor!=null);
        }
        cursor.close();
        return list;
    }
    public ArrayList<Account> getAllAccount(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Account> listAccount =new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From Taikhoan where CHUCVU NOT LIKE '%Sys%'",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {


                Account account=new Account();
                account.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                account.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
                account.setChucVu(cursor.getString(cursor.getColumnIndex("CHUCVU")));
                account.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                account.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                listAccount.add(account);
            } while (cursor.moveToNext() && cursor!=null);
        }
        cursor.close();
        return listAccount;
    }

    public ArrayList<Account> getAnAccount(Account requestAccount){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Account> listAccount =new ArrayList<>();

        Cursor cursor=db.rawQuery("Select * From Taikhoan where CHUCVU NOT LIKE '%Sys%' AND USERNAME=?",new String[]{requestAccount.getUsername()});
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {


                Account account=new Account();
                account.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                account.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
                account.setChucVu(cursor.getString(cursor.getColumnIndex("CHUCVU")));
                account.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                account.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                listAccount.add(account);
            } while (cursor.moveToNext() && cursor!=null);
        }
        cursor.close();
        return listAccount;
    }




    public ArrayList<DeviceType> getAllDeviceType(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<DeviceType> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From LoaiThietBi",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {
                DeviceType deviceType=new DeviceType();
                deviceType.setMaLoai(cursor.getString(cursor.getColumnIndex("MALOAI")));
                deviceType.setTenLoai(cursor.getString(cursor.getColumnIndex("TENLOAI")));
                list.add(deviceType);
            } while (cursor.moveToNext() && cursor!=null);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Room> getAllRoom(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Room> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From PhongHoc",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {
                Room room=new Room();
                room.setMaPhong(cursor.getString(cursor.getColumnIndex("MAPHONG")));
                room.setLoaiPhong(cursor.getString(cursor.getColumnIndex("LOAIPHONG")));
                room.setTang(cursor.getInt(cursor.getColumnIndex("TANG")));
                list.add(room);
            } while (cursor.moveToNext() && cursor != null);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail> getAllDetail(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Detail> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From ChiTietSuDung",null);
        cursor.moveToPosition(2);
        if(cursor.getCount()>0) {
            do {
                Detail detail=new Detail();
                detail.setMaPhong(cursor.getString(cursor.getColumnIndex("MAPHONG")));
                detail.setMaTB(cursor.getString(cursor.getColumnIndex("MATB")));
                detail.setNgayMuon(cursor.getString(cursor.getColumnIndex("NGAYMUON")));
                detail.setNgayTra(cursor.getString(cursor.getColumnIndex("NGAYTRA")));
                detail.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG"))));
                detail.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                list.add(detail);
            } while (cursor.moveToNext() && cursor != null);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Detail> getAllDetailNotYetReturn(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Detail> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From ChiTietSuDung Where NGAYTRA is null",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {
                Detail detail=new Detail();
                detail.setMaPhong(cursor.getString(cursor.getColumnIndex("MAPHONG")));
                detail.setMaTB(cursor.getString(cursor.getColumnIndex("MATB")));
                detail.setNgayMuon(cursor.getString(cursor.getColumnIndex("NGAYMUON")));
                detail.setNgayTra(cursor.getString(cursor.getColumnIndex("NGAYTRA")));
                detail.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG"))));
                detail.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                list.add(detail);
            } while (cursor.moveToNext() && cursor != null);
        }
        cursor.close();
        return list;
    }
    public ArrayList<Detail> getAllDetailByMaTB(String maTB){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Detail> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * From ChiTietSuDung Where MATB=?",new String[]{maTB});
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {
                Detail detail=new Detail();
                detail.setMaPhong(cursor.getString(cursor.getColumnIndex("MAPHONG")));
                detail.setMaTB(cursor.getString(cursor.getColumnIndex("MATB")));
                detail.setNgayMuon(cursor.getString(cursor.getColumnIndex("NGAYMUON")));
                detail.setNgayTra(cursor.getString(cursor.getColumnIndex("NGAYTRA")));
                detail.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG"))));
                detail.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                list.add(detail);
            } while (cursor.moveToNext() && cursor != null);
        }
        cursor.close();
        return list;
    }

    public Detail getDetailNotReturn(Detail detail){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From ChiTietSuDung Where MATB=? and MAPHONG =? and NGAYTRA is null",
                            new String[] {detail.getMaTB(),detail.getMaPhong()});
        cursor.moveToFirst();
        Detail detailCheck=new Detail();
        if(cursor.getCount()>0) {
                detailCheck.setMaPhong(cursor.getString(cursor.getColumnIndex("MAPHONG")));
                detailCheck.setMaTB(cursor.getString(cursor.getColumnIndex("MATB")));
                detailCheck.setNgayMuon(cursor.getString(cursor.getColumnIndex("NGAYMUON")));
                detailCheck.setNgayTra(cursor.getString(cursor.getColumnIndex("NGAYTRA")));
                detailCheck.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG"))));
                detailCheck.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
        }
        cursor.close();
        return detailCheck;
    }

    public void dropAllTableAndCreateDataSet() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("ThietBi",null,null);
        db.delete("LoaiThietBi",null,null);
        db.delete("ChiTietSuDung",null,null);
        db.delete("PhongHoc",null,null);
        db.delete("TaiKhoan",null,null);
        addDeviceType(new DeviceType("1","Micro"));
        addDeviceType(new DeviceType("2","Loa"));
        addDeviceType(new DeviceType("3","Khoá"));
        addRoom(new Room("1","Phòng học",1));
        addRoom(new Room("2","Phòng họp",2));
        addRoom(new Room("3","Phòng đoàn",3));
        addDevice(new Device("1","microphone","vietnam","1",null));
        addDevice(new Device("2","loa ngoài","vietnam","2",null));
        addDevice(new Device("3","khoá sắt","mĩ","3",null));
        addAccount("admin","admin","Sys admin","admin@gmail.com","admin");
        addAccount("ledat","123456","staff","datletrong215@gmail.com","Le Trong Dat");

    }

    public ArrayList<Statistic> statistic(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Statistic> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select MaTB,Count(MaTB) From ChiTietSuDung where NGAYTRA is not null " +
                "Group By MaTB ORDER BY Count(MATB) DESC LIMIT 10",null);
        cursor.moveToFirst();
        System.out.println(cursor.getCount());
        if(cursor.getCount()>0){
            do{
                Statistic sta=new Statistic();
                sta.setDevice(getDevice(cursor.getString(cursor.getColumnIndex("MATB"))));
                sta.setCount(cursor.getInt(1));
                System.out.println(sta.getDevice().getMaTB());
                System.out.println(sta.getCount());
                list.add(sta);
            }while(cursor.moveToNext() && cursor!=null);
        }
        cursor.close();
        System.out.println(list.size());
        return list;
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

    public boolean checkDeviceForDelete(Device device){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from ChiTietSuDung where MATB = ?",new String []{device.getMaTB()});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    public boolean checkDeviceTypeForDelete(DeviceType deviceType){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from ThietBi where MALOAI = ?",new String []{deviceType.getMaLoai()});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    public boolean checkRoomForDelete(Room room){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from ChiTietSuDung where MAPHONG = ?",new String []{room.getMaPhong()});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    public boolean checkAccountForDelete(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from ChiTietSuDung where USERNAME = ? ",new String []{account.getUsername()});
        if(cursor.getCount()>0) return false;
        else return true;
    }

//    public boolean checkAccountForDelete(Account account){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  *\n" +
//                "     FROM Taikhoan\n" +
//                " where Taikhoan.USERNAME   not in  (SELECT  ChiTietSuDung.USERNAME\n" +
//                "     FROM ChiTietSuDung\n" +
//                "     LEFT JOIN Taikhoan\n" +
//                "     ON ChiTietSuDung.USERNAME = Taikhoan.USERNAME)",new String []{room.getMaPhong()});
//        if(cursor.getCount()>0) return false;
//        else return true;
//    }


    public void returnDevice(Detail detail){
        SQLiteDatabase db=this.getWritableDatabase();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            detail.setNgayTra(LocalDate.now().toString());
        }
        ContentValues values=new ContentValues();
        values.put("MAPHONG",detail.maPhong);
        values.put("MATB",detail.maTB);
        values.put("NGAYMUON",detail.ngayMuon);
        values.put("NGAYTRA",detail.ngayTra);
        values.put("SOLUONG",detail.soLuong);
        values.put("USERNAME",detail.username + ": Đã trả.");
        db.update("ChiTietSuDung",values,"MAPHONG='"+detail.getMaPhong() +"' and MATB='"+detail.getMaTB()+"'",null);
        db.close();
    }

    public void updateDevice(Device device){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MATB",device.getMaTB());
        values.put("TENTB",device.getTenTB());
        values.put("XUATXU",device.getXuatXu());
        values.put("MALOAI",device.getMaLoai());
        values.put("IMG",device.getImg());
        db.update("ThietBi",values,"MATB=?",new String[]{device.getMaTB()});
        db.close();
    }

    public boolean deleteDevice(Device device){
        SQLiteDatabase db=this.getWritableDatabase();
        if(checkDeviceForDelete(device)==true){
            db.delete("ThietBi","MATB=?",new String[]{device.getMaTB()});
            return true;
        }
        db.close();
        return false;
    }

    public void updateDeviceType(DeviceType deviceType){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MALOAI",deviceType.getMaLoai());
        values.put("TENLOAI",deviceType.getTenLoai());
        db.update("LoaiThietBi",values,"MALOAI='"+deviceType.getMaLoai()+"'",null);
        db.close();
    }

    public boolean deleteDeviceType(DeviceType deviceType){
        SQLiteDatabase db=this.getWritableDatabase();
        if(checkDeviceTypeForDelete(deviceType)==true){
            db.delete("LoaiThietBi","MALOAI='"+deviceType.getMaLoai()+"'",null);
            return true;
        }
        db.close();
        return false;
    }

    public void updateRoom(Room room){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MAPHONG",room.getMaPhong());
        values.put("LOAIPHONG",room.getLoaiPhong());
        values.put("TANG",room.getTang());
        db.update("PhongHoc",values,"MAPHONG='"+room.getMaPhong()+"'",null);
        db.close();
    }


    public boolean deleteRoom(Room room){
        SQLiteDatabase db=this.getWritableDatabase();
        if(checkRoomForDelete(room)==true){
            db.delete("PhongHoc","MAPHONG='"+room.getMaPhong()+"'",null);
            return true;
        }
        db.close();
        return false;
    }
    public boolean deleteAccount(Account account){
        SQLiteDatabase db=this.getWritableDatabase();

        if(checkAccountForDelete(account)==true){
            db.delete("Taikhoan","EMAIL='"+account.getEmail()+"'",null);
            db.close();
            return true;
        }
        db.close();
        return false;



    }



    public int getCurrentDetailID(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from ChiTietSuDung",null);
        cursor.moveToLast();
        int id=cursor.getInt(0);
        return id;
    }
}

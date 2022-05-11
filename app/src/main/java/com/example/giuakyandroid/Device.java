package com.example.giuakyandroid;

public class Device {
    public String maTB;
    public String tenTB;
    public String xuatXu;
    public String maLoai;

    public Device() {
    }

    public Device(String maTB, String tenTB, String xuatXu, String maLoai) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.xuatXu = xuatXu;
        this.maLoai = maLoai;
    }

    public String getmaTB() {
        return maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setmaTB(String maTB) {
        this.maTB = maTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }
}

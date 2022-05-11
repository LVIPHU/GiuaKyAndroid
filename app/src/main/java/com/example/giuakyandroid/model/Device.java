package com.example.giuakyandroid.model;

public class Device {
    public String maTB;
    public String tenTB;
    public String xuatXu;
    public String maLoai;
    public byte[] img;

    public Device() {
    }

    public Device(String maTB, String tenTB, String xuatXu, String maLoai, byte[] img) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.xuatXu = xuatXu;
        this.maLoai = maLoai;
        this.img = img;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}

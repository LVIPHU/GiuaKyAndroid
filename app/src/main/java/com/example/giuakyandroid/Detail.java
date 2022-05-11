package com.example.giuakyandroid;

public class Detail {
    public String maPhong;
    public String maTB;
    public String ngayMuon;
    public String ngayTra;
    public int soLuong;
    public String username;

    public Detail() {
    }

    public Detail(String maPhong, String maTB, String ngayMuon, String ngayTra, int soLuong, String username) {
        this.maPhong = maPhong;
        this.maTB = maTB;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.soLuong = soLuong;
        this.username = username;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

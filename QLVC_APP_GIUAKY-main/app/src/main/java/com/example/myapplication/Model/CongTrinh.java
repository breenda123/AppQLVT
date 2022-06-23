package com.example.myapplication.Model;

public class CongTrinh {
    String maCT, tenCT, diaChi;

    public CongTrinh() {
    }

    public CongTrinh(String maCT, String tenCT, String diaChi) {
        this.maCT = maCT;
        this.tenCT = tenCT;
        this.diaChi = diaChi;
    }

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public String getTenCT() {
        return tenCT;
    }

    public void setTenCT(String tenCT) {
        this.tenCT = tenCT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return getTenCT();
    }
}

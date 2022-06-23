package com.example.myapplication.Model;

public class PhieuVanChuyen {
    String maPVC, ngayVC, maCT;


    public PhieuVanChuyen(String string) {
    }

    public PhieuVanChuyen(String maPVC, String ngayVC, String maCT) {
        this.maPVC = maPVC;
        this.ngayVC = ngayVC;
        this.maCT = maCT;
    }

    public String getMaPVC() {
        return maPVC;
    }

    public void setMaPVC(String maPVC) {
        this.maPVC = maPVC;
    }

    public String getNgayVC() {
        return ngayVC;
    }

    public void setNgayVC(String ngayVC) {
        this.ngayVC = ngayVC;
    }

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    @Override
    public String toString() {
        return getMaPVC();
    }
}

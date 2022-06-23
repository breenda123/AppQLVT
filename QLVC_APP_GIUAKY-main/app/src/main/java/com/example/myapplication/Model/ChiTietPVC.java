package com.example.myapplication.Model;

public class ChiTietPVC {
    String maPVC;
    int maVt, soLuong, cuLy;

    public ChiTietPVC() {
    }

    public ChiTietPVC(String maPVC, int maVt, int soLuong, int cuLy) {
        this.maPVC = maPVC;
        this.maVt = maVt;
        this.soLuong = soLuong;
        this.cuLy = cuLy;
    }

    public String getMaPVC() {
        return maPVC;
    }

    public void setMaPVC(String maPVC) {
        this.maPVC = maPVC;
    }

    public int getMaVt() {
        return maVt;
    }

    public void setMaVt(int maVt) {
        this.maVt = maVt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getCuLy() {
        return cuLy;
    }

    public void setCuLy(int cuLy) {
        this.cuLy = cuLy;
    }

    @Override
    public String toString() {
        return "ChiTietPVC{" +
                "maPVC='" + maPVC + '\'' +
                ", maVt='" + maVt + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", cuLy='" + cuLy + '\'' +
                '}';
    }
}

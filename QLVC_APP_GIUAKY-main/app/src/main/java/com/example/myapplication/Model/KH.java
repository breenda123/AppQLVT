package com.example.myapplication.Model;

import java.io.Serializable;

public class KH implements Serializable {
    int maKH;
    String tenKH, email,sdt;

    public KH() {
    }

    public KH(int maKH, String tenKH, String email, String sdt) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.email = email;
        this.sdt = sdt;
    }


    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "KH{}";
    }
}

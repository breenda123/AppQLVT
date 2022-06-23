package com.example.myapplication.Model;

public class VatTu {
    byte[] hinh;
    int maVT;
    String tenVt, dvTinh;
    Float giaVc;

    public VatTu() {
    }

    public VatTu(int maVT, String tenVt, String dvTinh, Float giaVc, byte[] hinh) {
        this.hinh = hinh;
        this.maVT = maVT;
        this.tenVt = tenVt;
        this.dvTinh = dvTinh;
        this.giaVc = giaVc;
    }

    public int getMaVt() {
        return maVT;
    }

    public void setMaVt(int maVt) {
        this.maVT = maVt;
    }

    public String getTenVt() {
        return tenVt;
    }

    public void setTenVt(String tenVt) {
        this.tenVt = tenVt;
    }

    public String getDvTinh() {
        return dvTinh;
    }

    public void setDvTinh(String dvTinh) {
        this.dvTinh = dvTinh;
    }


    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public Float getGiaVc() {
        return giaVc;
    }

    public void setGiaVc(Float giaVc) {
        this.giaVc = giaVc;
    }

    @Override
    public String toString() {
        return getTenVt();
    }
}

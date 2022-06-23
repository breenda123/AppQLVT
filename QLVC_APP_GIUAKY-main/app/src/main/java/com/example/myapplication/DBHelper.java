package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.ChiTietPVC;
import com.example.myapplication.Model.CongTrinh;
import com.example.myapplication.Model.KH;
import com.example.myapplication.Model.PhieuVanChuyen;
import com.example.myapplication.Model.VatTu;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
// factory con trỏ dùng để duyêt dữ liệu
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
//duyệt từng dòng trong database ,trả dữ liệu về dạng con trỏ
    public Cursor GetData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    void InsertVT(String tenVT, String dvTinh, Float giaVC, byte[] hinh) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into vattu values (null,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenVT);
        statement.bindString(2, dvTinh);
        statement.bindDouble(3, giaVC);
        statement.bindBlob(4, hinh);
        statement.executeInsert();
    }
    void InsertKH(String hoTenKH, String Email,String Sdt) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into KH values (null,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, hoTenKH);
        statement.bindString(2, Email);
        statement.bindString(3, Sdt);
        statement.executeInsert();
    }

    void InsertCT(String maCT, String tenVT, String diaChi) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into congtrinh values (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, maCT);
        statement.bindString(2, tenVT);
        statement.bindString(3, diaChi);
        statement.executeInsert();
    }
    public Boolean InsertSignUp(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long resutl = db.insert("user",null,contentValues);
        if (resutl == -1 ) return false;
        else
            return true;
    }

    public boolean checkusername(String username){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username = ?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username = ? and password = ?", new String[] {username,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public ArrayList<Float> queryYdata(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query1 =  "SELECT SUM((soluong*culy*giaVC)) FROM chitietPVC \n" +
                "inner join vattu on vattu.maVT = chitietPVC.maVT \n" +
                "inner join PVC on PVC.maPVC = chitietPVC.maPVC \n" +
                "group by ngayVC";
        Cursor dt = db.rawQuery(query1,null);

        ArrayList<Float> ydata = new ArrayList<Float>();
        ArrayList<Integer> Xdata = new ArrayList<Integer>();

        while (dt.moveToNext()){
//
            ydata.add(dt.getFloat(0));
        }
        dt.close();
        return  ydata;
    }

    public ArrayList<Float> queryXdata(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query1 =  "SELECT strftime('%m', ngayVC) FROM chitietPVC\n" +
                "inner join vattu on vattu.maVT = chitietPVC.maVT\n" +
                "inner join PVC on PVC.maPVC = chitietPVC.maPVC\n" +
                "group by strftime('%m', ngayVC)";
        Cursor dt = db.rawQuery(query1,null);

        ArrayList<Float> Xdata = new ArrayList<Float>();
        for(dt.moveToFirst(); !dt.isAfterLast(); dt.moveToNext()){
            Xdata.add(dt.getFloat(0));
        }
        dt.close();
        return Xdata;
    }
    public void deleteCT(CongTrinh ct) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM congtrinh WHERE maCT='" + ct.getMaCT() + "'";
        db.execSQL(query);
    }
    public void deleteVT(VatTu vt) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM vattu WHERE maVT='" + vt.getMaVt() + "'";
        db.execSQL(query);
    }

    public void deleteKH(KH kh) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM kh WHERE maKH='" + kh.getMaKH() + "'";
        db.execSQL(query);
    }
    public void sendKH(KH kh) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT FROM kh WHERE maKH='" + kh.getMaKH() + "'";
        db.execSQL(query);
    }
    public void deleteCTVC(ChiTietPVC ctvc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM chitietPVC WHERE maPVC='" + ctvc.getMaPVC() + "'";
        db.execSQL(query);
    }
    public void deletePVC(PhieuVanChuyen pvc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM PVC WHERE maPVC='" + pvc.getMaPVC() + "'";
        db.execSQL(query);
    }
    public void updateCT(String tenCT, String diachi, String maCT) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update  congtrinh  set ";
        sql += "tenCT  = '" + tenCT + "' ,  ";
        sql += "diachi  = '" + diachi + "'";
        sql += "  WHERE maCT  = '" + maCT + "'";
        db.execSQL(sql);
    }

public void updateVT(String tenVT, String dvTinh, Float giaVC,byte[] hinh,int maVT) {
    SQLiteDatabase db = getWritableDatabase();
    String sql = "Update vattu set tenVT= ?,dvTinh=?,giaVC =?,hinh= ? where maVT=?";
    SQLiteStatement statement = db.compileStatement(sql);
    statement.clearBindings();
    statement.bindString(1, tenVT);
    statement.bindString(2, dvTinh);
    statement.bindDouble(3, giaVC);
    statement.bindBlob(4, hinh);
    statement.bindLong(5, maVT);
    statement.executeUpdateDelete();
}
    public void updateKH(String hoTenKH, String Email, String Sdt,int maKH) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Update KH set hoTenKH= ?,email=?,sdt =? where maKH=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, hoTenKH);
        statement.bindString(2, Email);
        statement.bindString(3, Sdt);
        statement.bindLong(4, maKH);
        statement.executeUpdateDelete();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


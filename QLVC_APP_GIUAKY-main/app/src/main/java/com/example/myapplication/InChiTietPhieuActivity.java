package com.example.myapplication;

import static android.text.TextUtils.substring;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Model.CongTrinh;
import com.example.myapplication.Model.KhachHang_PVC;
import com.example.myapplication.Model.PhieuVanChuyen;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class InChiTietPhieuActivity extends Activity {
    DBHelper DBhelper;
    KhachHang_PVC kh_pvc;
    CongTrinh congtrinh;
    PhieuVanChuyen pvc;
    ImageButton scanPdf;
    LinearLayout scrollView;
    Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_chi_tiet_phieu);
        //Set Controll
        TextView maCongTrinhTv_ICTP = findViewById(R.id.maCongTrinhTv_ICTP);
        TextView tenCongTrinhTv_ICTP = findViewById(R.id.tenCongTrinhTv_ICTP);
        TextView diaChiTv_ICTP = findViewById(R.id.diaChiTv_ICTP);
        TextView maPhieuTv_ICTP =  findViewById(R.id.maPhieu_ICTP);
        TextView ngayVanChuyenTv_ICTP  = findViewById(R.id.ngayVanChuyenTv_ICTP);
        TextView tongTienTv_ICTP = findViewById(R.id.tongTienTv_ICTP);
        TextView ngayInTv_ICTP = findViewById(R.id.ngayInTv_ICTP);
        TableLayout danhSachVatTuTbl = findViewById(R.id.bangChiTietVatTuTb_ICTP);
        EditText daiDienCongTrinhEdt_ICTP = findViewById(R.id.daiDienCongTrinhEdt_ICTP);
        EditText nguoiLapEdt_ICTP = findViewById(R.id.nguoiLapEdt_ICTP);

        scanPdf = findViewById(R.id.kiemTraInBtn_ICTP);
        scrollView = findViewById(R.id.scrollView);

        //Set Data
        Intent intent = getIntent();
        kh_pvc = (KhachHang_PVC) intent.getSerializableExtra("IN_PVC");
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        //Tìm  Công trình theo mã CT
        Cursor dt = DBhelper.GetData("select * from PVC where maPVC= '" + kh_pvc.getMaPVC()+ "'");
        while(dt.moveToNext()){
            pvc = new PhieuVanChuyen(dt.getString(0),dt.getString(1),dt.getString(2));
        }
        maPhieuTv_ICTP.setText(pvc.getMaPVC());
        ngayVanChuyenTv_ICTP.setText(pvc.getNgayVC());
        dt = DBhelper.GetData("select * from congtrinh where maCT='" +pvc.getMaCT()+"'");
        while(dt.moveToNext()){
            congtrinh = new CongTrinh(dt.getString(0),dt.getString(1),dt.getString(2));
        }
        maCongTrinhTv_ICTP.setText(congtrinh.getMaCT());
        tenCongTrinhTv_ICTP.setText(congtrinh.getTenCT());
        diaChiTv_ICTP.setText(congtrinh.getDiaChi());
        dt = DBhelper.GetData("select tenVT, dvTinh, soluong, giaVC, culy\n" +
                "from chitietPVC \n" +
                "inner join vattu on chitietPVC.maVT = vattu.maVT\n" +
                "where maPVC = '" + kh_pvc.getMaPVC() + "'");
        int tongTien = 0;
        //Set data vào table
        while(dt.moveToNext()){
            TableRow tableRow = new TableRow(this);
            TextView tenVt = new TextView(this);
            tenVt.setGravity(Gravity.CENTER);
            tenVt.setText(dt.getString(0));
            TextView donVi = new TextView(this);
            donVi.setGravity(Gravity.CENTER);
            donVi.setText(dt.getString(1));
            TextView soLuong = new TextView(this);
            soLuong.setGravity(Gravity.CENTER);
            soLuong.setText(dt.getString(2));
            TextView giaVc = new TextView(this);
            giaVc.setText(dt.getString(3));
            giaVc.setGravity(Gravity.CENTER);
            TextView cuLy = new TextView(this);
            cuLy.setGravity(Gravity.CENTER);
            cuLy.setText(dt.getString(4) + "km");
            TextView thanhTien = new TextView(this);
            thanhTien.setGravity(Gravity.CENTER);
            int tienVcVt = Integer.parseInt(dt.getString(2))*Integer.parseInt(dt.getString(3))*Integer.parseInt(dt.getString(4));
            tongTien+=tienVcVt;
            thanhTien.setText(String.valueOf(tienVcVt) + "đ");

            tableRow.addView(tenVt);
            tableRow.addView(donVi);
            tableRow.addView(soLuong);
            tableRow.addView(giaVc);
            tableRow.addView(cuLy);
            tableRow.addView(thanhTien);
            danhSachVatTuTbl.addView(tableRow);
        }
        tongTienTv_ICTP.setText(String.valueOf(tongTien) + "đ");
        try {
            ngayInTv_ICTP.setText("TP HCM, ngày " +kh_pvc.getNgayTT().substring(8,10)+" tháng "+kh_pvc.getNgayTT().substring(5,7)+" năm "+kh_pvc.getNgayTT().substring(0,4));
        }
        catch (Exception e){
            e.toString();
        }

    }

    public void kiemTraIn(View view) {
        bitmap = loadBitmapFromView(scrollView, scrollView.getWidth(), scrollView.getHeight());
        createPdf();
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffromScroll.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();


    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }
}

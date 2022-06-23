package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.ChiTietPVC;
import com.example.myapplication.Model.CongTrinh;
import com.example.myapplication.Model.PhieuVanChuyen;
import com.example.myapplication.Model.VatTu;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;C

import java.util.ArrayList;

public class MPLineChart extends AppCompatActivity{

    private LineChart lineChart;
//    LineData lineData;
    DBHelper Dbhelper;
    CongTrinh congtrinh;
    VatTu vattu;
    ChiTietPVC chitietPVC;
    PhieuVanChuyen phieuVanChuyen;
    long date = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setActionBar();
        lineChart = findViewById(R.id.Chart);

        Dbhelper = new DBHelper(this, "qlvc.sqlite", null, 1);


        draw();
//
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đồ thị doanh thu theo tháng");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void draw(){
        Dbhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        final ArrayList<Entry> yVals = new ArrayList<Entry>();
        final ArrayList<Float> xValues = new ArrayList<Float>();


        for (int i = 0; i <Dbhelper.queryYdata().size(); i++){
            yVals.add(new Entry(Dbhelper.queryXdata().get(i),Dbhelper.queryYdata().get(i)));
        }
        yVals.add(new Entry(1,0));


//        final ArrayList<String> xvals = new ArrayList<>();
//        final ArrayList<String> xdata = Dbhelper.queryXdata();
//        for (int i = 0; i < Dbhelper.queryXdata().size(); i++){
//            xvals.add(xdata.get(i));
//        }

        LineDataSet set1 = new LineDataSet(yVals, "Thống kê doanh thu vật tư theo tháng");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        lineChart.setVisibleXRangeMaximum(12);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
    }


}


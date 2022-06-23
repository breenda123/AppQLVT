package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.Adapter.CustomAdapter_PVC;
import com.example.myapplication.Model.PhieuVanChuyen;

import java.util.ArrayList;

public class PhieuVanChuyenActivity extends AppCompatActivity {

    DBHelper DBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_van_chuyen);
        // GET THONG TIN VT
        ImageButton imgbtn_addpvc = findViewById(R.id.imgbtn_addpvc);
        ListView listView = findViewById(R.id.lvPVC);

        ArrayList<PhieuVanChuyen> ArrPVC = new ArrayList<>();
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        Cursor dt = DBhelper.GetData("select * from PVC");
        while (dt.moveToNext()) {
            Log.d("SelectPVC", dt.getString(0) + " " + dt.getString(2));
            PhieuVanChuyen PVC = new PhieuVanChuyen(dt.getString(0), dt.getString(1), dt.getString(2));
            ArrPVC.add(PVC);
        }
        CustomAdapter_PVC adapter = new CustomAdapter_PVC(ArrPVC, this, R.layout.item_pvc);
        listView.setAdapter(adapter);

        imgbtn_addpvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhieuVanChuyenActivity.this, ThemPVCActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_danhsach, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.memu_home:
                Intent intent =new Intent(this, dashboard.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
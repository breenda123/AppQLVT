package com.example.myapplication;

import static com.example.myapplication.ext.ConstExt.restartActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.CustomAdapter_KH_PVC;
import com.example.myapplication.Adapter.CustomSpinnerAdapterPVC;
import com.example.myapplication.Model.KH;
import com.example.myapplication.Model.KhachHang_PVC;
import com.example.myapplication.Model.PhieuVanChuyen;

import java.util.ArrayList;

public class KH_PVC_Activity extends AppCompatActivity {
    int position;
    DBHelper DBhelper;
    ImageView btnAdd;
    ListView listViewKH_PVC;
    int selected_position;
    KH kh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_pvc);
        DBhelper = new DBHelper(KH_PVC_Activity.this, "qlvc.sqlite", null, 1);
        Intent intent = getIntent();
        kh = (KH) intent.getSerializableExtra("KhachHang");
        btnAdd = findViewById(R.id.imgbtn_addKH_PVC);
        listViewKH_PVC = findViewById(R.id.lvDSKH_PVC);

        //GET DANH SÁCH Khách hàng - Phiếu vận chuyển
        ArrayList<KhachHang_PVC> arrKH_PVC = new ArrayList<>();
        Cursor dt = DBhelper.GetData("select * from KH_PVC where maKH = '"+kh.getMaKH()+"'");
        while (dt.moveToNext()) {
            Log.d("SelectKH_PVC", dt.getString(0));
            KhachHang_PVC kh_pvc = new KhachHang_PVC(Integer.parseInt(dt.getString(0)), dt.getInt(2), dt.getString(3),dt.getString(1));
            arrKH_PVC.add(kh_pvc);
        }
        CustomAdapter_KH_PVC adapter = new CustomAdapter_KH_PVC(arrKH_PVC, this, R.layout.item_kh_pvc );
        listViewKH_PVC.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_ThemPVC_KH();
            }
        });

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.memu_home:
                Intent intent =new Intent(this, dashboard.class);
                startActivity(intent);
                break;
            case  R.id.memu_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Dialog Them Khách hàng - Phiếu vận chuyển
    private void Dialog_ThemPVC_KH() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_themkh_pvc);
        Spinner spnPVC = (Spinner) dialog.findViewById(R.id.spnPVC_KH);
        Button btnThem_PVC_KH = dialog.findViewById(R.id.btnThemPVC_KH);
        ArrayList<PhieuVanChuyen> dsPVC= new ArrayList<PhieuVanChuyen>();
        Cursor dt= DBhelper.GetData("select * from PVC");
        while (dt.moveToNext()){
            PhieuVanChuyen ct=new PhieuVanChuyen(dt.getString(0), dt.getString(1),dt.getString(2));
            dsPVC.add(ct);
        }

        CustomSpinnerAdapterPVC spinnerAdapterPVC = new CustomSpinnerAdapterPVC(dsPVC);
        try {
            spnPVC.setAdapter(spinnerAdapterPVC);
        }catch (Exception e){
            System.out.print(e.toString());
        }
        spnPVC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThem_PVC_KH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DBhelper.QueryData("insert into KH_PVC values('"+ kh.getMaKH() +"','"+dsPVC.get(selected_position).getMaPVC()+"','0',null)");
                    Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    restartActivity(KH_PVC_Activity.this);
                    dialog.dismiss();
                }catch (Exception e){
                    Toast.makeText(KH_PVC_Activity.this,e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

}
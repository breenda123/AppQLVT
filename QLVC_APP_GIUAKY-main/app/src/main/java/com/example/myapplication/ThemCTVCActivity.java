package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Adapter.CustomSpinerAdapterVT;
import com.example.myapplication.Model.PhieuVanChuyen;
import com.example.myapplication.Model.VatTu;

import java.util.ArrayList;

public class ThemCTVCActivity extends AppCompatActivity {
    DBHelper DBhelper;
    int selected_positionPVC, selected_positionVT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ctvc);


        EditText edtSoLuong = findViewById(R.id.edtSoLuong);
        EditText edtCuLy = findViewById(R.id.edtCuLy);

        Button btnadd = findViewById(R.id.btnTaoCTVC);
        Spinner PVCspinner = findViewById(R.id.spnMaPVC);
        Spinner VTspinner = findViewById(R.id.spnVT);

        ArrayList<PhieuVanChuyen> dsPVC= new ArrayList<PhieuVanChuyen>();
        Cursor dt= DBhelper.GetData("select * from PVC");
        while (dt.moveToNext()){
            PhieuVanChuyen ct=new PhieuVanChuyen(dt.getString(0), dt.getString(1),dt.getString(2));
            dsPVC.add(ct);
        }

        ArrayAdapter spinnerAdapterPVC = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dsPVC);
        PVCspinner.setAdapter(spinnerAdapterPVC);

        ArrayList<VatTu> dsVT= new ArrayList<VatTu>();
        Cursor dh= DBhelper.GetData("select * from vattu");
        while (dh.moveToNext()){
            VatTu vt=new VatTu(dh.getInt(0), dh.getString(1),dh.getString(2),dh.getFloat(3),dh.getBlob(4));
            dsVT.add(vt);
        }

        CustomSpinerAdapterVT spinnerAdapterVT = new CustomSpinerAdapterVT(dsVT);
        VTspinner.setAdapter(spinnerAdapterVT);

        PVCspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected_positionPVC= position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        VTspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_positionVT= position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flagValid = true;
                if(edtSoLuong.getText().toString().trim().isEmpty())
                {
                    edtSoLuong.setError("Số lượng không được trống");
                    flagValid = false;
                }
                if(edtCuLy.getText().toString().trim().isEmpty())
                {
                    edtCuLy.setError("Cự ly không được trống");
                    flagValid = false;
                }

                if(flagValid) {

                    try {
                        DBhelper.QueryData("insert into chitietPVC values('" + dsPVC.get(selected_positionPVC).getMaPVC() + "','" + dsVT.get(selected_positionVT).getMaVt() +"','" + Integer.parseInt(edtSoLuong.getText().toString()) + "','" + Integer.parseInt(edtCuLy.getText().toString()) + "')");
                        Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemCTVCActivity.this, ChiTietPVCActivity.class);
                        startActivity(intent);
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Lỗi ghi CSDL! Không thể thêm!", Toast.LENGTH_SHORT).show();
                    }
                }


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
}
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.myapplication.ext.ConstExt.POSITION;

import com.example.myapplication.Adapter.CustomAdapter_CongTrinh;
import com.example.myapplication.Model.CongTrinh;

public class SuaCongTrinhActivity extends AppCompatActivity {

    DBHelper DBhelper;
    EditText edtTenCT, edtDiaChi;
    Button btnSuaCT;
    String maCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_cong_trinh);
        create();

        ArrayList<CongTrinh> ArrCT = new ArrayList<>();
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        Cursor dt = DBhelper.GetData("select * from congtrinh");
        while (dt.moveToNext()) {
            Log.d("SelectCT", dt.getString(0));
            CongTrinh CT = new CongTrinh(dt.getString(0), dt.getString(1), dt.getString(2));
            ArrCT.add(CT);
        }
        CustomAdapter_CongTrinh adapter = new CustomAdapter_CongTrinh(ArrCT, this, R.layout.item_dsct);
        CongTrinh ct = (CongTrinh) adapter.getItem(POSITION);
        edtTenCT.setText(ct.getTenCT());
        edtDiaChi.setText(ct.getDiaChi());
        maCT = ct.getMaCT();
        setEvent();

    }


    private void setEvent() {

        btnSuaCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if (edtTenCT.getText().toString().trim().isEmpty()) {
                    edtTenCT.setError("Tên công trình không được trống");
                    flagValid = false;
                }
                if (edtDiaChi.getText().toString().trim().isEmpty()) {
                    edtDiaChi.setError("Địa chỉ không được trống");
                    flagValid = false;
                }
                try {
                    if (flagValid) {
                        Log.d("edit", "CT: " + edtTenCT.getText().toString().trim());

                        DBhelper = new DBHelper(SuaCongTrinhActivity.this, "qlvc.sqlite", null, 1);
                        DBhelper.updateCT(edtTenCT.getText().toString(), edtDiaChi.getText().toString(), maCT);
                        Toast.makeText(SuaCongTrinhActivity.this, "sửa công trình thành công!", Toast.LENGTH_LONG).show();
                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(SuaCongTrinhActivity.this, CongTrinhActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(SuaCongTrinhActivity.this, "không thành công!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void create() {
        edtTenCT = findViewById(R.id.edtTenCT);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnSuaCT = findViewById(R.id.btnSuaCT);
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
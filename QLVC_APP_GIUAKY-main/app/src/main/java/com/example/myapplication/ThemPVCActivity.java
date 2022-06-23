package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.CongTrinh;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThemPVCActivity extends AppCompatActivity {
    DBHelper DBhelper;
    int selected_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_pvc);


        EditText edtMaPVC = findViewById(R.id.edtMaPVC);
        Button btnadd = findViewById(R.id.btnTaoPVC);
        Spinner gvspinner = findViewById(R.id.spnCT);

        ArrayList<CongTrinh> dsCT = new ArrayList<CongTrinh>();
        Cursor dt = DBhelper.GetData("select * from congtrinh");
        while (dt.moveToNext()) {
            CongTrinh ct = new CongTrinh(dt.getString(0), dt.getString(1), dt.getString(2));
            dsCT.add(ct);
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dsCT);
        gvspinner.setAdapter(spinnerAdapter);

        gvspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker ngaygiao = findViewById(R.id.dpCT);
                int day = ngaygiao.getDayOfMonth();
                int month = ngaygiao.getMonth() + 1;
                int year = ngaygiao.getYear();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String ngaychon = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);

                Date ngay = null;
                try {
                    ngay = new SimpleDateFormat("yyyy-MM-dd").parse(ngaychon);
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(ngay);
                    Date date2 = Calendar.getInstance().getTime();
                    String strtodate = dateFormat.format(date2);
                    Boolean flagValid = true;
                    String MaPVC = edtMaPVC.getText().toString();
                    if (edtMaPVC.getText().toString().trim().isEmpty()) {
                        edtMaPVC.setError("Mã phiếu vận chuyển không được trống");
                        flagValid = false;
                    }

                    try {
                        if (flagValid) {
                            // Kiểm tra xem ngày giao có lớn hơn ngày hiện tại không
                            try {
                                DBhelper.QueryData("insert into PVC values('" + MaPVC + "','" + date+ "','" + dsCT.get(selected_position).getMaCT() + "')");
                                Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ThemPVCActivity.this, PhieuVanChuyenActivity.class);
                                startActivity(intent);
                            } catch (Exception ex) {
                                Toast.makeText(getApplicationContext(), "Lỗi ghi CSDL! Không thể thêm!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
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

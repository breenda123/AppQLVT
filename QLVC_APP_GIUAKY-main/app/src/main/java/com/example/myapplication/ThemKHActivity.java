package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ThemKHActivity extends AppCompatActivity {

   // private int REQUEST_CODE = 8888;
    DBHelper DBhelper;
    EditText edtTenKH, edtEmail, edtSdt;
    Button btnTaoKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper = new DBHelper(ThemKHActivity.this, "qlvc.sqlite", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_kh);
        create();
        setEvent();

    }

    private void setEvent() {
        btnTaoKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if (edtTenKH.getText().toString().trim().isEmpty()) {
                    edtTenKH.setError("Tên khách hàng không được trống");
                    flagValid = false;
                }
                if (edtEmail.getText().toString().trim().isEmpty()) {
                    edtEmail.setError("Email không được trống");
                    flagValid = false;
                }
                if (edtSdt.getText().toString().trim().isEmpty()) {
                    edtSdt.setError("SDT không được trống");
                    flagValid = false;
                }
                try {
                    if (flagValid) {
                        Log.d("addd", "KH: " + edtTenKH.getText().toString().trim());

                        DBhelper.QueryData("insert into KH values(null,'"   + edtTenKH.getText().toString() + "','" + edtEmail.getText().toString()+ "','"  + edtSdt.getText().toString() +"')");
                        Toast.makeText(ThemKHActivity.this, "thêm khách hàng thành công!", Toast.LENGTH_LONG).show();
                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(ThemKHActivity.this, KhachHangActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(ThemKHActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void create() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSdt);
        btnTaoKH = findViewById(R.id.btnTaoKH);
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
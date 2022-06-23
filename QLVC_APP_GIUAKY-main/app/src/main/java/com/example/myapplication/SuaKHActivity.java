package com.example.myapplication;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.myapplication.ext.ConstExt.POSITION;

import com.example.myapplication.Adapter.CustomAdapter_KhachHang;
import com.example.myapplication.Model.KH;

public class SuaKHActivity extends AppCompatActivity {

    DBHelper DBhelper;
    EditText edtTenKH,edtEmail,edtSdt;
    Button btnSuaKH;
    int maKH;
    int selected_positionPVC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper= new DBHelper(SuaKHActivity.this,"qlvc.sqlite",null,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_kh);
        create();

        ArrayList<KH> ArrKH  = new ArrayList<>();
        DBhelper = new DBHelper(this,"qlvc.sqlite",null,1);
        Cursor dt= DBhelper.GetData("select * from KH");
        while(dt.moveToNext())
        {
            Log.d("SelectKH", dt.getString(0) );
            KH KH = new KH(dt.getInt(0),dt.getString(1),dt.getString(2),dt.getString(3));
            ArrKH.add(KH);
        }
        CustomAdapter_KhachHang adapter = new CustomAdapter_KhachHang(ArrKH, this, R.layout.item_dskh);
        KH kh = (KH) adapter.getItem(POSITION);
        edtTenKH.setText(kh.getTenKH());
        edtEmail.setText(kh.getEmail());
        edtSdt.setText(kh.getSdt());
        maKH =kh.getMaKH();
        setEvent();

    }




    private void setEvent() {
        btnSuaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if(edtTenKH.getText().toString().trim().isEmpty())
                {
                    edtTenKH.setError("Tên khách hàng không được trống");
                    flagValid = false;
                }
                if(edtEmail.getText().toString().trim().isEmpty())
                {
                    edtEmail.setError("Email không được trống");
                    flagValid = false;
                }
                if(edtSdt.getText().toString().trim().isEmpty())
                {
                    edtSdt.setError("SDT không được trống");
                    flagValid = false;
                }
                try {
                    if(flagValid)
                    {
                        Log.d("edit", "KH: " +  edtTenKH.getText().toString().trim());

                        DBhelper.updateKH(edtTenKH.getText().toString(), edtEmail.getText().toString(),edtSdt.getText().toString(), maKH);
                        Toast.makeText(SuaKHActivity.this, "sửa khách hàng thành công!", Toast.LENGTH_LONG).show();
                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(SuaKHActivity.this, KhachHangActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(SuaKHActivity.this, "không thành công!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void create() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSdt);
        btnSuaKH = findViewById(R.id.btnSuaKH);
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
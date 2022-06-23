package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class dashboard extends AppCompatActivity {
    DBHelper DBhelper;
    private MaterialCardView cardViewQLVT, cardViewQLCT, cardViewChuyenVT, cardViewCTVC, cardViewThongKe,cardViewKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        cardViewQLVT = findViewById(R.id.cv_qlvt);
        //cardViewQLVT.startAnimation(animation);
        cardViewQLCT = findViewById(R.id.cv_qlct);
        //cardViewQLCT.startAnimation(animation);
        cardViewChuyenVT = findViewById(R.id.cv_chuyenVT);
        //cardViewChuyenVT.startAnimation(animation);
        cardViewCTVC = findViewById(R.id.cv_qlchitiet);
        //cardViewCTVC.startAnimation(animation);
        cardViewThongKe = findViewById(R.id.cv_thongke);
        //cardViewThongKe.startAnimation(animation4);
        cardViewKhachHang = findViewById(R.id.cv_khachhang);
        //cardViewKhachHang.startAnimation(animation);

        cardViewQLVT.setOnClickListener(view -> {
            startActivity(new Intent(this, DSVatTuActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        });

        cardViewQLCT.setOnClickListener(view -> {
            startActivity(new Intent(this, CongTrinhActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        cardViewChuyenVT.setOnClickListener(view -> {
            startActivity(new Intent(this, PhieuVanChuyenActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        cardViewCTVC.setOnClickListener(view -> {
            startActivity(new Intent(this, ChiTietPVCActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        cardViewThongKe.setOnClickListener(view -> {
            startActivity(new Intent(this, MPLineChart.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        cardViewKhachHang.setOnClickListener(view -> {
            startActivity(new Intent(this, KhachHangActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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

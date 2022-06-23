package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper DBhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this, R.color.white);

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);

        TextView loginbtn = (TextView) findViewById(R.id.login);
        TextView signup = (TextView) findViewById(R.id.singup);
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass = DBhelper.checkusernamepassword(user,pass);
                    if (checkuserpass == true){
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, dashboard.class));
                    }
                    else {
                        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công. Bạn đang đăng nhập vào tài khoản root", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, dashboard.class));
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
                }
            }
        });
        Init_DB();

    }
    public void Init_DB() {
        DBhelper.QueryData("create table if not exists user(id integer primary key Autoincrement,username varchar(50) unique, password nvarchar(50))");
        DBhelper.QueryData("create table if not exists vattu(maVT integer primary key Autoincrement,tenVT nvarchar(100),dvTinh nvarchar(30),giaVC float,hinh Blob)");
        DBhelper.QueryData("create table if not exists congtrinh( maCT nvarchar(30) primary key,tenCT nvarchar(50), diachi nvarchar(100))");
        DBhelper.QueryData("create table if not exists PVC(maPVC nchar(10) primary key, ngayVC date, maCT varchar(30), FOREIGN KEY(maCT) REFERENCES congtrinh(maCT) )");
        DBhelper.QueryData("create table if not exists chitietPVC( maPVC nchar(10), maVT int, soluong int, culy int,PRIMARY KEY (maPVC, maVT) FOREIGN KEY(maPVC) REFERENCES PVC(maPVC),  FOREIGN KEY (maVT) REFERENCES vattu(maVT))");
        DBhelper.QueryData("create table if not exists KH(maKH integer primary key Autoincrement,hoTenKH nvarchar(50),email varchar(50),sdt varchar(13))");
        try{
            DBhelper.QueryData("create table if not exists KH_PVC(maKH integer, maPVC nchar(10), tinhtrang bit, NgayThanhToan date,PRIMARY KEY (maKH, maPVC) FOREIGN KEY(maPVC) REFERENCES PVC(maPVC),  FOREIGN KEY (maKH) REFERENCES KH(maKH), unique(maPVC))");
        }catch (Exception e){
            System.out.print(e.toString());
        }
    }
}
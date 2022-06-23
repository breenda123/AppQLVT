package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    TextView username, password;
    TextView btnsign;
    DBHelper Dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        username = (TextView) findViewById(R.id.username_sign);
        password = (TextView) findViewById(R.id.password_sign);

        btnsign = (TextView) findViewById(R.id.signin);
        Dbhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(SignUp.this,"Vui lòng nhập thông tin",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuser = Dbhelper.checkusername(user);
                    if (checkuser == false){
                        Boolean insert = Dbhelper.InsertSignUp(user,pass);
                        if (insert == true){
                            Toast.makeText(SignUp.this,"Tạo tài khoản thành công",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignUp.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignUp.this, "Trùng tên đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}

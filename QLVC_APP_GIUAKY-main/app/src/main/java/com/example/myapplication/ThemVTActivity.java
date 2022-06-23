package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ThemVTActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private static final int NOTIFICATION_ID = 1;
    private int REQUEST_CODE = 8888;
    DBHelper DBhelper;
    ImageView ivHinh;
    EditText edtTenVT, edtGiaVC, edtDVTinh;
    Button btnTaoVT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_vt);
        create();
        setEvent();

    }
    //Sau khi chụp ảnh và gửi về rồi, chúng ta cần một hàm để nhận dữ liệu này và hiển thị lên trên ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //kiểm tra xem biến requestCode trả về có mang giá trị giống như biến REQUEST_CODE đã khai báo hay không,
        // vì có thể nhiều Activity sẽ có nhiều REQUEST_CODE khác nhau
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //hình ảnh lưu dưới dạng byte
    //lấy mảng byte lưu vào csdl
    public byte[] ConverttoArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    private void setEvent() {

        ivHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //kết nối camera
                startActivityForResult(intent, REQUEST_CODE); //trả về dữ liệu khi chụp xong và ss dk khi trả về dlieu
                                                                //có đúng Activity đang cần ko
            }
        });

        btnTaoVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if (edtTenVT.getText().toString().trim().isEmpty()) {
                    edtTenVT.setError("Tên vật tư không được trống");
                    flagValid = false;
                }
                if (edtDVTinh.getText().toString().trim().isEmpty()) {
                    edtDVTinh.setError("Đơn vị tính không được trống");
                    flagValid = false;
                }
                if (edtGiaVC.getText().toString().trim().isEmpty()) {
                    edtGiaVC.setError("Giá vận chuyển không được trống");
                    flagValid = false;
                }
                try {
                    if (flagValid) {
                        Log.d("addd", "VT: " + edtTenVT.getText().toString().trim() + ConverttoArrayByte(ivHinh).toString());

                        DBhelper = new DBHelper(ThemVTActivity.this, "qlvc.sqlite", null, 1);
                        DBhelper.InsertVT(edtTenVT.getText().toString(), edtDVTinh.getText().toString(), Float.parseFloat(edtGiaVC.getText().toString()), ConverttoArrayByte(ivHinh));
                        Toast.makeText(ThemVTActivity.this, "thêm vật tư thành công!", Toast.LENGTH_LONG).show();

                        // hien thong bao
                       // sendNotify();
                        sendOnChannel1(  );

                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(ThemVTActivity.this, DSVatTuActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(ThemVTActivity.this, "không thành công!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

//            private void sendNotify() {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iconflower);
//
//                Notification notification = new NotificationCompat.Builder(ThemVTActivity.this, notify.CHANNEL_ID)
//                        .setContentTitle("co vat tu moi!")
//                        .setContentText("mo vat tu da duoc them vao")
//                        .setSmallIcon(R.drawable.iconflower)
//                        .setLargeIcon(bitmap)
//                        .setColor(getResources().getColor(R.color.colorAccent))
//                        .build();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(getNotificationId(), notification);
//
//            }
        });
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
    }

//    private int getNotificationId() {
//        return (int) new Date().getTime();
//    }

    private void sendOnChannel1()  {
        // String title = this.editTextTitle.getText().toString();
        // String message = this.editTextMessage.getText().toString();
        String title = "News";
        String message ="Có mặt hàng mới";

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }

    private void create() {
        edtTenVT = findViewById(R.id.edtTenVT);
        edtGiaVC = findViewById(R.id.edtGiaVC);
        edtDVTinh = findViewById(R.id.edtDVTinh);
        ivHinh = findViewById(R.id.imageView);
        btnTaoVT = findViewById(R.id.btnTaoVT);
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
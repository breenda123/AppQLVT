package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.myapplication.ext.ConstExt.POSITION;

import com.example.myapplication.Adapter.CustomAdapter_VatTu;
import com.example.myapplication.Model.VatTu;

public class SuaVTActivity extends AppCompatActivity {

    private int PICK_IMAGE = 8888;
    DBHelper DBhelper;
    EditText edtTenVT,edtDvTinh,edtGiaVC;
    ImageView imageView;
    Button btnSuaVT;
    int maVT;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_vat_tu);
        create();

        ArrayList<VatTu> ArrVT  = new ArrayList<>();
        DBhelper = new DBHelper(this,"qlvc.sqlite",null,1);
        Cursor dt= DBhelper.GetData("select * from vattu");
        while(dt.moveToNext())
        {
            Log.d("SelectVT", dt.getString(0) );
            VatTu VT = new VatTu(dt.getInt(0),dt.getString(1),dt.getString(2),dt.getFloat(3),dt.getBlob(4));
            ArrVT.add(VT);
        }
        CustomAdapter_VatTu adapter = new CustomAdapter_VatTu(ArrVT, this, R.layout.item_dsvt);
        VatTu vt = (VatTu) adapter.getItem(POSITION);
        edtTenVT.setText(vt.getTenVt());
        edtDvTinh.setText(vt.getDvTinh());
        edtGiaVC.setText(vt.getGiaVc().toString());
        Bitmap bitmap= BitmapFactory.decodeByteArray(vt.getHinh(), 0, vt.getHinh().length);
        imageView.setImageBitmap(bitmap);
        maVT =vt.getMaVt();
        setEvent();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,600,600,true);

                imageView.setImageBitmap(bitmap1);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public byte[] ConverttoArrayByte(ImageView img)
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    private void setEvent() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(gallery.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Chọn Hình Ảnh"), PICK_IMAGE);
            }
        });

        btnSuaVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if(edtTenVT.getText().toString().trim().isEmpty())
                {
                    edtTenVT.setError("Tên vật tư không được trống");
                    flagValid = false;
                }
                if(edtDvTinh.getText().toString().trim().isEmpty())
                {
                    edtDvTinh.setError("Đơn vị tính không được trống");
                    flagValid = false;
                }
                if(edtGiaVC.getText().toString().trim().isEmpty())
                {
                    edtGiaVC.setError("Giá vận chuyển không được trống");
                    flagValid = false;
                }
                try {
                    if(flagValid)
                    {
                        Log.d("edit", "VT: " +  edtTenVT.getText().toString().trim());

                        DBhelper= new DBHelper(SuaVTActivity.this,"qlvc.sqlite",null,1);
                        DBhelper.updateVT(edtTenVT.getText().toString(), edtDvTinh.getText().toString(), Float.parseFloat(edtGiaVC.getText().toString()), ConverttoArrayByte(imageView),maVT);
                        Toast.makeText(SuaVTActivity.this, "sửa vật tư thành công!", Toast.LENGTH_LONG).show();
                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(SuaVTActivity.this, DSVatTuActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(SuaVTActivity.this, "không thành công!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void create() {
        edtTenVT = findViewById(R.id.edtTenVT);
        edtDvTinh = findViewById(R.id.edtDVTinh);
        edtGiaVC = findViewById(R.id.edtGiaVC);
        imageView = findViewById(R.id.imageView);
        btnSuaVT = findViewById(R.id.btnSuaVT);
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
package com.example.myapplication.Adapter;
import static com.example.myapplication.ext.ConstExt.POSITION;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

import com.example.myapplication.DBHelper;
import com.example.myapplication.Model.VatTu;
import com.example.myapplication.R;
import com.example.myapplication.SuaVTActivity;

public class CustomAdapter_VatTu extends BaseAdapter {
    ArrayList<VatTu> arrayList;
    Context context;
    int layout;
    private DBHelper DBhelper;

    public CustomAdapter_VatTu(ArrayList<VatTu> arrayList, Context context, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DBhelper = new DBHelper(parent.getContext(), "qlvc.sqlite", null, 1);
        View viewitem = View.inflate(parent.getContext(), R.layout.item_dsvt, null);
        VatTu VT = (VatTu) getItem(position);
        TextView tvMaVT = (TextView) viewitem.findViewById(R.id.tvMaVT);
        tvMaVT.setText(String.valueOf(VT.getMaVt()));
        TextView tvTenVT = (TextView) viewitem.findViewById(R.id.tvTenVT);
        tvTenVT.setText(VT.getTenVt());
        TextView tvdvTinh = (TextView) viewitem.findViewById(R.id.tvDVT);
        tvdvTinh.setText(VT.getDvTinh());
        TextView tvGia = (TextView) viewitem.findViewById(R.id.tvGiaVC);
        tvGia.setText(String.valueOf(VT.getGiaVc()));
        ImageView tvHinh = (ImageView) viewitem.findViewById(R.id.imHinh);
        //hiển thị hình ảnh dưới dạng byte
        Bitmap bitmap = BitmapFactory.decodeByteArray(VT.getHinh(), 0, VT.getHinh().length);
        tvHinh.setImageBitmap(bitmap);
        ImageView btnEditVT = viewitem.findViewById(R.id.btnUpdate11);
        btnEditVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POSITION = position;
                Intent intent = new Intent(context, SuaVTActivity.class);
                context.startActivity(intent);
            }
        });
        ImageView btnDelete = viewitem.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Thông báo!");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Xác nhận xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cursor dt = DBhelper.GetData("select * from chitietPVC where maPVC = '"+ arrayList.get(position).getMaVt() +"'");
                        if(dt.moveToNext()){
                            Toast.makeText(context, "Không thể xóa vật tư!", Toast.LENGTH_SHORT).show();
                        } else{
                            DBhelper.deleteVT(arrayList.get(position));
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa vật tư thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
        return viewitem;
    }
}
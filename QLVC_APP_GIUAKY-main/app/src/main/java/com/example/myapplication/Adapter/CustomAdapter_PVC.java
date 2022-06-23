package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DBHelper;
import com.example.myapplication.Model.PhieuVanChuyen;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomAdapter_PVC extends BaseAdapter {
    ArrayList<PhieuVanChuyen> arrayList;
    Context context;
    int layout;
    private DBHelper DBhelper;

    public CustomAdapter_PVC(ArrayList<PhieuVanChuyen> arrayList, Context context, int layout) {
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
        View viewitem = View.inflate(parent.getContext(), R.layout.item_pvc, null);
        PhieuVanChuyen PVC = (PhieuVanChuyen) getItem(position);
        TextView tvMaPVC = (TextView) viewitem.findViewById(R.id.tvMaPVC);
        tvMaPVC.setText(String.valueOf(PVC.getMaPVC()));
        TextView tvNgay = (TextView) viewitem.findViewById(R.id.tvNgayVC);
        tvNgay.setText(PVC.getNgayVC());
        TextView tvTenCT = (TextView) viewitem.findViewById(R.id.tvMaCT);
        tvTenCT.setText(PVC.getMaCT());

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
                        Cursor dt = DBhelper.GetData("select * from chitietPVC where maPVC = '"+ arrayList.get(position).getMaPVC() +"'");
                        if(dt.moveToNext()){
                            Toast.makeText(context, "Không thể xóa phiếu vận chuyển!", Toast.LENGTH_SHORT).show();
                        } else{
                            DBhelper.deletePVC(arrayList.get(position));
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa phiếu vận chuyển thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
        return viewitem;
    }
}

package com.example.myapplication.Adapter;

import static com.example.myapplication.ext.ConstExt.restartActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DBHelper;
import com.example.myapplication.InChiTietPhieuActivity;
import com.example.myapplication.KH_PVC_Activity;
import com.example.myapplication.KhachHangActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.KhachHang_PVC;
import com.example.myapplication.R;
import com.example.myapplication.SuaKHActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter_KH_PVC extends BaseAdapter {
    DBHelper DBhelper;
    ArrayList<KhachHang_PVC> arrayList;
    Context context;
    int layout;

    public CustomAdapter_KH_PVC(ArrayList<KhachHang_PVC> arrayList, Context context, int layout) {
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
    public View getView(int position, View view, ViewGroup parent) {
        DBhelper = new DBHelper(parent.getContext(), "qlvc.sqlite", null, 1);
        View viewitem = View.inflate(parent.getContext(), R.layout.item_kh_pvc, null);
        KhachHang_PVC kh_pvc = (KhachHang_PVC) getItem(position);
        TextView tvMaKH_PVC = viewitem.findViewById(R.id.tvMaKH_PVC);
        tvMaKH_PVC.setText(String.valueOf(kh_pvc.getMaKH()));
        TextView tvMaPVC_KH = viewitem.findViewById(R.id.tvMaPVC_KH);
        tvMaPVC_KH.setText(kh_pvc.getMaPVC());
        TextView tvNgayTT = viewitem.findViewById(R.id.tvNgayTT);
        tvNgayTT.setText(String.valueOf(kh_pvc.getNgayTT()));
        TextView tvTrangThai = viewitem.findViewById(R.id.tvTT);
        if(kh_pvc.getTrangThai()==1){
            tvTrangThai.setText("Đã thanh toán");
        }else{
            tvTrangThai.setText("Chưa thanh toán");
        }
        ImageView btnDelete_PVC_KH = viewitem.findViewById(R.id.btnDeletePVC_KH);
        btnDelete_PVC_KH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kh_pvc.getTrangThai() == 1){
                    Toast.makeText(context, "Phiếu đã thanh toán, không được xóa!", Toast.LENGTH_SHORT).show();
                } else{
                    DBhelper.QueryData("DELETE FROM KH_PVC where maKH = '"+kh_pvc.getMaKH()+"' and maPVC ='" +kh_pvc.getMaPVC()+"'");
                    arrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        ImageView btnDone = viewitem.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    DBhelper.QueryData("UPDATE KH_PVC set tinhtrang = '1', NgayThanhToan = date('now') where maKH = '"+kh_pvc.getMaKH()+"' and maPVC = '"+kh_pvc.getMaPVC()+"'");
                    Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_LONG).show();
                    restartActivity((Activity) context);
                }catch (Exception e){
                    Toast.makeText(context,e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageView btnPrint = viewitem.findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InChiTietPhieuActivity.class);
                try{
                    intent.putExtra("IN_PVC", (Serializable) kh_pvc);
                }catch (Exception e){
                    e.toString();
                }
                context.startActivity(intent);
            }
        });

        return viewitem;
    }
}

package com.example.myapplication.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DBHelper;
import com.example.myapplication.Model.VatTu;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomSpinerAdapterVT extends BaseAdapter {
    DBHelper DBhelper;
    ArrayList<VatTu> arrayList;

    public CustomSpinerAdapterVT(ArrayList<VatTu> arrayList) {
        this.arrayList = arrayList;
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
        DBhelper= new DBHelper(parent.getContext(),"qlvc.sqlite",null,1);
        View viewitem= View.inflate(parent.getContext(), R.layout.dong_xem_vat_tu,null);
        VatTu VT= (VatTu) getItem(position);
        TextView tvTenVT =(TextView) viewitem.findViewById(R.id.tvXemTenSP);
        tvTenVT.setText("Tên Vật Tư: "+VT.getTenVt());
        TextView tvGia =(TextView) viewitem.findViewById(R.id.tvXemGiaSP);
        tvGia.setText("Giá VC: "+String.valueOf(VT.getGiaVc()));
        ImageView tvHinh =(ImageView) viewitem.findViewById(R.id.ivXemHinhSP);
        Bitmap bitmap= BitmapFactory.decodeByteArray(VT.getHinh(), 0, VT.getHinh().length);
        tvHinh.setImageBitmap(bitmap);

        return viewitem;
    }
}

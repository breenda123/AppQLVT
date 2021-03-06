package com.example.myapplication.Adapter;

import static com.example.myapplication.ext.ConstExt.POSITION;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DBHelper;
import com.example.myapplication.Model.KH;
import com.example.myapplication.R;
import com.example.myapplication.SuaKHActivity;

import java.util.ArrayList;

public class CustomAdapter_KhachHang extends BaseAdapter {
    ArrayList<KH> arrayList;
    Context context;
    int layout;
    DBHelper DBhelper;

    public CustomAdapter_KhachHang(ArrayList<KH> arrayList, Context context, int layout) {
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
        View viewitem = View.inflate(parent.getContext(), R.layout.item_dskh, null);
        KH KH = (KH) getItem(position);
        TextView tvMaKH = (TextView) viewitem.findViewById(R.id.tvMaKH);
        tvMaKH.setText(String.valueOf(KH.getMaKH()));
        TextView tvTenKH = (TextView) viewitem.findViewById(R.id.tvTenKH);
        tvTenKH.setText(KH.getTenKH());
        TextView tvEmail = (TextView) viewitem.findViewById(R.id.tvEmail);
        tvEmail.setText(KH.getEmail());
        TextView tvSdt = (TextView) viewitem.findViewById(R.id.tvSdt);
        tvSdt.setText(KH.getSdt());
        ImageView btnDelete = viewitem.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Th??ng b??o!");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("X??c nh???n x??a?");
                builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cursor dt = DBhelper.GetData("select * from KH_PVC where maKH = '"+ arrayList.get(position).getMaKH() +"'");
                        if(dt.moveToNext()){
                            Toast.makeText(context, "Kh??ng th??? x??a kh??ch h??ng!", Toast.LENGTH_SHORT).show();
                        } else{
                            DBhelper.deleteKH(arrayList.get(position));
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "X??a kh??ch h??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
        ImageView btnEditKH = viewitem.findViewById(R.id.btnEditKH);
        btnEditKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POSITION = position;
                Intent intent = new Intent(context, SuaKHActivity.class);
                context.startActivity(intent);
            }
        });
        ImageView btnSendMail = viewitem.findViewById(R.id.btnSendMail);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMail(KH);
            }
        });
        return viewitem;
    }

    private void SendMail(KH KH) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.sendmail);
        EditText edtReciverMail = dialog.findViewById(R.id.ReceiverEmail);
        edtReciverMail.setText(KH.getEmail());
        EditText edtSubjectMail = dialog.findViewById(R.id.SubjectEmail);
        edtSubjectMail.setText("FILE H??A ????N PHI???U V???N CHUY???N");
        EditText editMessageMail = dialog.findViewById(R.id.MessageEmail);
        editMessageMail.setText("K??nh g???i qu?? kh??ch h??ng File H??a ????n phi???u v???n chuy???n.\n\nXin ch??n th??nh c???m ??n qu?? kh??ch ???? tin t?????ng v?? s??? d???ng d???ch v??? c???a ch??ng t??i!\n\nCh??c qu?? kh??ch m???t ng??y l??m vi???c vui v???.");
        Button btnSendMail = dialog.findViewById(R.id.SendEmail);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] recivers = edtReciverMail.getText().toString().split(",");
                intent.putExtra(Intent.EXTRA_EMAIL,recivers);
                intent.putExtra(Intent.EXTRA_SUBJECT, edtSubjectMail.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, editMessageMail.getText().toString());
                intent.setType("message/rfc822");
                context.startActivity(Intent.createChooser(intent, "Choose an email client" ));
            }
        });
        dialog.show();

    }
}
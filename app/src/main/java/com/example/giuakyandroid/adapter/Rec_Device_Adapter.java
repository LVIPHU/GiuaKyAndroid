package com.example.giuakyandroid.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.activity.FixDevice;
import com.example.giuakyandroid.activity.ReportActivity;
import com.example.giuakyandroid.model.Device;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class Rec_Device_Adapter extends RecyclerView.Adapter<Rec_Device_Adapter.ViewHolder>{
    private android.app.Activity activity;
    private ArrayList<Device> mList;


    public Rec_Device_Adapter(Activity activity, ArrayList<Device> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View deviceView = inflater.inflate(R.layout.recycler_device_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(deviceView);
        System.out.println(mList.size());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Device device=mList.get(position);
        holder.txtViewTenTB.setText("Tên thiết bị: "+device.getTenTB());
        holder.txtViewMaLoaiTB.setText("Mã loại: "+device.getMaLoai());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Bạn có chắc chắn muốn xoá?")
                        .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SqliteHelper db=new SqliteHelper(activity);
                                if(db.deleteDevice(device)==true){
                                    mList.remove(position);
                                    notifyItemRemoved(position);
                                }
                                else{
                                    Toast.makeText(activity.getApplicationContext(),"Không thể xóa thiết bị",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
        holder.btnFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity, FixDevice.class);
                intent.putExtra("NUMBER_OF_LIST",position);
                activity.startActivity(intent);
            }
        });

        holder.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity, ReportActivity.class);
                intent.putExtra("NUMBER_OF_LIST",position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtViewTenTB;
        private TextView txtViewMaLoaiTB;
        private AppCompatButton btnFix, btnDelete, btnReport;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTenTB = itemView.findViewById(R.id.txtViewTenTB);
            txtViewMaLoaiTB = itemView.findViewById(R.id.txtViewMaLoaiTB);
            btnFix = itemView.findViewById(R.id.btnFix);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnReport = itemView.findViewById(R.id.btnReport);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            System.out.println("Click");
            Device device=mList.get(getAdapterPosition());
            System.out.println(device.getMaTB());
            System.out.println(device.getTenTB());

            LayoutInflater inflater=LayoutInflater.from(activity);
            View view=inflater.inflate(R.layout.dialog_thong_tin_thiet_bi,null);
            AlertDialog dialogTT= new AlertDialog.Builder(activity)
                                .setView(view)
                                .setNegativeButton("Hủy",null)
                                .create();

            TextView txtViewTTMaTB,txtViewTTTenTB,txtViewTTXuatXu,txtViewTTLoai;
            ImageView imgViewTB;
            txtViewTTMaTB=view.findViewById(R.id.txtViewTTMaTB);
            txtViewTTTenTB=view.findViewById(R.id.txtViewTTTenTB);
            txtViewTTXuatXu=view.findViewById(R.id.txtViewTTXuatXu);
            txtViewTTLoai=view.findViewById(R.id.txtViewTTLoai);
            imgViewTB=view.findViewById(R.id.imgViewTB);

            txtViewTTMaTB.setText(device.getMaTB());
            txtViewTTTenTB.setText(device.getTenTB());
            txtViewTTXuatXu.setText(device.getXuatXu());
            txtViewTTLoai.setText(device.getMaLoai());
            byte[] imgByte=device.getImg();
            if(imgByte!=null) {
                try {
                    Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                    imgViewTB.setImageBitmap(imgBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try{
                    imgViewTB.setImageResource(R.drawable.question_mark);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            dialogTT.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    dialogTT.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogTT.dismiss();
                        }
                    });
                }
            });

            dialogTT.show();
        }
    }
}

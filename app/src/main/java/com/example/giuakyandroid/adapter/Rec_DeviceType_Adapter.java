package com.example.giuakyandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.activity.FixDeviceType;
import com.example.giuakyandroid.model.DeviceType;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class Rec_DeviceType_Adapter extends RecyclerView.Adapter<Rec_DeviceType_Adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<DeviceType> mList;

    public Rec_DeviceType_Adapter(Activity activity, ArrayList<DeviceType> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Rec_DeviceType_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View deviceTypeView = inflater.inflate(R.layout.recycler_devicetype_layout, parent, false);
        ViewHolder viewHolder = new Rec_DeviceType_Adapter.ViewHolder(deviceTypeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Rec_DeviceType_Adapter.ViewHolder holder, int position) {
        DeviceType deviceType=mList.get(position);
        holder.txtViewMaLoai.setText("Mã loại: "+deviceType.getMaLoai());
        holder.txtViewTenLoai.setText("Tên loại: "+deviceType.getTenLoai());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Bạn có chắc chắn muốn xoá?")
                        .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SqliteHelper db=new SqliteHelper(activity);
                                if(db.deleteDeviceType(deviceType)==true){
                                    mList.remove(position);
                                    notifyItemRemoved(position);
                                }
                                else{
                                    Toast.makeText(activity.getApplicationContext(),"Không thể xóa loại thiết bị",Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {
                Intent intent = new Intent(activity, FixDeviceType.class);
                intent.putExtra("NUMBER_OF_LIST",position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewMaLoai;
        private TextView txtViewTenLoai;
        private AppCompatButton btnFix, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewMaLoai=itemView.findViewById(R.id.txtViewMaLoai);
            txtViewTenLoai=itemView.findViewById(R.id.txtViewTenLoai);
            btnFix=itemView.findViewById(R.id.btnFix);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }

}

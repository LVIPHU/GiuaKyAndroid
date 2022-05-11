package com.example.giuakyandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Rec_Device_Adapter extends RecyclerView.Adapter<Rec_Device_Adapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Device> mList;

    public Rec_Device_Adapter(Context mContext, ArrayList<Device> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Device device=mList.get(position);
        holder.txtViewTenTB.setText(device.getTenTB());
        holder.txtViewMaLoaiTB.setText(device.getMaLoai());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewTenTB;
        private TextView txtViewMaLoaiTB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTenTB=itemView.findViewById(R.id.txtViewTenTB);
            txtViewMaLoaiTB=itemView.findViewById(R.id.txtViewMaLoaiTB);
        }

    }
}

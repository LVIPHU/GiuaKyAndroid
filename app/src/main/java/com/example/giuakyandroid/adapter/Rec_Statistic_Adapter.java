package com.example.giuakyandroid.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Statistic;

import java.util.ArrayList;

public class Rec_Statistic_Adapter extends RecyclerView.Adapter<Rec_Statistic_Adapter.ViewHolder>{
    private Activity activity;
    private ArrayList<Statistic> mList;

    public Rec_Statistic_Adapter(Activity activity, ArrayList<Statistic> mList){
        this.activity=activity;
        this.mList=mList;
    }


    @NonNull
    @Override
    public Rec_Statistic_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View statisticView = inflater.inflate(R.layout.recycler_statistic_layout, parent, false);
        Rec_Statistic_Adapter.ViewHolder viewHolder = new Rec_Statistic_Adapter.ViewHolder(statisticView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Statistic sta=mList.get(position);
        holder.txtViewTKMaTB.setText("Mã TB: "+sta.getDevice().getMaTB());
        holder.txtViewTKSoLan.setText("Số lần mượn: "+sta.getCount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtViewTKMaTB;
        private TextView txtViewTKSoLan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTKMaTB=itemView.findViewById(R.id.txtViewTKMaTB);
            txtViewTKSoLan=itemView.findViewById(R.id.txtViewTKSoLan);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            Statistic sta=mList.get(getAdapterPosition());

            final Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.dialog_thong_tin_thiet_bi);
            dialog.setCancelable(true);

            TextView txtViewTTMaTB,txtViewTTTenTB,txtViewTTXuatXu,txtViewTTLoai;
            ImageView imgViewTB;
            txtViewTTMaTB=dialog.findViewById(R.id.txtViewTTMaTB);
            txtViewTTTenTB=dialog.findViewById(R.id.txtViewTTTenTB);
            txtViewTTXuatXu=dialog.findViewById(R.id.txtViewTTXuatXu);
            txtViewTTLoai=dialog.findViewById(R.id.txtViewTTLoai);
            imgViewTB=dialog.findViewById(R.id.imgViewTB);

            txtViewTTMaTB.setText(sta.getDevice().getMaTB());
            txtViewTTTenTB.setText(sta.getDevice().getTenTB());
            txtViewTTXuatXu.setText(sta.getDevice().getXuatXu());
            txtViewTTLoai.setText(sta.getDevice().getMaLoai());
            byte[] imgByte=sta.getDevice().getImg();
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
            System.out.println(dialog);
            dialog.show();
        }
    }
}

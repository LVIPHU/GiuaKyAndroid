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
import com.example.giuakyandroid.activity.FixRoom;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class Rec_Room_Adapter extends RecyclerView.Adapter<Rec_Room_Adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<Room> mList;

    public Rec_Room_Adapter(Activity activity, ArrayList<Room> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Rec_Room_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View roomView = inflater.inflate(R.layout.recycler_room_layout, parent, false);
        Rec_Room_Adapter.ViewHolder viewHolder = new Rec_Room_Adapter.ViewHolder(roomView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Rec_Room_Adapter.ViewHolder holder, int position) {
        Room room=mList.get(position);
        holder.txtViewMaPhong.setText("Mã phòng: "+room.getMaPhong());
        holder.txtViewLoaiPhong.setText("Loại phòng: "+room.getLoaiPhong());
        holder.txtViewTang.setText("Tầng: "+room.getTang());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Bạn có chắc chắn muốn xoá?")
                        .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SqliteHelper db=new SqliteHelper(activity);
                                if(db.deleteRoom(room)==true){
                                    mList.remove(position);
                                    notifyItemRemoved(position);
                                }
                                else{
                                    Toast.makeText(activity.getApplicationContext(),"Không thể xóa phòng",Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(activity, FixRoom.class);
                intent.putExtra("NUMBER_OF_LIST",position);
                activity.startActivity(intent);
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewMaPhong;
        private TextView txtViewLoaiPhong;
        private TextView txtViewTang;
        private AppCompatButton btnFix, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewMaPhong=itemView.findViewById(R.id.txtViewMaPhong);
            txtViewLoaiPhong=itemView.findViewById(R.id.txtViewLoaiPhong);
            txtViewTang=itemView.findViewById(R.id.txtViewTang);
            btnFix=itemView.findViewById(R.id.btnFix);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }

    public boolean checkRoom(Room room){
        if(room.maPhong==null || room.maPhong.equals("")){
            Toast.makeText(activity.getApplicationContext(),"Vui lòng nhập mã phòng",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(room.loaiPhong==null || room.loaiPhong.equals("")){
            Toast.makeText(activity.getApplicationContext(),"Vui lòng nhập loại phòng",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(room.tang<0 || room.tang>10){
            Toast.makeText(activity.getApplicationContext(),"Tầng không hợp lệ",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}


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
import com.example.giuakyandroid.activity.FixAdmin;
import com.example.giuakyandroid.activity.FixRoom;
import com.example.giuakyandroid.activity.FixStaff;
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class Rec_Setting extends RecyclerView.Adapter<Rec_Setting.ViewHolder> {
    private Activity activity;
    private ArrayList<Account> mList;

    public Rec_Setting(Activity activity, ArrayList<Account> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Rec_Setting.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View roomView = inflater.inflate(R.layout.recycler_staff_layout, parent, false);
        Rec_Setting.ViewHolder viewHolder = new Rec_Setting.ViewHolder(roomView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Rec_Setting.ViewHolder holder, int position) {
        Account account=mList.get(position);
        holder.txtViewNameAdmin.setText("Tên : "+account.getName());
        holder.txtViewChucvuAdmin.setText("Chức vụ: "+account.getChucVu());
        holder.txtViewEmailAdmin.setText("Email: "+account.getEmail());

        holder.btnFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FixStaff.class);
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
        private TextView txtViewNameAdmin;
        private TextView txtViewChucvuAdmin;
        private TextView txtViewEmailAdmin;
        private AppCompatButton btnFix, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewNameAdmin=itemView.findViewById(R.id.txtViewNameStaff);
            txtViewChucvuAdmin=itemView.findViewById(R.id.txtViewChucvuStaff);
            txtViewEmailAdmin=itemView.findViewById(R.id.txtViewEmailStaff);
            btnFix=itemView.findViewById(R.id.btnFixStaff);

        }
    }

    //   public boolean checkAccount(Account account){
//        if(acc==null || room.maPhong.equals("")){
//            Toast.makeText(activity.getApplicationContext(),"Vui lòng nhập mã phòng",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if(room.loaiPhong==null || room.loaiPhong.equals("")){
    //         Toast.makeText(activity.getApplicationContext(),"Vui lòng nhập loại phòng",Toast.LENGTH_SHORT).show();
    //         return false;
    //     }
//        else if(room.tang<0 || room.tang>10){
//            Toast.makeText(activity.getApplicationContext(),"Tầng không hợp lệ",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
    //  }
}


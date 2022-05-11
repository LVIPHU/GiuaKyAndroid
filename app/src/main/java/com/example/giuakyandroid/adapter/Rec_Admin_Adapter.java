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
import com.example.giuakyandroid.model.Account;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

public class Rec_Admin_Adapter extends RecyclerView.Adapter<Rec_Admin_Adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<Account> mList;

    public Rec_Admin_Adapter(Activity activity, ArrayList<Account> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Rec_Admin_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View roomView = inflater.inflate(R.layout.recycler_admin_layout, parent, false);
        Rec_Admin_Adapter.ViewHolder viewHolder = new Rec_Admin_Adapter.ViewHolder(roomView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Rec_Admin_Adapter.ViewHolder holder, int position) {
        Account account=mList.get(position);
        holder.txtViewNameAdmin.setText("Tên : "+account.getName());
        holder.txtViewChucvuAdmin.setText("Chức vụ: "+account.getChucVu());
        holder.txtViewEmailAdmin.setText("Email: "+account.getEmail());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Bạn có chắc chắn muốn xoá?")
                        .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SqliteHelper db=new SqliteHelper(activity);

                                if(db.deleteAccount(account)==true){
                                    mList.remove(position);
                                    notifyItemRemoved(position);
                                }
                                else{
                                    Toast.makeText(activity.getApplicationContext(),"Không thể xóa tài khoản này ",Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(activity, FixAdmin.class);
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
            txtViewNameAdmin=itemView.findViewById(R.id.txtViewNameAdmin);
            txtViewChucvuAdmin=itemView.findViewById(R.id.txtViewChucvuAdmin);
            txtViewEmailAdmin=itemView.findViewById(R.id.txtViewEmailAdmin);
            btnFix=itemView.findViewById(R.id.btnFixAdmin);
            btnDelete=itemView.findViewById(R.id.btnDeleteAdmin);
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


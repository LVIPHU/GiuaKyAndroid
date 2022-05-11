package com.example.giuakyandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Detail;
import com.example.giuakyandroid.model.SqliteHelper;

import java.util.ArrayList;

/* Tạo RecyclerView.Adapter
 * là nơi xử lý dữ liệu và gán data cho RecyclerVIew.
 * Vai trò của Adapter sẽ chuyển đổi một object tại một vị trí trở thành 1 hàng của danh sách sẽ được gắn vào RecyclerView.
 * Tuy nhiên đối với RecyclerView Adapter sẽ yêu cầu "ViewHolder" object trong đó mô tả và cung cấp quyền truy cập vào tất cả các View trong mỗi item row.
 * Chúng ta sẽ tạo ra một Adapter và holder bên trong Rec_Detail_Adapter : public class ViewHolder extends RecyclerView.ViewHolder
 */
public class Rec_Detail_Adapter extends RecyclerView.Adapter<Rec_Detail_Adapter.ViewHolder>{
    /* Chúng ta đã có một Adapter và ViewHolder, giờ sẽ hoàn thiện nốt Adapter.
     * Đầu tiên tạo ra các biến cho danh sách các Detail và truyền chúng qua hàm tạo
     */
    // Lưu Context để dễ dàng truy cập
    private Context activity;
    //Dữ liệu hiện thị là danh sách Thông tin mượn thiết bị
    private ArrayList<Detail> mList;

    public Rec_Detail_Adapter(Activity activity, ArrayList<Detail> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    // Mọi Adapter sẽ có 3 phương thức quan trọng:

    // Tạo Ta đối tượng ViewHolder, trong nó chứa View hiện thị dữ liệu
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Nạp layout cho View biểu diễn phần tử thôn tin mượn thiết bị
        View deviceView = inflater.inflate(R.layout.recycler_borrow_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(deviceView);
        return viewHolder;
    }
    // chuyển dữ liệu phần tử vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail detail=mList.get(position);
        holder.txtViewMuonMaPhong.setText("Mã phòng: "+detail.getMaPhong());
        holder.txtViewMuonMaTB.setText("Mã thiết bị: "+detail.getMaTB());
        holder.txtViewMuonNgayMuon.setText("Ngày mượn: "+detail.getNgayMuon());
        holder.txtViewMuonNgayTra.setText("Ngày trả: ");
        holder.txtViewMuonSL.setText("Số lượng: "+detail.getSoLuong());
        holder.txtViewMuonUsername.setText("Username: "+detail.getUsername());
        holder.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Có phải bạn muốn trả thiết bị?")
                        .setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Detail detail = mList.get(position);
                                SqliteHelper db=new SqliteHelper(activity);
                                db.returnDevice(detail);
                                mList.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
    }

    // cho biết số phần tử của dữ liệu
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtViewMuonMaPhong;
        private TextView txtViewMuonMaTB;
        private TextView txtViewMuonNgayMuon;
        private TextView txtViewMuonNgayTra;
        private TextView txtViewMuonSL;
        private TextView txtViewMuonUsername;
        private Button btnReturn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewMuonMaPhong=itemView.findViewById(R.id.txtViewMuonMaPhong);
            txtViewMuonMaTB=itemView.findViewById(R.id.txtViewMuonMaTB);
            txtViewMuonNgayMuon=itemView.findViewById(R.id.txtViewMuonNgayMuon);
            txtViewMuonNgayTra=itemView.findViewById(R.id.txtViewMuonNgayTra);
            txtViewMuonSL=itemView.findViewById(R.id.txtViewMuonSL);
            txtViewMuonUsername=itemView.findViewById(R.id.txtViewMuonUsername);
            btnReturn=itemView.findViewById(R.id.btnReturn);
        }
    }
}

package com.example.giuakyandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.Device;

import java.util.ArrayList;

public class SpinnerDevice extends BaseAdapter {
    Context context;
    ArrayList<Device> listDevice;
    LayoutInflater inflater;

    public SpinnerDevice(Context applicationContext, ArrayList<Device> listDevice){
        this.context=applicationContext;
        this.listDevice=listDevice;
        inflater=LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return listDevice.size();
    }

    @Override
    public Device getItem(int position) {
        return listDevice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.spinner_device,null);
        TextView textView=(TextView) convertView.findViewById(R.id.textView3);
        ImageView imgView=(ImageView) convertView.findViewById(R.id.imageView);

        Device device=listDevice.get(position);
        textView.setText(device.getTenTB());
        byte[] imgByte=device.getImg();
        if(imgByte!=null) {
            try {
                Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                imgView.setImageBitmap(imgBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                imgView.setImageResource(R.drawable.question_mark);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return convertView;
    }
}

package com.example.giuakyandroid.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.adapter.Rec_Room_Adapter;
import com.example.giuakyandroid.model.Detail;
import com.example.giuakyandroid.model.Device;
import com.example.giuakyandroid.model.DeviceType;
import com.example.giuakyandroid.model.Room;
import com.example.giuakyandroid.model.SqliteHelper;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.*;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {
    private int position;
    private Device device;

    ArrayList<DeviceType> listType = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
    ArrayList<Detail> listDetails = new ArrayList<>();

    SqliteHelper db = new SqliteHelper(this);

    TableLayout table;
    AppCompatButton btnPhoto, btnGoBack, btnPDF;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        /*Pop up a notice that user must accept request READ | WRITE EXTERNAL STORAGE*/
        ActivityCompat.requestPermissions(ReportActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);
        setControl();
        setScreen();
        setEvent();
    }



    private void setControl() {
        table = findViewById(R.id.deviceTable);
        linearLayout = findViewById(R.id.deviceExportLayout);
        btnPhoto = findViewById(R.id.btnPhoto);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnPDF = findViewById(R.id.btnPDF);
    }

    private void setScreen() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("NUMBER_OF_LIST");
            //The key argument here must match that used in the other activity
        }
        ArrayList<Device> list = db.getAllDevice();
        device=list.get(position);
        listType=db.getAllDeviceType();
        for(int i=0;i<listType.size();i++){
            listName.add(listType.get(i).tenLoai);
        }

        listDetails = db.getAllDetailByMaTB(device.getMaTB());

        for( int i = 0 ; i < listDetails.size() ; i++)
        {
            TableRow row = new TableRow(this);

            TextView tv1 = new TextView(this);
            tv1.setText(getRoomType(listDetails.get(i).getMaPhong()));
            tv1.setPadding(100, 5,5,5);
            tv1.setTextColor(Color.BLACK);
            tv1.setWidth(450);

            TextView tv2 = new TextView(this);
            tv2.setText(String.valueOf(getRoomFloor(listDetails.get(i).getMaPhong())));
            tv2.setPadding(100, 5,5,5);
            tv2.setTextColor(Color.BLACK);

            TextView tv3 = new TextView(this);
            tv3.setText(listDetails.get(i).getNgayMuon());
            tv3.setPadding(150, 5,5,5);
            tv3.setTextColor(Color.BLACK);

            TextView tv4 = new TextView(this);
            tv4.setText(String.valueOf(listDetails.get(i).getSoLuong()));
            tv4.setPadding(200, 5,5,5);
            tv4.setTextColor(Color.BLACK);

            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            row.addView(tv4);

            table.addView(row);
        }
    }

    private void setEvent() {
        btnPhoto.setOnClickListener(view -> {
            ViewGroup viewGroup = table;
            screenshotToPhoto(viewGroup, "result");
            Toast.makeText(ReportActivity.this,
                    "Xuất hình ảnh thành công !",
                    Toast.LENGTH_LONG)
                    .show();
        });

        btnPDF.setOnClickListener(view -> {
            createPdfWithItext7(view);
            Toast.makeText(ReportActivity.this,
                    "Xuất tệp tin PDF thành công !",
                    Toast.LENGTH_LONG)
                    .show();
        });

        btnGoBack.setOnClickListener(view -> finish());
    }

    public String getRoomType(String maPhong){
        String loaiPhong = null;
        ArrayList<Room> listRoom = db.getAllRoom();
        for (int i = 0; i < listRoom.size(); i++) {
            if(listRoom.get(i).getMaPhong().equalsIgnoreCase(maPhong)){
                loaiPhong = listRoom.get(i).getLoaiPhong();
            }
        }
        return loaiPhong;
    }

    public int getRoomFloor(String maPhong){
        int tang = 0;
        ArrayList<Room> listRoom = db.getAllRoom();
        for (int i = 0; i < listRoom.size(); i++) {
            if(listRoom.get(i).getMaPhong().equalsIgnoreCase(maPhong)){
                tang = listRoom.get(i).getTang();
            }
        }
        return tang;
    }

    /**
     * @author Luong Vi Phu
     * This funtion uses bitmap library - a pre-installed Android libray so as to
     * capture screen and store the photo into root directory
     *
     * To check, open "View -> Tool Window -> Device File Explorer -> sdCard". We will see the stored photo
     * Remember choose "Synchronize" to refresh sdCard
     * */
    private static void screenshotToPhoto(View view, String filename) {
        /*Step 1*/
        Date date = new Date();


        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            /*Step 2*/
            String dirpath = Environment.getExternalStorageDirectory() + "";

            // File name
            String path = dirpath + "/" + filename + "-" + format +".jpeg";

            /*Step 3*/
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);

            /*Step 4*/
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

            /*Step 5*/
            outputStream.flush();
            outputStream.close();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    /**
     * @author Luong Vi Phu
     * Create Pdf with iText 7 - a third-party library to instanciate PDF file
     *
     * To check, open "View -> Tool Window -> Device File Explorer -> sdCard". We will see the stored photo
     * Remember choose "Synchronize" to refresh sdCard
     *
     * Step 1: instanciate output PDF File
     * Step 2: declare PdfWriter, PdfDocument and Document
     * Step 3: declare number of column and dimension
     *          STT   Loai phong   Tang    Ngay su dung      So luong
     * Step 4: declare header
     * Step 5: declare columns name
     * Step 6: insert divece information from database
     * */
    public void createPdfWithItext7(View v)
    {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                position = extras.getInt("NUMBER_OF_LIST");
                //The key argument here must match that used in the other activity
            }
            ArrayList<Device> list = db.getAllDevice();
            device=list.get(position);
            /*Step 1*/
            @SuppressLint("SdCardPath") String path = "/sdcard/DanhSachSinhVien.pdf";;
            File file = new File(path);
            OutputStream output = new FileOutputStream(file);

            /*Step 2*/
            PdfWriter writer = new PdfWriter(output);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);

            Document document = new Document(pdfDocument);

            /*Step 3*/
            float[] dimension = {1, 3, 1, 3, 2};
            Table table = new Table(UnitValue.createPercentArray(dimension));

            /*Step 4*/
            ArrayList<Detail> objects = db.getAllDetailByMaTB(device.getMaTB());

            Paragraph header = new Paragraph("THONG TIN SU DUNG THIET BI");
            Cell cell = new Cell(1, 5)
                    .add( header )
                    .setFontSize(13)
                    .setFontColor(DeviceGray.WHITE)
                    .setBackgroundColor(DeviceGray.GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(cell);

            /*Step 5*/
            DeviceGray color = new DeviceGray(0.75f);

            Paragraph column1 = new Paragraph("STT");
            Paragraph column2 = new Paragraph("Loai Phong");
            Paragraph column3 = new Paragraph("Tang");
            Paragraph column4 = new Paragraph("Ngay su dung");
            Paragraph column5 = new Paragraph("So luong");

            for (int i = 0; i < 2; i++)
            {
                Cell[] headerFooter = new Cell[]{
                        new Cell().setBackgroundColor(color).add(column1),
                        new Cell().setBackgroundColor(color).add(column2),
                        new Cell().setBackgroundColor(color).add(column3),
                        new Cell().setBackgroundColor(color).add(column4),
                        new Cell().setBackgroundColor(color).add(column5)
                };
                for (Cell hfCell : headerFooter) {
                    if (i == 0) {
                        table.addHeaderCell(hfCell);
                    } else {
                        //table.addFooterCell(hfCell);
                    }
                }
            }

            /*Step 6*/
            for (int i = 0; i < objects.size(); i++) {
                Detail detail = objects.get(i);
                String type = getRoomType(detail.getMaPhong());
                String floor = String.valueOf(getRoomFloor(detail.getMaPhong()));
                String day = detail.getNgayMuon();
                String qty = String.valueOf(detail.getSoLuong());

                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(i + 1))));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  type  )));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  floor   )));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  day  )));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  qty )));
            }

            document.add(table);
            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
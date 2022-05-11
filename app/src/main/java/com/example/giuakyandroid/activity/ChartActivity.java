package com.example.giuakyandroid.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;
import com.example.giuakyandroid.model.SqliteHelper;
import com.example.giuakyandroid.model.Statistic;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    SqliteHelper db=new SqliteHelper(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        PieChart pieChart = findViewById(R.id.barChart);
        Button btn = findViewById(R.id.btnReturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<Statistic> statistics = new ArrayList<>();
        statistics = db.statistic();
        ArrayList<PieEntry> list = new ArrayList<>();
        int count=0;
        double percent=0;
        for(int j=0;j<statistics.size();j++){
            count+=statistics.get(j).count;
        }
        for(int i=0;i<statistics.size();i++){
            if(count>0){
                percent = (statistics.get(i).count * 100) / count;
            }
            list.add(new PieEntry(statistics.get(i).count, (statistics.get(i).device.tenTB).toString() + " " + percent + "%"));
        }

        PieDataSet pieDataSet = new PieDataSet(list,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(20f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animateY(2000);
    }
}

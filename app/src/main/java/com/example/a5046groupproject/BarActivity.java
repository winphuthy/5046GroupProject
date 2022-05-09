package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a5046groupproject.databinding.ActivityBarBinding;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarActivity extends AppCompatActivity {
    private Button buttonPie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        ActivityBarBinding binding =
                ActivityBarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        buttonPie = view.findViewById(R.id.buttonPieGraph);
        buttonPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPieGraph();
            }
        });
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 10));
        barEntries.add(new BarEntry(1, 50));
        barEntries.add(new BarEntry(2, 40));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Budget");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        List<String> xAxisValues = new ArrayList<>(Arrays.asList("FOOD","PLAY","STUDY"));
        binding.barChart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        binding.barChart.invalidate();



    }
    public void openPieGraph(){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }
}
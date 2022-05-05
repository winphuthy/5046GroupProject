package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a5046groupproject.databinding.ActivityGraphBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private ActivityGraphBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGraphBinding binding = ActivityGraphBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(10.0f,"food"));
        pieEntries.add(new PieEntry(50.0f, "movie"));
        pieEntries.add(new PieEntry(40.0f, "study"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Budget");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        binding.barChart.setData(pieData);
        binding.barChart.setUsePercentValues(true);
        binding.barChart.setCenterText("Budget Percentage PieChart");
        binding.barChart.invalidate();
        /*BarDataSet barDataSet = new BarDataSet(barEntries, "Steps");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Sun", "Mon", "Tues",
                "Wed", "Thurs", "Fri","Sat"));
        binding.barChart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        barData.setBarWidth(1.0f);
        binding.barChart.setVisibility(View.VISIBLE);
        binding.barChart.animateY(4000);
        //description will be displayed as "Description Label" if not provided
        Description description = new Description();
        description.setText("Daily Steps");
        binding.barChart.setDescription(description);
        //refresh the chart
        binding.barChart.invalidate();*/
    }
}
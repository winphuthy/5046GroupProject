package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a5046groupproject.databinding.ActivityGraphBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private Button buttonBarGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGraphBinding binding = ActivityGraphBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        buttonBarGraph = view.findViewById(R.id.buttonBarGraph);
        buttonBarGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            openBarGraph();
            }
        });

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
    }
    public void openBarGraph(){
        Intent intent = new Intent(this, BarActivity.class);
        startActivity(intent);
    }
}
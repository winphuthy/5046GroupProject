package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a5046groupproject.databinding.ActivityBarBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarActivity extends AppCompatActivity {
    private Button buttonPie;
    private FirebaseAuth firebaseAuth;
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

        firebaseAuth = FirebaseAuth.getInstance();
        StoryRepository storyRepository = new StoryRepository(getApplication());
        String id = firebaseAuth.getCurrentUser().getUid();

        List<Story> result = storyRepository.getStoryFromCustomerInList(id);
        int size = result.size();
        float priceType1=0f, priceType2=0f, priceType3=0f;

        for (int i = 0; i < size; i++) {
            if(result.get(i).getConsumeType().equals("type1")){
                priceType1+=result.get(i).getPrice();
            }else if(result.get(i).getConsumeType().equals("type2")){
                priceType2+=result.get(i).getPrice();
            }else if(result.get(i).getConsumeType().equals("type3")){
                priceType3+=result.get(i).getPrice();
            }
        }

        float p1 =0f, p2 =0f, p3 =0f;

        p1 = ((priceType1)/(priceType1+priceType2+priceType3))*100f;
        p2 = ((priceType2)/(priceType1+priceType2+priceType3))*100f;
        p3 = ((priceType3)/(priceType1+priceType2+priceType3))*100f;

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, p1));
        barEntries.add(new BarEntry(1, p2));
        barEntries.add(new BarEntry(2, p3));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Budget");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Type1","Type2","Type3"));
        binding.barChart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        binding.barChart.invalidate();

    }
    public void openPieGraph(){
        finish();
    }
}
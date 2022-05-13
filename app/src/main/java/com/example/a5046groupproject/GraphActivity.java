package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a5046groupproject.databinding.ActivityGraphBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private Button buttonBarGraph;
    private FirebaseAuth firebaseAuth;
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

        // TODO: Data collect from this list

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


        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(p1,"Type 1"));
        pieEntries.add(new PieEntry(p2, "Type 2"));
        pieEntries.add(new PieEntry(p3, "Type 3"));

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
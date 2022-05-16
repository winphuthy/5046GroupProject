package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a5046groupproject.databinding.ActivityGraphBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private Button buttonBarGraph, startTime, finishTime, generateGraph;
    private FirebaseAuth firebaseAuth;
    private DatePickerDialog datePickerDialog;
    private Calendar day, afterDate, beforeDate;
    private ActivityGraphBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGraphBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        buttonBarGraph = view.findViewById(R.id.buttonBarGraph);
        startTime = view.findViewById(R.id.startDate);
        finishTime = view.findViewById(R.id.endDate);
        generateGraph = view.findViewById(R.id.generateGraph);

        buttonBarGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBarGraph();
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(startTime);
            }
        });
        finishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(finishTime);
            }
        });
        generateGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGraph();
            }
        });

    }
    public void openBarGraph(){
        Intent intent = new Intent(this, BarActivity.class);
        startActivity(intent);
    }
    @SuppressLint("ResourceType")
    private void datePicker(Button buttonType){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(buttonType.equals(startTime)){
                    String date = (dayOfMonth)+"/"+(month+1)+"/"+year;
                    buttonType.setText(date);
                }else if(buttonType.equals(finishTime)){
                    String date = (dayOfMonth)+"/"+(month+1)+"/"+year;
                    buttonType.setText(date);
                }
            }
        };
        Calendar calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DATE);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, 1, dateSetListener, y, m, d);
        datePickerDialog.show();
    }
    public void loadGraph(){
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        StoryRepository storyRepository = new StoryRepository(getApplication());
        String id = firebaseAuth.getCurrentUser().getUid();
        startTime = view.findViewById(R.id.startDate);
        finishTime = view.findViewById(R.id.endDate);

        afterDate = Calendar.getInstance();
        beforeDate = Calendar.getInstance();
        day = Calendar.getInstance();
        try {
            afterDate.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(startTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            beforeDate.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(finishTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Story> result = storyRepository.getStoryFromCustomerInList(id);
        int size = result.size();
        float priceType1=0f, priceType2=0f, priceType3=0f;
        beforeDate.add(Calendar.DATE,1);
        afterDate.add(Calendar.DATE,-1);
        for (int i = 0; i < size; i++) {
            try {
                day.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(result.get(i).storyTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(day.after(afterDate)&&day.before(beforeDate)){
                if(result.get(i).getConsumeType().equals("type1")){
                    priceType1+=result.get(i).getPrice();
                }else if(result.get(i).getConsumeType().equals("type2")){
                    priceType2+=result.get(i).getPrice();
                }else if(result.get(i).getConsumeType().equals("type3")){
                    priceType3+=result.get(i).getPrice();
                }
            }
            System.out.println(result.get(i).getStoryTime());
        }

        float p1 =0f, p2 =0f, p3 =0f;
        p1 = percent(priceType1,priceType2,priceType3);
        p2 = percent(priceType2,priceType1,priceType3);
        p3 = percent(priceType3,priceType1,priceType2);

        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(p1,"Type 1 %"));
        pieEntries.add(new PieEntry(p2, "Type 2 %"));
        pieEntries.add(new PieEntry(p3, "Type 3 %"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Budget");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        binding.barChart.setData(pieData);
        binding.barChart.setUsePercentValues(true);
        binding.barChart.setCenterText("Budget Percentage PieChart");
        binding.barChart.invalidate();
    }
    public float percent(float a, float b, float c){
        return (a/(a+b+c))*100f;
    }
}
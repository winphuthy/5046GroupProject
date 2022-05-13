package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.a5046groupproject.databinding.ActivityBarBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BarActivity extends AppCompatActivity {
    private Button buttonPie, startTime, finishTime, generateGraph;
    private FirebaseAuth firebaseAuth;
    private DatePickerDialog datePickerDialog;
    private Calendar day, afterDate, beforeDate;
    private ActivityBarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        binding = ActivityBarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        buttonPie = view.findViewById(R.id.buttonPieGraph);
        startTime = view.findViewById(R.id.startDateBar);
        finishTime = view.findViewById(R.id.endDateBar);
        generateGraph = view.findViewById(R.id.generateGraphBar);
        buttonPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPieGraph();
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
        startTime = view.findViewById(R.id.startDateBar);
        finishTime = view.findViewById(R.id.endDateBar);

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

        p1 = ((priceType1)/(priceType1+priceType2+priceType3))*100f;
        p2 = ((priceType2)/(priceType1+priceType2+priceType3))*100f;
        p3 = ((priceType3)/(priceType1+priceType2+priceType3))*100f;


        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, p1));
        barEntries.add(new BarEntry(1, p2));
        barEntries.add(new BarEntry(2, p3));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Budget");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Type1 %","Type2 %","Type3 %"));
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
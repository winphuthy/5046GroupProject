package com.example.a5046groupproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Activity2 extends AppCompatActivity implements EventCreate.EventListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        CalendarView calendarView = findViewById(R.id.calendarMain);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                TextView textView = (TextView) calendarView.findViewById(R.id.chooseDate);
                CharSequence date = (i2+"/"+(i1+1)+"/"+i);
                System.out.println(textView+"line 36");
                //textView.setText("test");
                openDialog();
            }
        });
    }
    public void openDialog(){
        EventCreate eventCreate = new EventCreate();
        eventCreate.show(getSupportFragmentManager(),"Line40");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void sendText(String title, String startTime, String endTime, String desc, String location){
        Calendar sTime = Calendar.getInstance();
        Calendar eTime = Calendar.getInstance();
        String time = "25/04/2022 10:30";

        try {
            sTime.setTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            eTime.setTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,eTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, desc)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        startActivity(intent);
        //System.out.println(cal+"line 52");
        //System.out.println(title + startTime + endTime + desc + location);
    }
}
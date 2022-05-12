package com.example.a5046groupproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements EventCreate.EventListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        CalendarView calendarView = findViewById(R.id.calendarMain);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                CharSequence date = (i2+"/"+(i1+1)+"/"+i);
                System.out.println(date.toString()+"line 36");
                openDialog(date);
            }
        });
    }
    public void openDialog(CharSequence date){
        EventCreate eventCreate = new EventCreate(date);
        eventCreate.show(getSupportFragmentManager(),"Line40");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void sendText(String date, String title, String startTime, String endTime, String desc, String location){
        Calendar sTime = Calendar.getInstance();
        Calendar eTime = Calendar.getInstance();
        startTime = date +" "+ startTime;
        endTime = date + " " + endTime;
        System.out.println("line 47 "+ startTime);
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
    }
}
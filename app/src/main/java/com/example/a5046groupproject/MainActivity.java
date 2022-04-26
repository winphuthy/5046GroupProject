package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.Collections;

public class MainActivity<service> extends AppCompatActivity {
    private Button buttonCalendar;
    private Button buttonMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalendar = findViewById(R.id.buttonCalendar);
        buttonMap = findViewById(R.id.buttonMap);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
    }
    public void openCalendar(){
        Intent intent = new Intent(this,Activity2.class);
        startActivity(intent);
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    // Show events on user's calendar.
}
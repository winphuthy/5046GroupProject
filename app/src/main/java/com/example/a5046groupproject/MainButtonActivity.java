package com.example.a5046groupproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainButtonActivity<service> extends AppCompatActivity {
    private Button buttonCalendar;
    private Button buttonMap;
    private Button buttonGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_main);

        buttonCalendar = findViewById(R.id.buttonCalendar);
        buttonMap = findViewById(R.id.buttonMap);
        buttonGraph = findViewById(R.id.buttonGraph);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGraph();
            }
        });
    }
    public void openCalendar(){
        Intent intent = new Intent(this,Activity2.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void openGraph(){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }
    // Show events on user's calendar.
}
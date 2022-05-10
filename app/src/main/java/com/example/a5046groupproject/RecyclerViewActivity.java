package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.a5046groupproject.databinding.ActivityMainBinding;
import com.example.a5046groupproject.databinding.ActivityRecyclerViewBinding;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity{
    private RecyclerView.LayoutManager layoutManager;
    private List<CourseResult> units;
    private RecyclerViewAdapter adapter;
    private ActivityRecyclerViewBinding binding;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            
        
    }
}
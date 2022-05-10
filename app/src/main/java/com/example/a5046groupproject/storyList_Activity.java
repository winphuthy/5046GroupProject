package com.example.a5046groupproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.a5046groupproject.adapter.StoryAdapter;
import com.example.a5046groupproject.databinding.ActivityStoryListBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.viewmodel.StoryViewModel;

import java.util.List;

public class storyList_Activity extends AppCompatActivity {
    private StoryViewModel storyViewModel;
    private ActivityStoryListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StoryAdapter adapter = new StoryAdapter();
        recyclerView.setAdapter(adapter);

//        storyViewModel = new ViewModelProviders.of(this).get(StoryViewModel.class);
        storyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StoryViewModel.class);
        storyViewModel.getAllStories().observe(this, new Observer<List<Story>>() {
            @Override
            public void onChanged(@Nullable List<Story> storyList) {
                adapter.setStories(storyList);
            }
        });
    }
}
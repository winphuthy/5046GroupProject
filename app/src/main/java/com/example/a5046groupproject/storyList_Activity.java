package com.example.a5046groupproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.a5046groupproject.adapter.StoryAdapter;
import com.example.a5046groupproject.databinding.ActivityStoryListBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.example.a5046groupproject.viewmodel.StoryViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class storyList_Activity extends AppCompatActivity {
    private static final String TAG = "storyList_Activity: ";
    private StoryViewModel storyViewModel;
    private ActivityStoryListBinding binding;
    private FirebaseAuth mAuth;
    private StoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d(TAG, "onCreate: start");

        mAuth = FirebaseAuth.getInstance();

        String id = mAuth.getCurrentUser().getUid();



//        Story test1 = new Story(id, "title xxx1", "detail xxx1", "type1", 11.0f, "123123123");
//        Story test2 = new Story(id, "title xxx2", "detail xxx2", "type2", 12.0f, "123123123");
//        Story test3 = new Story(id, "title xxx3", "detail xxx3", "type3", 13.0f, "123123123");

        StoryRepository sr = new StoryRepository(getApplication());
//        sr.insert(test1);
//        sr.insert(test2);
//        sr.insert(test3);

        Log.d(TAG, "onCreate: display livedata " + sr.getAllStories().toString());

//        binding.debug.setText(String.valueOf(sr.getAllStories().getValue().size()));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StoryAdapter(sr.getStoryFromCustomerInList(mAuth.getUid()));
        recyclerView.setAdapter(adapter);
        
        storyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StoryViewModel.class);
        storyViewModel.getAllStories().observe(this, new Observer<List<Story>>() {
            @Override
            public void onChanged(@Nullable List<Story> storyList) {
                adapter.setStories(storyList);
            }
        });
//        storyViewModel = new ViewModelProviders.of(this).get(StoryViewModel.class);
    }
}
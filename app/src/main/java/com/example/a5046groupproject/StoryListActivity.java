package com.example.a5046groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a5046groupproject.adapter.StoryAdapter;
import com.example.a5046groupproject.databinding.ActivityStoryListBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.fragment.AddStoryFragment;
import com.example.a5046groupproject.fragment.StoryListFragment;
import com.example.a5046groupproject.viewmodel.StoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoryListActivity extends AppCompatActivity {
    private ActivityStoryListBinding binding;
    private StoryViewModel storyViewModel;
    private StoryAdapter storyAdapter;
//binding the AddStoryFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStoryListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddStoryFragment());
            }
        });

        binding.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new StoryListFragment());
            }
        });
    }
    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, nextFragment);
        fragmentTransaction.commit();
    }
}
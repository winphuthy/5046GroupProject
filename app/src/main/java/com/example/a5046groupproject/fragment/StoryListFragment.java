package com.example.a5046groupproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a5046groupproject.R;
import com.example.a5046groupproject.adapter.StoryAdapter;
import com.example.a5046groupproject.databinding.FragmentStoryListBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.viewmodel.StoryViewModel;

import java.util.ArrayList;
import java.util.List;


public class StoryListFragment extends Fragment {
    private FragmentStoryListBinding binding;
    private StoryViewModel storyViewModel;
    private StoryAdapter adapter;

    public StoryListFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding  = FragmentStoryListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;

        //Initialization view model at Application level
        storyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StoryViewModel.class);


        //List<Story> storyList = storyViewModel.getAllStories();
        List<Story> storyList = new ArrayList<>();
        storyList.add(new Story("Demo","Demo",100,88888888));
        // Make recycler view usable
        // get Activity in Fragment, this in Activity.
        adapter = new StoryAdapter(getActivity(),storyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        // Inform the Adapter when database changed.
        storyViewModel.getAllStories().observe(getViewLifecycleOwner(), new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> stories) {
                adapter.setStories(stories);
            }
        });

        // Delete object in database when click the delete button
        adapter.setOnItemClickListener(new StoryAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                storyViewModel.delete(adapter.getCurrentStory(position));
            }
        });

        return view;
    }
}
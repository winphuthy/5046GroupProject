package com.example.a5046groupproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5046groupproject.R;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.Holder>{
    private List<Story> stories;
    private StoryRepository sr;

    public StoryAdapter(List<Story> stories, StoryRepository sr){
        this.stories = stories;
        this.sr = sr;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_item, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position){
        Story currentStory = stories.get(position);
        holder.textViewTitle.setText(currentStory.getStoryTitle());
        holder.textViewDetails.setText(currentStory.getDetails());
        holder.textViewType.setText(currentStory.getConsumeType());
        holder.textViewPrice.setText(String.valueOf(currentStory.getPrice()));
        holder.textViewTime.setText(currentStory.getStoryTime());
        holder.viewDelete.setOnClickListener(view -> {
            stories.remove(currentStory);
            sr.delete(currentStory);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount(){
        return stories.size();
    }

    public void setStories(List<Story> stories){
        this.stories = stories;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDetails;
        private TextView textViewType;
        private TextView textViewPrice;
        private TextView textViewTime;
        private ImageView viewDelete;

        public Holder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.ViewTitle);
            textViewDetails = itemView.findViewById(R.id.ViewDetail);
            textViewType = itemView.findViewById(R.id.ViewType);
            textViewPrice = itemView.findViewById(R.id.ViewPrice);
            textViewTime = itemView.findViewById(R.id.ViewTime);
            viewDelete = itemView.findViewById(R.id.view_item_delete);
        }
    }
}

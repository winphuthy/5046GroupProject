package com.example.a5046groupproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5046groupproject.R;
import com.example.a5046groupproject.databinding.ActivityStoryListBinding;
import com.example.a5046groupproject.entity.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.NoteHolder> {
    private List<Story> stories;
    private ActivityStoryListBinding binding;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Story currentStory = stories.get(position);
        holder.textViewTitle.setText(currentStory.getStoryTitle());
        holder.textViewDetails.setText(currentStory.getDetails());
        holder.textViewType.setText(String.valueOf(currentStory.getConsumeType()));
        holder.textViewPrice.setText(String.valueOf(currentStory.getPrice()));
        holder.textViewTime.setText(String.valueOf(currentStory.getStoryTime()));

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setStories(List<Story> notes) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDetails;
        private TextView textViewType;
        private TextView textViewPrice;
        private TextView textViewTime;


        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.ViewTitle);
            textViewDetails = itemView.findViewById(R.id.ViewDetail);
            textViewType = itemView.findViewById(R.id.ViewType);
            textViewPrice = itemView.findViewById(R.id.ViewPrice);
            textViewTime = itemView.findViewById(R.id.ViewTime);
        }
    }
}

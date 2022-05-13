package com.example.a5046groupproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5046groupproject.R;
import com.example.a5046groupproject.entity.Story;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    Context context;
    private List<Story> stories;
    private onItemClickListener onItemClickListener;

    public StoryAdapter(Context context, List<Story> stories){
        this.context = context;
        this.stories = stories;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_story_recycle_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        Story currentStory = stories.get(position);

        viewHolder.textViewTitle.setText(currentStory.getStoryTitle());
        /*
        viewHolder.textViewDetails.setText(currentStory.getDetails());
        */
        viewHolder.textViewType.setText(currentStory.getConsumeType());
        viewHolder.textViewPrice.setText(String.valueOf(currentStory.getPrice()));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.textViewTime.setText(String.valueOf(currentStory.getStoryTime()));

        if (onItemClickListener!=null){
            viewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int elementPosition = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(viewHolder.imageDelete,elementPosition);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    //Get the story which is ready to be deleted
    public Story getCurrentStory(int position){
        return stories.get(position);
    }

    // Interface callback reference from https://wiki.jikexueyuan.com/project/twenty-four-Scriptures/recycler-view-add-remove.html
    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewType;
        private final TextView textViewTitle;
        private final TextView textViewPrice;
        private final ImageView imageDelete;
        private final TextView textViewTime;


        public ViewHolder(View view) {
            super(view);
            textViewTitle = itemView.findViewById(R.id.text_story_title);
            textViewType = itemView.findViewById(R.id.text_consume_type);
            textViewPrice = itemView.findViewById(R.id.edit_text_price);
            textViewTime = itemView.findViewById(R.id.text_date);
            imageDelete = itemView.findViewById(R.id.image_delete);

    }
}}

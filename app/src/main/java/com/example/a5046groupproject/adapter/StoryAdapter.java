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
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_story_recycler_view, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        Story currentStory = stories.get(position);

        viewHolder.textViewTitle.setText(currentStory.getStoryTitle());
        viewHolder.textViewDetails.setText(currentStory.getDetails());
        viewHolder.textViewType.setText(String.valueOf(currentStory.getConsumeType()));
        viewHolder.textViewPrice.setText(String.valueOf(currentStory.getPrice()));
        /*
        DataFormat formatter = new SimpleDateFormat("dd/MM/yyyy")
        */
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
        private final TextView textViewTitle;
        private final TextView textViewDetails;
        private final TextView textViewType;
        private final TextView textViewPrice;
        private final TextView textViewTime;
        private final ImageView imageDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.ViewTitle);
            textViewDetails = itemView.findViewById(R.id.ViewDetail);
            textViewType = itemView.findViewById(R.id.ViewType);
            textViewPrice = itemView.findViewById(R.id.ViewPrice);
            textViewTime = itemView.findViewById(R.id.ViewTime);
            imageDelete = itemView.findViewById(R.id.image_delete);
        }
    }
}

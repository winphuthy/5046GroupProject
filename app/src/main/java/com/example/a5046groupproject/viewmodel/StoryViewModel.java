package com.example.a5046groupproject.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a5046groupproject.database.StoryDatabase;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StoryViewModel extends AndroidViewModel {
    private StoryRepository sRepository;
    private LiveData<List<Story>> allStories;

    public StoryViewModel(Application application){
        super(application);
        sRepository = new StoryRepository(application);
        allStories = sRepository.getAllStories();
    }
/* pending
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Story> findByIDFuture(final int userId){
        return sRepository.findByIDFuture(userId);
    }
*/

    public LiveData<List<Story>> getAllStories(){
        return allStories;
    }

    public List<Story> getStoryList() {return sRepository.getStoryList(); }

    public void insert(Story story){
        sRepository.insert(story);
    }

    public void delete(Story story){
        sRepository.delete(story);
    }

/*pending
    public void updateStory(Story story){
        sRepository.updateStory(story);
    }
    */
}


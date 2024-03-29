package com.example.a5046groupproject.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.a5046groupproject.dao.StoryDao;
import com.example.a5046groupproject.database.StoryDatabase;
import com.example.a5046groupproject.entity.Story;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class StoryRepository{
    private StoryDao storyDao;
    private LiveData<List<Story>> allStories;

    //Initialisation
    public StoryRepository(Application application){
        StoryDatabase db = StoryDatabase.getInstance(application);
        storyDao = db.storyDao();
        allStories = storyDao.getAllStories();
    }

    //Room executes this query on a separate thread
    public LiveData<List<Story>> getAllStories(){
        return allStories;
    }

    public List<Story> getAllInList(){
        return storyDao.getAllinStory();
    }

    public List<Story> getStoryFromCustomerInList(String customerid){
        List<Story> allStory = storyDao.getAllinStory();
        List<Story> result = new ArrayList<>();
        for (Story story : allStory) {
            if (story.ownerID.equals(customerid)) {
                result.add(story);
            }
        }
        return result;
    }

    //insert one
    public void insert(final Story story){
        StoryDatabase.databaseWriteExecutor.execute(new Runnable(){
            @Override
            public void run(){
                storyDao.insert(story);
            }
        });
    }

    //delete one
    public void delete(final Story story){
        StoryDatabase.databaseWriteExecutor.execute(new Runnable(){
            @Override
            public void run(){
                storyDao.delete(story);
            }
        });
    }

    public void updateStory(final Story story){
        StoryDatabase.databaseWriteExecutor.execute(new Runnable(){
            @Override
            public void run(){
                storyDao.updateStory(story);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Story> findByIDFuture(final int userId){

        return CompletableFuture.supplyAsync(new Supplier<Story>(){
            @Override
            public Story get(){
                return storyDao.findByID(userId);
            }
        }, StoryDatabase.databaseWriteExecutor);
    }
}

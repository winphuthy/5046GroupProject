package com.example.a5046groupproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a5046groupproject.entity.Story;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("SELECT * FROM story_table ORDER BY uid ASC")
    LiveData<List<Story>> getAllStories();

    @Query("SELECT * FROM story_table ORDER BY uid ASC")
    List<Story> getStoryList();

    @Insert
    void insert(Story story);

    @Delete
    void delete(Story story);

}

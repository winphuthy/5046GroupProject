package com.example.a5046groupproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a5046groupproject.entity.Activity;

import java.util.ArrayList;

public interface ActivityDAO {
    @Query("SELECT * FROM activity ORDER BY uid ASC")
    LiveData<ArrayList<Activity>> getAll();

    @Query("SELECT * FROM activity WHERE uid = :activity_id LIMIT 1")
    Activity findByID(int activity_id);

    @Insert
    void insert(Activity activity);

    @Delete
    void delete(Activity activity);

    @Update
    void updateActivity(Activity activity);

    @Query("DELETE FROM activity")
    void deleteAll();
}

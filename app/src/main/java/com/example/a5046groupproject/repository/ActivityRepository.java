package com.example.a5046groupproject.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.a5046groupproject.dao.ActivityDAO;
import com.example.a5046groupproject.database.ActivityDatabase;
import com.example.a5046groupproject.entity.Activity;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ActivityRepository {
    private ActivityDAO activityDao;
    private LiveData<ArrayList<Activity>> allActivities;

    public ActivityRepository(Application application){
        ActivityDatabase db = ActivityDatabase.getInstance(application);
        activityDao =db.activityDao();
        allActivities= activityDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<ArrayList<Activity>> getAllActivities() {
        return allActivities;
    }

    public  void insert(final Activity activity){
        ActivityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                activityDao.insert(activity);
            }
        });
    }

    public void deleteAll(){
        ActivityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                activityDao.deleteAll();
            }
        });
    }
    public void delete(final Activity activity){
        ActivityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                activityDao.delete(activity);
            }
        });
    }

    public void updateCustomer(final Activity activity){
        ActivityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                activityDao.updateActivity(activity);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Activity> findByIDFuture(final int activity_id) {

        return CompletableFuture.supplyAsync(new Supplier<Activity>() {
            @Override
            public Activity get() {
                return activityDao.findByID(activity_id);
            }
        }, ActivityDatabase.databaseWriteExecutor);
    }

        }

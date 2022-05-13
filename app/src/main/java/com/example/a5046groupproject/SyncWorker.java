package com.example.a5046groupproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DatabaseReference;

public class SyncWorker extends Worker{
    private static final String TAG = "SyncWorker";
    DatabaseReference dbReference;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork(){
        Data inputData = getInputData();
        int number = inputData.getInt("number", -1);
        
        String userid = inputData.getString("userid");
        
        Log.d(TAG, "doWork: number" + number);

        for (int i = number; i > 0; i--) {
            Log.d(TAG, "doWork: i was " + i);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Result.failure();
            }
        }
        return Result.success();
    }
}

package com.example.a5046groupproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.a5046groupproject.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class SyncWorker extends Worker{
    private static final String TAG = "SyncWorker";
    FirebaseAuth mAuth;
    DatabaseReference dbReference;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork(){
        Data inputData = getInputData();

        String name = inputData.getString("name");
        String description = inputData.getString("description");
        
        mAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance("https://groupproject-c6f4c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("UserProfile");
        
        String uid = mAuth.getCurrentUser().getUid(); 
        String email = mAuth.getCurrentUser().getEmail();
        
        User instant = new User(uid, email, name, description);
        dbReference.push().setValue(instant);
        Log.d(TAG, "doWork: user class is pushing ```");
        
        return Result.success();
    }
}

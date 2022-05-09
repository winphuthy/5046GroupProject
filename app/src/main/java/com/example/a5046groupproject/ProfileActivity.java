package com.example.a5046groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.a5046groupproject.databinding.ActivityProfileBinding;
import com.example.a5046groupproject.databinding.PopupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity{

    private ActivityProfileBinding binding;
    public PopupBinding popupBinding;
    private FirebaseAuth mAuth;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        mAuth = FirebaseAuth.getInstance();
        checkUser();
        
        binding.logoutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            checkUser();
        });
        
        binding.popupBtn.setOnClickListener(view -> {
            createNewDialogue();
        });

        Data data = new Data.Builder()
                .putInt("number", 10)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest downloadRequest = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .setInputData(data)
//                .setConstraints(constraints)
                .addTag("download")
                .build();
        
//        WorkManager.getInstance(this).enqueue(downloadRequest);

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorker.class, 1, TimeUnit.HOURS)
                        .setInputData(data)
                        .addTag("Periodic")
                        .build();
    }

    /**
     * check user whether is logged in
     */
    private void checkUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else {
            String email = user.getEmail();
            String id = user.getUid();
            binding.idLabel.setText(id);
            binding.emailLabel.setText(email);
        }
    }

    public void createNewDialogue() {
        dialogBuilder = new AlertDialog.Builder(this);
        popupBinding = PopupBinding.inflate(getLayoutInflater());
        final View contactPopup = getLayoutInflater().inflate(R.layout.popup, null);

        // TODO: add input 

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        popupBinding.saveBtn.setOnClickListener(view -> {
            // TODO: add save function
        });


    }
}
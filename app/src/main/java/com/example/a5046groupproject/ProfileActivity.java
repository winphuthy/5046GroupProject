package com.example.a5046groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import android.widget.Button;
import android.widget.EditText;

import com.example.a5046groupproject.databinding.ActivityProfileBinding;
import com.example.a5046groupproject.databinding.PopupBinding;
import com.example.a5046groupproject.entity.User;
import com.example.a5046groupproject.viewmodel.ProfileActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity{

    private ActivityProfileBinding binding;
    public PopupBinding popupBinding;
    private FirebaseAuth mAuth;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;
    private ProfileActivityViewModel profileActivityViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        mAuth = FirebaseAuth.getInstance();
        profileActivityViewModel =
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfileActivityViewModel.class);
        
        User test = new User("","", "", "");

        User instant = profileActivityViewModel.getUser();
        instant.setUid(mAuth.getCurrentUser().getUid());
        instant.setEmail(mAuth.getCurrentUser().getEmail());
        profileActivityViewModel.setUser(instant);
        instant = null;
        
        checkUser();
        
        binding.logoutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            checkUser();
        });
        
        binding.popupBtn.setOnClickListener(view -> {
            createNewDialogue();
        });

        binding.rv.setOnClickListener(view -> {
            startActivity(new Intent(this, storyList_Activity.class));
        });
        
        binding.roomtest.setOnClickListener(view -> {
            startActivity(new Intent(this, RoomActivity.class));
        });

        binding.ziheng.setOnClickListener(view -> {
            startActivity(new Intent(this, MainButtonActivity.class));
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
            startActivity(new Intent(this, LoginPage.class));
            finish();
        }else {
            String email = user.getEmail();
            String id = user.getUid();
            binding.idLabel.setText(id);
            binding.emailLabel.setText(email);
            binding.username.setText(profileActivityViewModel.getUser().getName());
            binding.description.setText(profileActivityViewModel.getUser().getDescription());
        }
    }

    public void createNewDialogue() {
        dialogBuilder = new AlertDialog.Builder(this);
//        popupBinding = PopupBinding.inflate(getLayoutInflater());
        final View contactPopup = getLayoutInflater().inflate(R.layout.popup, null);

        EditText input_name = (EditText) contactPopup.findViewById(R.id.input_name);
        EditText input_description = (EditText) contactPopup.findViewById(R.id.input_description);
        Button saveBtn = (Button)contactPopup.findViewById(R.id.saveBtn);
        Button deleteBtn = (Button)contactPopup.findViewById(R.id.deleteBtn);

        // TODO: add input

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        saveBtn.setOnClickListener(view -> {
            // TODO: add save function
            String name = input_name.getText().toString();
            String description = input_description.getText().toString();
            
            profileActivityViewModel.getUser().setName(name);
            profileActivityViewModel.getUser().setDescription(description);

            checkUser();
            
            dialog.dismiss();
            
        });

        deleteBtn.setOnClickListener(view -> {
            input_name.setText("");
            input_description.setText("");
        });


    }
}
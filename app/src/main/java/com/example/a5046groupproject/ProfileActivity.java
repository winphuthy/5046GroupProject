package com.example.a5046groupproject;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a5046groupproject.databinding.ActivityProfileBinding;
import com.example.a5046groupproject.databinding.PopupBinding;
import com.example.a5046groupproject.entity.User;
import com.example.a5046groupproject.viewmodel.ProfileActivityViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity{

    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;
    private ProfileActivityViewModel profileActivityViewModel;
    private final String TAG = this.getClass().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        profileActivityViewModel =
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfileActivityViewModel.class);


        User instant = profileActivityViewModel.getUser();
        instant.setUid(mAuth.getCurrentUser().getUid());
        instant.setEmail(mAuth.getCurrentUser().getEmail());
        profileActivityViewModel.setUser(instant);
        instant = null;

        DatabaseReference df = FirebaseDatabase
                .getInstance("https://groupproject-c6f4c-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference()
                .child("UserProfile");

        String name = profileActivityViewModel.getUser().getName();
        String description = profileActivityViewModel.getUser().getDescription();

        Data data = new Data.Builder()
                .putString("name", name)
                .putString("description", description)
                .build();

        checkUser();

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorker.class, 1, TimeUnit.MINUTES)
                        .setInputData(data)
                        .addTag("Periodic")
                        .build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
        Toast.makeText(this, "1 min Count down", Toast.LENGTH_SHORT).show();

        binding.logoutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            checkUser();
        });

        binding.popupBtn.setOnClickListener(view -> {
            createNewDialogue();
        });

        binding.updateOnline.setOnClickListener(view -> {

//            FirebaseDatabase.getInstance("https://groupproject-c6f4c-default-rtdb.asia-southeast1.firebasedatabase.app")
//                    .getReference("user")
//                    .push()
//                    .setValue("Hello World");


            OneTimeWorkRequest downloadRequest = new OneTimeWorkRequest
                    .Builder(SyncWorker.class)
                    .setInputData(data)
//                .setConstraints(constraints)
                    .addTag("upload")
                    .build();

            WorkManager.getInstance(this).enqueue(downloadRequest);

            Log.d(TAG, "onCreate: upload success");
        });

        binding.downloadLocal.setOnClickListener(view -> {
            df.limitToLast(1).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>(){
                @Override
                public void onSuccess(DataSnapshot dataSnapshot){
                    String a = dataSnapshot.getChildren().toString();
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        String name = s.child("name").getValue().toString();
                        String description = s.child("description").getValue().toString();
                        String uid = s.child("uid").getValue().toString();
                        String email = s.child("email").getValue().toString();
                        profileActivityViewModel.setUser(new User(uid, email, name, description));
                    }
                    checkUser();
                }
            });
        });

//        binding.ziheng.setOnClickListener(view -> {
//            startActivity(new Intent(this, MainButtonActivity.class));
//        });
        

    }

    /**
     * check user whether is logged in
     */
    private void checkUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginPage.class));
            finish();
        } else {
            String email = user.getEmail();
            String id = user.getUid();
            binding.idLabel.setText(id);
            binding.emailLabel.setText(email);
            binding.username.setText(profileActivityViewModel.getUser().getName());
            binding.description.setText(profileActivityViewModel.getUser().getDescription());
        }
    }

    public void createNewDialogue(){
        dialogBuilder = new AlertDialog.Builder(this);
//        popupBinding = PopupBinding.inflate(getLayoutInflater());
        final View contactPopup = getLayoutInflater().inflate(R.layout.popup, null);

        EditText input_name = (EditText) contactPopup.findViewById(R.id.input_name);
        EditText input_description = (EditText) contactPopup.findViewById(R.id.input_description);
        Button saveBtn = (Button) contactPopup.findViewById(R.id.saveBtn);
        Button deleteBtn = (Button) contactPopup.findViewById(R.id.deleteBtn);

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
package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.a5046groupproject.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity{

    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    
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
}
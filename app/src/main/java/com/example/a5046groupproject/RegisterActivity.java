package com.example.a5046groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.a5046groupproject.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity{

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Toast.makeText(RegisterActivity.this, "get into RegisterActivity oncreate", Toast.LENGTH_SHORT).show();


        mAuth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(view -> {
            createUser();
        });
    }

    /**
     * Create a user by login with email
     */
    private void createUser(){

        String email = binding.etRegEmail.getText().toString();
        String password = binding.etRegPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.etRegPass.setError("Email cannot be empty");
            binding.etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            binding.etRegPass.setError("Password cannot be empty");
            binding.etRegPass.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task){
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginPage.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
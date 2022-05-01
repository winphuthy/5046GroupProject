package com.example.a5046groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.a5046groupproject.databinding.ActivityLoginPageBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity{

    public ActivityLoginPageBinding binding;
    private FirebaseAuth mAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "GOOGLE_SIGN";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        mAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(view -> {
            userLogin();
        });

        binding.registerBtn.setOnClickListener(view -> {
//            Toast.makeText(LoginPage.this, "user on click ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPage.this, RegisterActivity.class));
        });
        
        
    }
    

  

    private void userLogin(){
//        Toast.makeText(LoginPage.this, "login on click ", Toast.LENGTH_SHORT).show();

        String email = binding.username.getText().toString();
        String password = binding.password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.username.setError("Email cannot be empty");
            binding.username.requestFocus();
        } else {
            if (TextUtils.isEmpty(password)) {
                binding.password.setError("Password cannot be empty");
                binding.password.requestFocus();
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()) {
//                            startActivity(new Intent(this, MainActivity.class));
                            Toast.makeText(LoginPage.this, "Add next activity ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
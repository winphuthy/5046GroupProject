package com.example.a5046groupproject;

import androidx.annotation.LongDef;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity{

    public ActivityLoginPageBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient gsc;
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "GOOGLE_SIGN";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        mAuth = FirebaseAuth.getInstance();
        checkUser();

        // building a google sign in option used by google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        binding.googleLoginBtn.setOnClickListener(view -> {
            Log.d(TAG, "onClick google login");
            Intent intent = gsc.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });


        binding.loginBtn.setOnClickListener(view -> {
            userLogin();
        });

        binding.registerBtn.setOnClickListener(view -> {
//            Toast.makeText(LoginPage.this, "user on click ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPage.this, RegisterActivity.class));
        });


    }

    /**
     * check the User where logged in already
     */
    private void checkUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "checkUser: Logged in already");
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }

    /**
     * action when receive the result, create the account information class and verify with firebase
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google login intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    /**
     * verify with firebase
     * @param account GoogleSignInAccount class, the account information.
     */
    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account){
        Log.d(TAG, "firebaseAuthWithGoogleAccount: go login with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "onSuccess: Logged in");

                    FirebaseUser user = mAuth.getCurrentUser();

                    String id = user.getUid();
                    String email = user.getEmail();

                    Log.d(TAG, "onSuccess: Email: " + email);
                    Log.d(TAG, "onSuccess: ID: " + id);

                    if (authResult.getAdditionalUserInfo().isNewUser()) {
                        Log.d(TAG, "onSuccess: new Account Created");
                        Toast.makeText(LoginPage.this, "Account Created...\n" + email,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "onSuccess:  Existing user " + email);
                        Toast.makeText(LoginPage.this, "Existing user " + email,
                                Toast.LENGTH_SHORT).show();
                    }

                    //Start profile activity
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();

                }).addOnFailureListener(e -> {
            Log.d(TAG, "firebaseAuthWithGoogleAccount: " + e.getMessage());
        });
    }

    /**
     * Email login with firebase
     */
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
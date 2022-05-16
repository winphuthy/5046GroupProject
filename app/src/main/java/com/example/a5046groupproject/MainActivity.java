package com.example.a5046groupproject;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.a5046groupproject.databinding.FragmentHomeBinding;
import com.example.a5046groupproject.databinding.NavHeaderMain3Binding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a5046groupproject.databinding.ActivityMain2Binding;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    private FirebaseAuth mAuth;
    private final String APIKEY = "cMvTqZus4loBTTGJuLCXu0wU3ZIj0An0";
    private Request request;
    private JSONObject jsonObject = null;
    private TextView currencyEx1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain3.toolbar);
        binding.appBarMain3.fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        
        
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cost, R.id.nav_navigation,
                R.id.nav_todo, R.id.nav_report, R.id.nav_profile, R.id.nav_setting)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main3);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setCurrency(binding);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main3);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setCurrency(ActivityMain2Binding binding){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/convert?to=USD&from=AUD&amount=1")
                .addHeader("apikey", APIKEY)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String jsonData = response.body().string();
            System.out.println(jsonData);
            jsonObject = new JSONObject(jsonData);

            String rate =jsonObject.getString("result");
            System.out.println("rate:" + rate);
            currencyEx1 = binding.getRoot().findViewById(R.id.CurrencyRate1);
            currencyEx1.setText(rate);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
package com.example.a5046groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a5046groupproject.databinding.ActivityRoomBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RoomActivity extends AppCompatActivity{
    private ActivityRoomBinding binding;
    private FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        
        String id = mAuth.getCurrentUser().getUid();
        
        Story test1 = new Story(id, "title xxx1", "detail xxx1", "type1", 11.0f, "123123123");
        Story test2 = new Story(id, "title xxx2", "detail xxx2", "type2", 12.0f, "123123123");
        Story test3 = new Story(id, "title xxx3", "detail xxx3", "type3", 13.0f, "123123123");

        StoryRepository sr = new StoryRepository(getApplication());
        sr.insert(test1);
        sr.insert(test2);
        sr.insert(test3);
        String content = "";
        List<Story> list = sr.getAllInList();
        int size = list.size();
        float priceType1=0f, priceType2=0f, priceType3=0f;
        float price = 0f;
        for (int i = 0; i < size; i++) {
            if(list.get(i).getConsumeType().equals("type1")){
                priceType1+=list.get(i).getPrice();
            }
            price += list.get(i).getPrice();
            System.out.println(price);
        }
        binding.roomtest.setText(content);
        System.out.println(price + "line 43");
        
        
    }
}
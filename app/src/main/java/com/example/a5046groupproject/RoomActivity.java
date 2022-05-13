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
        for (int i = 0; i < size; i++) {
            content = content + list.get(i).getOwnerID() + "\n";
        }
        binding.roomtest.setText(content);
        
        
    }
}
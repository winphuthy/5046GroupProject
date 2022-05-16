package com.example.a5046groupproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a5046groupproject.adapter.StoryAdapter;
import com.example.a5046groupproject.databinding.ActivityStoryListBinding;
import com.example.a5046groupproject.databinding.PopupBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.repository.StoryRepository;
import com.example.a5046groupproject.viewmodel.StoryViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StoryList_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "storyList_Activity: ";
    private StoryViewModel storyViewModel;
    private ActivityStoryListBinding binding;
    private FirebaseAuth mAuth;
    private StoryAdapter adapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private int t1Minute, t1Hour;
    private Button timePickerBtn;
    private StoryRepository sr;
    DatePickerDialog.OnDateSetListener setListener;
    private String spinnerSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityStoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d(TAG, "onCreate: start");

        mAuth = FirebaseAuth.getInstance();

        String id = mAuth.getCurrentUser().getUid();
        
        sr = new StoryRepository(getApplication());


//        Story test1 = new Story(id, "title xxx1", "detail xxx1", "type1", 11.0f, "123123123");
//        Story test2 = new Story(id, "title xxx2", "detail xxx2", "type2", 12.0f, "123123123");
//        Story test3 = new Story(id, "title xxx3", "detail xxx3", "type3", 13.0f, "123123123");

        StoryRepository sr = new StoryRepository(getApplication());
//        sr.insert(test1);
//        sr.insert(test2);
//        sr.insert(test3);

        Log.d(TAG, "onCreate: display livedata " + sr.getAllStories().toString());

//        binding.debug.setText(String.valueOf(sr.getAllStories().getValue().size()));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StoryAdapter(sr.getStoryFromCustomerInList(mAuth.getUid()), sr);
        recyclerView.setAdapter(adapter);

        storyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StoryViewModel.class);
        storyViewModel.getAllStories().observe(this, new Observer<List<Story>>(){
            @Override
            public void onChanged(@Nullable List<Story> storyList){
                adapter.setStories(storyList);
            }
        });
//        storyViewModel = new ViewModelProviders.of(this).get(StoryViewModel.class);

        binding.addTitle.setOnClickListener(view -> createNewDialogue());
    }

    public void createNewDialogue(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopup = getLayoutInflater().inflate(R.layout.popupstory, null);


        EditText title = (EditText) contactPopup.findViewById(R.id.story_title);
        Spinner type = (Spinner) contactPopup.findViewById(R.id.story_type);
        EditText price = (EditText) contactPopup.findViewById(R.id.story_price);
        timePickerBtn = (Button) contactPopup.findViewById(R.id.story_time);
        EditText detail = (EditText) contactPopup.findViewById(R.id.story_detail);
        Button saveBtn = (Button) contactPopup.findViewById(R.id.story_saveBtn);
        Button clearBtn = (Button) contactPopup.findViewById(R.id.story_clearBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.consuming_types,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // TODO: add input 

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        saveBtn.setOnClickListener(view -> {
            String input_title = title.getText().toString();
            String input_type = spinnerSelect;
            float input_price = Float.valueOf(price.getText().toString());
            String input_time = timePickerBtn.getText().toString();
            String input_detail = detail.getText().toString();
            String ownerID = mAuth.getCurrentUser().getUid();
            if (input_type.isEmpty() || input_time.isEmpty() || input_title.isEmpty()) {
                Toast.makeText(this, "All label should not be empty", Toast.LENGTH_SHORT).show();
            }
            else{
                sr.insert(new Story(ownerID, input_title, input_detail, input_type, input_price, input_time));
                Log.d(TAG, "createNewDialogue: SAVE");
                dialog.dismiss();
            }
        });

        clearBtn.setOnClickListener(view -> {
            title.setText("");
            price.setText("");
            detail.setText("");
            timePickerBtn.setText("");
        });

        timePickerBtn.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_MinWidth, setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
            datePickerDialog.show();
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth){
                month = month + 1;
                String data = dayOfMonth + "/" + month + "/" + year;
                timePickerBtn.setText(data);
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l){
        spinnerSelect = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), spinnerSelect, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView){

    }

/*    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                hour = selectedHour;
                minute = selectedMinute;
                timePickerBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, *//*style,*//* onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }*/
}
package com.example.a5046groupproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Locale;

public class EventCreate extends AppCompatDialogFragment {
    private EditText inputTitle;
    private EditText inputStartTime;
    private EditText inputEndTime;
    private EditText inputDesc;
    private EditText inputLocation;
    private EventListener eventListener;
    private Button buttonTime;
    private int hour, minute;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_event, null);
        buttonTime = view.findViewById(R.id.buttonTimePicker);
        builder.setView(view)
                .setTitle("Add Event")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = inputTitle.getText().toString();
                        String startTime = inputStartTime.getText().toString();
                        String endTime = inputEndTime.getText().toString();
                        String desc = inputDesc.getText().toString();
                        String location = inputLocation.getText().toString();
                        System.out.println(CalendarContract.Calendars._ID + "line 50");
                        //eventListener.sendText(title,startTime,endTime,desc,location);


                    }
                });
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourPicker, int minutePicker) {
                        hour = hourPicker;
                        minute = minutePicker;
                        buttonTime.setText(String.format("%02d:%02d",hour,minute));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),2, onTimeSetListener, hour, minute, true);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();
            }
        });
        inputTitle = view.findViewById(R.id.inputTitle);
        inputStartTime = view.findViewById(R.id.inputStartTime);
        inputEndTime = view.findViewById(R.id.inputEndTime);
        inputDesc = view.findViewById(R.id.inputDesc);
        inputLocation = view.findViewById(R.id.inputLocation);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            eventListener = (EventListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+"line 52");
        }
    }

    public interface EventListener{
        void sendText (String title, String startTime, String endTime, String desc, String location) ;
    }
}

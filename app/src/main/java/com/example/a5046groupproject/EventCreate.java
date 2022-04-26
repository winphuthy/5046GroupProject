package com.example.a5046groupproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EventCreate extends AppCompatDialogFragment {
    private EditText inputTitle;
    private EditText inputStartTime;
    private EditText inputEndTime;
    private EditText inputDesc;
    private EditText inputLocation;
    private EventListener eventListener;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_event, null);

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

                        eventListener.sendText(title,startTime,endTime,desc,location);

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

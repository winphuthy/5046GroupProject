package com.example.a5046groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.a5046groupproject.databinding.PopuptestBinding;
import com.example.a5046groupproject.databinding.PopupBinding;

public class PopupTest extends AppCompatActivity {

    public PopuptestBinding binding;
    public PopupBinding popupBinding;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PopuptestBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.popupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDiaglog();
            }
        });
    }

    public void createNewDiaglog() {
        dialogBuilder = new AlertDialog.Builder(this);
        popupBinding = PopupBinding.inflate(getLayoutInflater());
        final View contactPopup = getLayoutInflater().inflate(R.layout.popup, null);

        //加入输入项

        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        popupBinding.saveBtn.setOnClickListener(view -> {
            //保存功能
        });


    }
}
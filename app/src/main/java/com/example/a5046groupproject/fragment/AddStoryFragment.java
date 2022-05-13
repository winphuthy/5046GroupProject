package com.example.a5046groupproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.a5046groupproject.R;
import com.example.a5046groupproject.databinding.FragmentAddStoryBinding;
import com.example.a5046groupproject.entity.Story;
import com.example.a5046groupproject.viewmodel.StoryViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AddStoryFragment extends Fragment {
    private FragmentAddStoryBinding binding;
    private StoryViewModel storyViewModel;

    public AddStoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        storyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StoryViewModel.class);

        // Initialization the Date picker.
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build();

        // Show current date when use haven't select a date.
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(datePicker.getSelection());
        binding.textDate.setText(date);


        binding.sliderCalorie.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                int i = (int) binding.sliderCalorie.getValue();
                binding.editTextPrice.setText(String.valueOf(i));
            }
        });


        // Date selection
        binding.buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is single date picker
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getActivity().getSupportFragmentManager(),"date picker");
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        // parse the value from millis to formatted date, you can use ZonedDateTime
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String date = formatter.format(selection);
                        binding.textDate.setText(date);
                    }
                });
            }
        });


        return view;
    }


    private boolean checkDish() {
        boolean isNull = binding.textInputTitle.getEditText().getText().toString().isEmpty();
        if (isNull) {
            binding.textInputTitle.setError("Title can't be empty");
            binding.textInputTitle.setErrorEnabled(true);
        } else {
            binding.textInputTitle.setErrorEnabled(false);
        }
        return !isNull;
    }

    private boolean addMeal(String time) {
        if (checkDish() && Long.parseLong(time) <= MaterialDatePicker.todayInUtcMilliseconds()) {
            // Get meal type
            int chipID = binding.chipGroup.getCheckedChipId();
            Chip chip = binding.chipGroup.findViewById(chipID);
            String consumeType = chip.getText().toString();
            // Get title
            String storyTitle = binding.textInputTitle.getEditText().getText().toString() + "\n" +
            binding.textInputDetails.getEditText().getText().toString();
            // get calorie
            float price = Float.parseFloat(binding.editTextPrice.getText().toString());

            Story story = new Story(consumeType, storyTitle, price, Long.parseLong(time));
            storyViewModel.insert(story);

            showDiyToast(getContext(), "New Story added", R.drawable.ic_baseline_check_circle_24);

            return true;
        } else {
            showDiyToast(getContext(), "Adding Story Failed", R.drawable.ic_baseline_cancel_24);
            return false;
        }
    }

    // https://blog.csdn.net/yinzhijiezhan/article/details/100892184
    // Thanks for "yzjgogo" providing the idea of diy toast
    // And I make it more diy-able.
    private void showDiyToast(Context context,String content,int imageId){
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        View layout = View.inflate(context, R.layout.toast_layout,null);

        ImageView imageView = layout.findViewById(R.id.image_toast);
        imageView.setImageResource(imageId);

        TextView textView = layout.findViewById(R.id.text_toast);
        textView.setText(content);

        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}

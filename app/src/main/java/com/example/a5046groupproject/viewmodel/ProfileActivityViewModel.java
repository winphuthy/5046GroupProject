package com.example.a5046groupproject.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a5046groupproject.databinding.ActivityProfileBinding;
import com.example.a5046groupproject.entity.User;

public class ProfileActivityViewModel extends ViewModel{

    private final String TAG = this.getClass().getSimpleName();
    private final String noData = "No detail";
    private MutableLiveData<User> userMutableLiveData;

    public User getUser(){
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            userMutableLiveData.setValue(new User(noData,noData,noData,noData));
        }
        return userMutableLiveData.getValue();
    }

    public MutableLiveData<User> getUserMutableLiveData(){
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            userMutableLiveData.setValue(new User(noData,noData,noData,noData));
        }
        return userMutableLiveData;
    }

    public void setUser(User user){
        userMutableLiveData.postValue(user);
    }
}

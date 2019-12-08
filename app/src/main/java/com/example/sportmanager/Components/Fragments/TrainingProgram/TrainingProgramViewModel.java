package com.example.sportmanager.Components.Fragments.TrainingProgram;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainingProgramViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TrainingProgramViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
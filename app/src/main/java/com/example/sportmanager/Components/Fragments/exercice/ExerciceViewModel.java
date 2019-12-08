package com.example.sportmanager.Components.Fragments.exercice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExerciceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExerciceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
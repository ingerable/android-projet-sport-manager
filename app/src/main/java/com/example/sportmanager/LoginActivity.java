package com.example.sportmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportmanager.Components.LoginFragment;
import com.example.sportmanager.Database.AppDatabase;

public class LoginActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = LoginFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.main_linearLayout, loginFragment, "main_fragment").commit();

    }

}

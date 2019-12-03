package com.example.sportmanager;

import android.app.Application;
import com.example.sportmanager.data.Domain.User;

public class MyApplication extends Application {

    private User connectedUser = null;

    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
}

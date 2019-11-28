package com.example.sportmanager.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Session {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
}

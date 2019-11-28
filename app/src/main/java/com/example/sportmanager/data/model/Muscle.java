package com.example.sportmanager.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Muscle {


    private int id;
    @PrimaryKey
    private String nom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

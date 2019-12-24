package com.example.sportmanager.data.Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Session {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int order;
    private String name = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

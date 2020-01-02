package com.example.sportmanager.data.Domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private float weight;
    private int age;
    private String hashPass;
    private String pathImage;
    private int favoriteTrainingProgramId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getFavoriteTrainingProgramId() {
        return favoriteTrainingProgramId;
    }

    public void setFavoriteTrainingProgramId(int favoriteTrainingProgramId) {
        this.favoriteTrainingProgramId = favoriteTrainingProgramId;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getFirstname() + " " + this.getLastname();
    }
}

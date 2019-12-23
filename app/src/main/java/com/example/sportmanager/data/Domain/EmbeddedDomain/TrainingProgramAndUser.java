package com.example.sportmanager.data.Domain.EmbeddedDomain;

import androidx.room.Embedded;

import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.User;

public class TrainingProgramAndUser {

    @Embedded
    TrainingProgram trainingProgram;

    @Embedded(prefix = "trainingProgram_creator")
    User user;

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

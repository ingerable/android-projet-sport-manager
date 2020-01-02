package com.example.sportmanager.data.Domain;

import androidx.room.Entity;

@Entity(primaryKeys = {"trainingProgramId", "sessionId"})
public class TrainingProgramSession {

    private int trainingProgramId;

    private int sessionId;

    public int getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(int trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}

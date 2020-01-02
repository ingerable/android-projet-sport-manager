package com.example.sportmanager.data.Domain;

import androidx.room.Entity;

@Entity(primaryKeys = {"sessionId", "exerciceId"} )
public class SessionExercice {


    private int sessionId;

    private int exerciceId;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(int exerciceId) {
        this.exerciceId = exerciceId;
    }
}

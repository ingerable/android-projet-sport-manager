package com.example.sportmanager.data.Domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TrainingProgramSessions {

    @Embedded
    private TrainingProgram trainingProgram;

    @Relation(parentColumn = "id", entityColumn = "trainingProgramId", entity = Session.class)
    List<Session> sessions;

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}

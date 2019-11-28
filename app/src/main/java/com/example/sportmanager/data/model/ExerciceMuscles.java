package com.example.sportmanager.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ExerciceMuscles {

    @Embedded
    private Exercice exercice;

    @Relation(parentColumn = "id", entityColumn = "exerciceId", entity = Muscle.class)
    private List<Muscle> trainedMuscles;

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public List<Muscle> getTrainedMuscles() {
        return trainedMuscles;
    }

    public void setTrainedMuscles(List<Muscle> trainedMuscles) {
        this.trainedMuscles = trainedMuscles;
    }
}

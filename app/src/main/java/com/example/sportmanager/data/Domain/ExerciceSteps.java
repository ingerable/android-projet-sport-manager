package com.example.sportmanager.data.Domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ExerciceSteps {

    @Embedded
    private Exercice exercice;

    @Relation(parentColumn = "id", entityColumn = "exerciceId", entity = Step.class)
    private List<Step> steps;

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}

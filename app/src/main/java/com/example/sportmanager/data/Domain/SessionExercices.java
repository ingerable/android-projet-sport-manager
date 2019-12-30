package com.example.sportmanager.data.Domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SessionExercices {

    @Embedded
    private Session session;

    @Relation(parentColumn = "id", entityColumn = "sessionId", entity = Exercice.class)
    private List<Exercice> exercices;
}

package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sportmanager.data.Domain.Exercice;
import com.example.sportmanager.data.Domain.Session;

import java.util.List;

@Dao
public interface ExerciceDao {

    @Query("SELECT * FROM exercice")
    List<Exercice> getAll();

    @Insert
    void insertAll(Exercice... exercices);

    @Delete
    void delete(Exercice session);

    @Query("SELECT * FROM exercice WHERE id = :id")
    Exercice findById(int id);

    @Update
    void updateExercices(Exercice... exercices);
}

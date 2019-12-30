package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.Exercice;

import java.util.List;

@Dao
public interface ExerciceDao {

    @Query("SELECT * FROM exercice")
    List<Exercice> getAll();

    @Insert
    void insertAll(Exercice... exercices);

    @Delete
    void delete(Exercice session);
}

package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgram;

import java.util.List;

@Dao
public interface SessionDao {

    @Query("SELECT * FROM session")
    List<Session> getAll();

    @Query("SELECT * FROM session WHERE session.id = :trainingProgramId")
    List<Session> getByTrainingProgramId(int trainingProgramId);

    @Insert
    void insertAll(Session... sessions);

    @Delete
    void delete(Session session);

}

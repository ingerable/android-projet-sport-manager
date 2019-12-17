package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.TrainingProgram;

import java.util.List;

@Dao
public interface TrainingProgramDao {

    @Query("SELECT * FROM trainingprogram")
    List<TrainingProgram> getAll();

    @Insert
    void insertAll(TrainingProgram... trainingPrograms);

    @Delete
    void delete(TrainingProgram trainingProgram);

}

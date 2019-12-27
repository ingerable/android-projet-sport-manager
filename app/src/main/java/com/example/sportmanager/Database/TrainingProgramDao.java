package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM trainingprogram WHERE id = :id")
    TrainingProgram findById(int id);

    @Query("SELECT * FROM trainingprogram WHERE user_id = :userId")
    List<TrainingProgram> getByUserId(int userId);

    @Update
    void updateTrainingPrograms(TrainingProgram... trainingPrograms);

}

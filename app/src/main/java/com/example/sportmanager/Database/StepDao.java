package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.Step;

import java.util.List;

@Dao
public interface StepDao {

    @Insert
    void insertAll(Step... steps);

    @Query("SELECT * FROM step WHERE exerciceId = :exerciceId AND stepPosition = :stepPosition")
    Step findByExerciceIdAndPosition(int exerciceId, int stepPosition);

    @Query("SELECT * FROM step ORDER BY stepPosition ASC")
    List<Step> findAll();
}

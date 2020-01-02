package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgramSession;

import java.util.List;

@Dao
public interface TrainingProgramSessionDao {

    @Query("SELECT * FROM session s INNER JOIN trainingprogramsession ts ON s.id = ts.sessionId WHERE ts.trainingProgramId = :trainingProgramId")
    List<Session> getByTrainingProgramId(int trainingProgramId);

    @Insert
    void insert(TrainingProgramSession trainingProgramSession);
}

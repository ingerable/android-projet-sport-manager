package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.Exercice;
import com.example.sportmanager.data.Domain.SessionExercice;

import java.util.List;

@Dao
public interface SessionExerciceDao {

    @Query("SELECT * FROM exercice e INNER JOIN sessionexercice se ON e.id = se.exerciceId WHERE se.sessionId = :sessionId")
    List<Exercice> findExercicesBySessionId(int sessionId);

    @Insert
    long insert(SessionExercice sessionExercice);

    @Query("SELECT * FROM sessionexercice WHERE sessionId = :sessionId AND exerciceId = :exerciceId")
    SessionExercice findBySessionAndExerciceId(int sessionId, int exerciceId);

    @Delete
    void delete(SessionExercice... sessionExercices);

    @Query("SELECT * FROM sessionexercice WHERE sessionId = :sessionId")
    List<SessionExercice> findBySessionId(int sessionId);

    @Query("SELECT * FROM sessionexercice WHERE exerciceId = :exerciceId")
    List<SessionExercice> findByExerciceId(int exerciceId);
}

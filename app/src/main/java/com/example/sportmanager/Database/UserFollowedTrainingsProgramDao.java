package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.UserFollowedTrainingsProgram;

import java.util.List;

@Dao
public interface UserFollowedTrainingsProgramDao {

    @Query("SELECT * from userfollowedtrainingsprogram WHERE userId = :userId")
    List<UserFollowedTrainingsProgram> findFollowedTrainingsProgramByUser(int userId);

    @Query("SELECT * from userfollowedtrainingsprogram WHERE userId = :userId AND followedTrainingProgramId = :followedTrainingProgramId")
    UserFollowedTrainingsProgram findTrainingProgramFollowedByUserAndTrainingProgram(int userId, int followedTrainingProgramId);

    @Insert
    void update(UserFollowedTrainingsProgram userFollowedTrainingsProgram);

    @Delete
    void delete(UserFollowedTrainingsProgram userFollowedTrainingsProgram);

    @Query("SELECT * FROM trainingprogram tp INNER JOIN UserFollowedTrainingsProgram uftp ON tp.id = uftp.followedTrainingProgramId WHERE uftp.userId = :userId")
    List<TrainingProgram> findTrainingProgramByUserId(int userId);
}

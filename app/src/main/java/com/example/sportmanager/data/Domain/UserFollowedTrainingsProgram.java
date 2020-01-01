package com.example.sportmanager.data.Domain;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "followedTrainingProgramId"})
public class UserFollowedTrainingsProgram {


    private int userId;

    private int followedTrainingProgramId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowedTrainingProgramId() {
        return followedTrainingProgramId;
    }

    public void setFollowedTrainingProgramId(int followedTrainingProgramId) {
        this.followedTrainingProgramId = followedTrainingProgramId;
    }
}

package com.example.sportmanager.data.Domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserTrainingPrograms {

    @Embedded
    private User creator;

    @Relation(parentColumn = "id", entityColumn = "creatorUserId", entity = TrainingProgram.class)
    private List<TrainingProgram> followedTrainingPrograms;

    @Relation(parentColumn = "id", entityColumn = "creatorUserId", entity = TrainingProgram.class)
    private List<TrainingProgram> createdTrainingPrograms;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<TrainingProgram> getFollowedTrainingPrograms() {
        return followedTrainingPrograms;
    }

    public void setFollowedTrainingPrograms(List<TrainingProgram> followedTrainingPrograms) {
        this.followedTrainingPrograms = followedTrainingPrograms;
    }

    public List<TrainingProgram> getCreatedTrainingPrograms() {
        return createdTrainingPrograms;
    }

    public void setCreatedTrainingPrograms(List<TrainingProgram> createdTrainingPrograms) {
        this.createdTrainingPrograms = createdTrainingPrograms;
    }
}

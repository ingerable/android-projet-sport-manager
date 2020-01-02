package com.example.sportmanager.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sportmanager.data.Domain.Exercice;
import com.example.sportmanager.data.Domain.Muscle;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.SessionExercice;
import com.example.sportmanager.data.Domain.Step;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.TrainingProgramSession;
import com.example.sportmanager.data.Domain.User;
import com.example.sportmanager.data.Domain.UserFollowedTrainingsProgram;

@Database(entities = {
        Exercice.class,
        Muscle.class,
        Session.class,
        Step.class,
        TrainingProgram.class,
        User.class,
        UserFollowedTrainingsProgram.class,
        TrainingProgramSession.class,
        SessionExercice.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract TrainingProgramDao TrainingProgramDao();

    public abstract SessionDao sessionDao();

    public abstract ExerciceDao exerciceDao();

    public abstract StepDao stepDao();

    public abstract UserFollowedTrainingsProgramDao userFollowedTrainingsProgramDao();

    public abstract TrainingProgramSessionDao trainingProgramSessionDao();

    public abstract SessionExerciceDao sessionExerciceDao();

    public static AppDatabase getAppDatabase(Context context)
    {
        if (INSTANCE == null) { //@TODO creer classe async pour les requetes DB
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}

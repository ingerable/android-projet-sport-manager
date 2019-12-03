package com.example.sportmanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sportmanager.data.Domain.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE user.id = :id")
    User findById(int id);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE user.login = :login")
    User findByLogin(String login);

    @Insert
    void insertAll(User... users);

    @Query("SELECT * FROM user WHERE hashPass = :hashPass AND login = :username")
    User findByLoginAndHashPass(String hashPass, String username);

    @Delete
    void delete(User user);
}

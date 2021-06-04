package com.example.trophyemall.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

/**
 * Esta es la interfaz Dao utilizada para post, donde se declaran las funciones para hacer consultas
 * y modificar la base de datos local
 */

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Delete
    void delete(Post post);

    @Update
    void update(Post dia);

    @Query("DELETE FROM " + Post.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM " + Post.TABLE_NAME)
    LiveData<List<Post>> getAllEntries();

    @Query("SELECT COUNT(*) FROM " + Post.TABLE_NAME)
    Single<Integer> getTotalEntries();
}

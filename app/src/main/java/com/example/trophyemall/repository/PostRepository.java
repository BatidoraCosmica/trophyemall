package com.example.trophyemall.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trophyemall.model.Post;
import com.example.trophyemall.model.PostDao;
import com.example.trophyemall.model.PostDatabase;

import java.util.List;

import io.reactivex.Single;

/**
 * Esta clase ejecuta las operaciones en la base de datos definidas en el Dao a través de las funciones
 * que llaman al Dao
 */

public class PostRepository {
    private static volatile PostRepository INSTANCE;
    private PostDao pDao;
    private LiveData<List<Post>> entradas;

    private PostRepository(Application application) {
        pDao = PostDatabase.getDatabase(application).postDao();
        entradas = pDao.getAllEntries();
    }

    public LiveData<List<Post>> getAllPost() {
        return entradas;
    }

    public Single<Integer> getTotalEntries() {
        return pDao.getTotalEntries();
    }

    public void insert(Post post) {
        PostDatabase.databaseWriter.execute(() -> pDao.insert(post));
    }

    public void update(Post post) {
        PostDatabase.databaseWriter.execute(() -> pDao.update(post));
    }

    public void delete(Post post) {
        PostDatabase.databaseWriter.execute(() -> pDao.delete(post));
    }


    public static PostRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (PostRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PostRepository(application);
                }
            }
        }

        return INSTANCE;
    }
}

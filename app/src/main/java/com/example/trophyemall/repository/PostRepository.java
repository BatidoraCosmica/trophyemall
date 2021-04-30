package com.example.trophyemall.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trophyemall.model.Post;
import com.example.trophyemall.model.PostDao;
import com.example.trophyemall.model.PostDatabase;

import java.util.List;

import io.reactivex.Single;

public class PostRepository {
    private static volatile PostRepository INSTANCE;
    private PostDao pDao;
    private LiveData<List<Post>> entradas;

    private PostRepository(Application application) {
        pDao = PostDatabase.getDatabase(application).postDao();
        entradas = pDao.getAllEntries();
    }

    public LiveData<List<Post>> getAllDiario() {
        return entradas;
    }

    public Single<Integer> getTotalEntries() {
        return pDao.getTotalEntries();
    }

    public void insert(Post diaDiario) {
        PostDatabase.databaseWriter.execute(() -> pDao.insert(diaDiario));
    }

    public void update(Post diaDiario) {
        PostDatabase.databaseWriter.execute(() -> pDao.update(diaDiario));
    }

    public void delete(Post diaDiario) {
        PostDatabase.databaseWriter.execute(() -> pDao.delete(diaDiario));
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

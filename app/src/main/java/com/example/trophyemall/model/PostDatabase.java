package com.example.trophyemall.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Post.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase{
    public abstract PostDao postDao();
    private static volatile PostDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriter = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static PostDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PostDatabase.class, "diario_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriter.execute(() -> {
                PostDao pDao = INSTANCE.postDao();
                pDao.insert(new Post("UsuarioGenerico1", "Otro", "SISISISISISISISISISI"));
                pDao.insert(new Post("UsuarioGenerico2", "Otro", "SISISISISISISISISISI"));
                pDao.insert(new Post("UsuarioGenerico3", "Otro", "SISISISISISISISISISI"));
                pDao.insert(new Post("UsuarioGenerico4", "Otro", "SISISISISISISISISISI"));
            });
        }
    };
}

package com.example.yahia.movieappudacitystage2.data_base;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;

@Database(entities = {Movie.class},version = 1,exportSchema = false)
public abstract class MovieRoomDataBase extends RoomDatabase {

    private static MovieRoomDataBase movieDatabaseInstance;
    private static final String DATABASE_NAME = "movie.db";
    private static final Object LOCK = new Object();

    public static MovieRoomDataBase getMovieDatabaseInstance(Context context){
        if(movieDatabaseInstance==null){
            synchronized (LOCK){
                movieDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieRoomDataBase.class,
                        MovieRoomDataBase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return movieDatabaseInstance;
    }
    public abstract MovieDao movieDao();
}
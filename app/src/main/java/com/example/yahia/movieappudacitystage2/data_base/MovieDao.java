package com.example.yahia.movieappudacitystage2.data_base;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;

import java.util.List;

@Dao
public interface MovieDao {
       @Query("select * from movie")
         List<Movie> getMovies();

        @Query("select * from movie where id = :id")
        Movie getMovie(int id);

       @Insert(onConflict = OnConflictStrategy.IGNORE)
         void insertMovie(Movie result);

       @Update(onConflict = OnConflictStrategy.REPLACE)
         void updateMovie(Movie result);

       @Delete
           void deleteMovie(Movie result);

}

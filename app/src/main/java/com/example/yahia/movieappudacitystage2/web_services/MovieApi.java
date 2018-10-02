package com.example.yahia.movieappudacitystage2.web_services;

import com.example.yahia.movieappudacitystage2.data.movie_data.MovieData;
import com.example.yahia.movieappudacitystage2.data.trailler_data.TraillerData;
import com.example.yahia.movieappudacitystage2.utils.UtilsHelper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
 //for movie
 @GET("{sort_path}?"+UtilsHelper.ApiKey)
             Call<MovieData>getMovieData(@Path("sort_path")String sort_path,@Query("page") int pageNumber);
 //for trailler
 @GET("{id_path}"+"/videos?language=en-US&"+UtilsHelper.ApiKey)
             Call<TraillerData>getTraillerData(@Path("id_path")String id_path);
}

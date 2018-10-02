package com.example.yahia.movieappudacitystage2.web_services;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static Retrofit retrofit;
    private static final Object LOCK = new Object();

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            synchronized (LOCK){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }
}

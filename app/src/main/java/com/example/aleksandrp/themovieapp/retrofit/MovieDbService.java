package com.example.aleksandrp.themovieapp.retrofit;

import com.example.aleksandrp.themovieapp.entity.Move;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AleksandrP on 19.05.2016.
 */
public interface MovieDbService {

//    http://api.themoviedb.org/3/movie/popular?api_key=300edfebf6c620fdf7166e06f2b0af8a

    @GET("movie/{filter}")
    Call<List<Move>> getListFilter(
            @Path("filter") String filter,
            @Query("api_key") String api_key);

}

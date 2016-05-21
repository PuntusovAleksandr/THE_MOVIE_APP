package com.example.aleksandrp.themovieapp.retrofit;

import com.example.aleksandrp.themovieapp.entity.Move;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by AleksandrP on 19.05.2016.
 */
public interface ThemoviedbServvice {

    @GET("users/{user}/repos")
    Call<List<Move>> getListPopular(@Path("api_key") String user);

    @GET("users/{user}/repos")
    Call<List<Move>> getListRated (@Path("api_key") String user);

    @GET("users/{user}/repos")
    Call<List<Move>> getListFavorite(@Path("api_key") String user);

}

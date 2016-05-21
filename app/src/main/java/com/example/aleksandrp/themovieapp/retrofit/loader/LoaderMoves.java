package com.example.aleksandrp.themovieapp.retrofit.loader;

import com.example.aleksandrp.themovieapp.entity.Move;
import com.example.aleksandrp.themovieapp.retrofit.ThemoviedbServvice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by AleksandrP on 19.05.2016.
 */
public class LoaderMoves {

    private Retrofit mRetrofit;
    private ThemoviedbServvice mServvice;

    private String BASE_URL = "http://api.themoviedb.org/3/movie/popular?";

    public LoaderMoves() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

    }

    public List<Move> getPopularMove() {
        mServvice = mRetrofit.create(ThemoviedbServvice.class);
//        Call<List<Move>> mListCall = mServvice.getListPopular()
        return null;
    }
}

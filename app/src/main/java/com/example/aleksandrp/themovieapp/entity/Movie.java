package com.example.aleksandrp.themovieapp.entity;

import java.util.List;

/**
 * Created by AleksandrP on 18.05.2016.
 */
public class Movie {

    private String page;
    private List<ItemMovie> mItemMovies;


    public String getPage() {
        return page;
    }

    public void setPage(String mPage) {
        page = mPage;
    }

    public List<ItemMovie> getItemMovies() {
        return mItemMovies;
    }

    public void setItemMovies(List<ItemMovie> mItemMovies) {
        this.mItemMovies = mItemMovies;
    }
}

package com.example.aleksandrp.themovieapp.entity;


import java.util.List;

/**
 * Created by AleksandrP on 21.05.2016.
 */
public class ItemMovie {

    private String id;
    private String original_title;
    private String overview;
    private String release_date;
    private String backdrop_path;
    private String popularity;

    private String runtime;
    private String homepage;

    private List<String> players;


    public ItemMovie(String mId, String mOriginal_title, String mOvierview, String mRelease_date,
                     String mBackdrop_path, String mPopularity) {
        id = mId;
        original_title = mOriginal_title;
        overview = mOvierview;
        release_date = mRelease_date;
        backdrop_path = mBackdrop_path;
        popularity = mPopularity;
    }

    public ItemMovie(String mId, String mOriginal_title, String mOverview, String mRelease_date,
                     String mBackdrop_path, String mPopularity, String mRuntime, String mHomepage,
                     List<String> mPlayers) {
        id = mId;
        original_title = mOriginal_title;
        overview = mOverview;
        release_date = mRelease_date;
        backdrop_path = mBackdrop_path;
        popularity = mPopularity;
        runtime = mRuntime;
        homepage = mHomepage;
        players = mPlayers;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> mPlayers) {
        players = mPlayers;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String mHomepage) {
        homepage = mHomepage;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String mRuntime) {
        runtime = mRuntime;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String mPopularity) {
        popularity = mPopularity;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String mBackdrop_path) {
        backdrop_path = mBackdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String mOverview) {
        overview = mOverview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String mRelease_date) {
        release_date = mRelease_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String mOriginal_title) {
        original_title = mOriginal_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        id = mId;
    }
}

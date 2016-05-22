package com.example.aleksandrp.themovieapp.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 22.05.2016.
 */
public class MovieFavorite extends RealmObject {

    @PrimaryKey
    private String id;
    private String original_title;
    private String overview;
    private String release_date;
    private String backdrop_path;
    private String popularity;

    private String runtime;
    private String homepage;

    private RealmList<RealmStringPath> players;

    public MovieFavorite() {
    }

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        id = mId;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String mOriginal_title) {
        original_title = mOriginal_title;
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

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String mBackdrop_path) {
        backdrop_path = mBackdrop_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String mPopularity) {
        popularity = mPopularity;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String mRuntime) {
        runtime = mRuntime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String mHomepage) {
        homepage = mHomepage;
    }

    public RealmList<RealmStringPath> getPlayers() {
        return players;
    }

    public void setPlayers(RealmList<RealmStringPath> mPlayers) {
        players = mPlayers;
    }
}

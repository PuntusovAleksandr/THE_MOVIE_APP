package com.example.aleksandrp.themovieapp.entity;

/**
 * Created by AleksandrP on 18.05.2016.
 */
public class Move {

    private String title, description, pathUrl;
    private int idMove;

    public Move(String mTitle, String mDescription, String mPathUrl, int mIdMove) {
        title = mTitle;
        description = mDescription;
        pathUrl = mPathUrl;
        idMove = mIdMove;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        title = mTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        description = mDescription;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String mPathUrl) {
        pathUrl = mPathUrl;
    }

    public int getIdMove() {
        return idMove;
    }

    public void setIdMove(int mIdMove) {
        idMove = mIdMove;
    }
}

package com.example.aleksandrp.themovieapp.entity;

import io.realm.RealmObject;

/**
 * Created by AleksandrP on 22.05.2016.
 */
public class RealmStringPath extends RealmObject {

    String path;

    public RealmStringPath() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String mPath) {
        path = mPath;
    }
}

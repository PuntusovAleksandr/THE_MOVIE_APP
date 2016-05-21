package com.example.aleksandrp.themovieapp.settings;

import android.content.SharedPreferences;

/**
 * Created by AleksandrP on 21.05.2016.
 */
public class Settings {


    /**
     * The constant FILE_NAME.
     */
// Settings xml file name
    public static final String FILE_NAME = "settings";

    private static final String FILE_1 = "file_1";


    public static void setData(String uri, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putString(FILE_1, uri);
        mEditor.apply();
    }


    public static String getData(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(FILE_1, "");
    }


}

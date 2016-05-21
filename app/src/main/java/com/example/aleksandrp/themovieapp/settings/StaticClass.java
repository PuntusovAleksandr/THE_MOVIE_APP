package com.example.aleksandrp.themovieapp.settings;

/**
 * Created by AleksandrP on 21.05.2016.
 */
public class StaticClass {

    private static String sFilter;

    public static String getFilter() {
        return sFilter;
    }

    public static void setFilter(String mFilter) {
        sFilter = mFilter;
    }
}

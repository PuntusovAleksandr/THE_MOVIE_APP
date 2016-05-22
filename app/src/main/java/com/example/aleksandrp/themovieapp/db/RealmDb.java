package com.example.aleksandrp.themovieapp.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aleksandrp.themovieapp.entity.ItemMovie;
import com.example.aleksandrp.themovieapp.entity.MovieFavorite;
import com.example.aleksandrp.themovieapp.entity.RealmStringPath;
import com.example.aleksandrp.themovieapp.params.StaticParams;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

/**
 * Created by AleksandrP on 22.05.2016.
 */
public class RealmDb  implements StaticParams{

    private static RealmDb sRealmDb;
    private Context context;
    private Realm mRealm;

    public static RealmDb getInstance(Context context) {
        if (sRealmDb == null) {
            sRealmDb = new RealmDb(context);
        }
        return sRealmDb;
    }


    private RealmDb(Context context) {
        this.context = context;
        if (sRealmDb == null) {
            setRealmData(context);
        }
    }

    /**
     * for creating (or change) data base, need reopen Realm
     * This method need calling after save data in Shared preference
     */
    public static void stopRealm() {
        if (sRealmDb != null) {
            sRealmDb.closeRealm();
        }
    }

    private void closeRealm() {
        if (mRealm != null) {
            mRealm.close();
            mRealm = null;
        }
    }

    private void setRealmData(Context context) {
        mRealm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(RealmDb.class.getName())
                        .schemaVersion(StaticParams.VERSION_DB)
                        .build()
        );
    }


    /**
     * add to db
     */
    public void addMovieToFavorite(ItemMovie mItemMovie) {
        RealmList<RealmStringPath> mRealmStringPaths = sreateRealmList(mItemMovie.getPlayers());

        mRealm.beginTransaction();
        MovieFavorite mMovieFavorite = mRealm.createObject(MovieFavorite.class); // Create a new object
        mMovieFavorite.setId(mItemMovie.getId());
        mMovieFavorite.setOriginal_title(mItemMovie.getOriginal_title());
        mMovieFavorite.setOverview(mItemMovie.getOverview());
        mMovieFavorite.setRelease_date(mItemMovie.getRelease_date());
        mMovieFavorite.setBackdrop_path(mItemMovie.getBackdrop_path());
        mMovieFavorite.setPopularity(mItemMovie.getPopularity());
        mMovieFavorite.setRuntime(mItemMovie.getRuntime());
        mMovieFavorite.setHomepage(mItemMovie.getHomepage());
        mMovieFavorite.setPlayers(mRealmStringPaths);

        mRealm.commitTransaction();
    }

    private RealmList<RealmStringPath> sreateRealmList(List<String> mPlayers) {
        RealmList<RealmStringPath> mStringPaths = new RealmList<>();
        for (int i = 0; i < mPlayers.size() ; i++) {
            mRealm.beginTransaction();
            RealmStringPath mPath = mRealm.createObject(RealmStringPath.class);
            mPath.setPath(mPlayers.get(i));
            mRealm.commitTransaction();
            mStringPaths.add(mPath);
        }
        return mStringPaths;
    }

    /**
     * get list favorite movies from db
     */
    public List<MovieFavorite> getMoviesFavorite() {
        return mRealm.where(MovieFavorite.class)
                .findAll();
    }

}

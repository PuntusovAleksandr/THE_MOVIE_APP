package com.example.aleksandrp.themovieapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aleksandrp.themovieapp.adapter.ListMovieAdapter;
import com.example.aleksandrp.themovieapp.db.RealmDb;
import com.example.aleksandrp.themovieapp.entity.ItemMovie;
import com.example.aleksandrp.themovieapp.fragment.MovieListFragment;
import com.example.aleksandrp.themovieapp.fragment.ShowMovieFragment;
import com.example.aleksandrp.themovieapp.params.StaticParams;
import com.example.aleksandrp.themovieapp.settings.StaticClass;

public class StartActivity extends AppCompatActivity implements
        StaticParams,
        ListMovieAdapter.ClickSelectMovie {

    private FragmentManager mFragmentManager;

    private boolean doubleBackToExitPressedOnce = false;

    public ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mFragmentManager = getFragmentManager();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        StaticClass.setFilter(getString(R.string.popular));     // set param filter moives
        startListFragment();

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//            mFragmentManager = getFragmentManager();
//            StaticClass.setFilter(getString(R.string.popular));     // set param filter moives
//            startListFragment();
//
//        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//        }

    }

    /**
     * set start fragment
     */
    public void startListFragment() {
        MovieListFragment mFragment =
                (MovieListFragment) getFragmentManager().findFragmentByTag(MOVIE_LIST_FRAGMENT);

        if (mFragment == null) {
            mFragment = new MovieListFragment(StartActivity.this);
        }
        setFragment(mFragment, MOVIE_LIST_FRAGMENT);
    }


    /**
     * set movie fragment
     * @param mMovie - path to icon
     */
    public void startShowMovieFragment(ItemMovie mMovie) {
        ShowMovieFragment mFragment =
                (ShowMovieFragment) getFragmentManager().findFragmentByTag(SHOW_MOVIE_FRAGMENT);

        if (mFragment == null) {
            mFragment = new ShowMovieFragment(StartActivity.this, mMovie);
        }
        setFragment(mFragment, SHOW_MOVIE_FRAGMENT);
    }



    private void setFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .commit();
    }

    @Override
    public void onSelectMovie(ItemMovie mMovie) {
        startShowMovieFragment(mMovie);
    }

    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        ShowMovieFragment mFragment =
                (ShowMovieFragment) getFragmentManager().findFragmentByTag(SHOW_MOVIE_FRAGMENT);

        if (mFragment != null) {
            startListFragment();
            return;
        }

        if (doubleBackToExitPressedOnce) {
            System.exit(0);
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(StartActivity.this, R.string.double_click_to_exit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmDb.getInstance(StartActivity.this).stopRealm();
    }
}

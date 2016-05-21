package com.example.aleksandrp.themovieapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aleksandrp.themovieapp.adapter.ListMovieAdapter;
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        String text = "";
//
//        switch (id) {
//            case R.id.dnepr:
//                text = "1";
//                break;
//            case R.id.nikopol:
//                text = "2";
//                break;
//            case R.id.kiev:
//                text = "3";
//                break;
//        }
//        Toast.makeText(StartActivity.this, text, Toast.LENGTH_SHORT).show();
//
//        return super.onOptionsItemSelected(item);
//    }



}

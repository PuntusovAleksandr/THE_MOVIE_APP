package com.example.aleksandrp.themovieapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aleksandrp.themovieapp.adapter.ListMoveAdapter;
import com.example.aleksandrp.themovieapp.entity.Move;
import com.example.aleksandrp.themovieapp.fragment.MoveListFragment;
import com.example.aleksandrp.themovieapp.fragment.ShowMoveFragment;
import com.example.aleksandrp.themovieapp.params.StaticParams;

public class StartActivity extends AppCompatActivity implements
        StaticParams,
        ListMoveAdapter.ClickSelectMove{

    private FragmentManager mFragmentManager;

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mFragmentManager = getFragmentManager();

        startListFragment();
    }

    /**
     * set start fragment
     */
    public void startListFragment() {
        MoveListFragment mFragment =
                (MoveListFragment) getFragmentManager().findFragmentByTag(MOVE_LIST_FRAGMENT);

        if (mFragment == null) {
            mFragment = new MoveListFragment();
        }
        setFragment(mFragment, MOVE_LIST_FRAGMENT);
    }


    /**
     * set move fragment
     * @param mMove - path to icon
     */
    public void startShowMoveFragment(Move mMove) {
        ShowMoveFragment mFragment =
                (ShowMoveFragment) getFragmentManager().findFragmentByTag(SHOW_MOVE_FRAGMENT);

        if (mFragment == null) {
            mFragment = new ShowMoveFragment(mMove);
        }
        setFragment(mFragment, SHOW_MOVE_FRAGMENT);
    }



    private void setFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .commit();
    }

    @Override
    public void onSelectMove(Move mMove) {
        startShowMoveFragment(mMove);
    }
    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        ShowMoveFragment mFragment =
                (ShowMoveFragment) getFragmentManager().findFragmentByTag(SHOW_MOVE_FRAGMENT);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        String text = "";

        switch (id) {
            case R.id.dnepr:
                text = "1";
                break;
            case R.id.nikopol:
                text = "2";
                break;
            case R.id.kiev:
                text = "3";
                break;
        }
        Toast.makeText(StartActivity.this, text, Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }


}

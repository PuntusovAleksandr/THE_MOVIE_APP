package com.example.aleksandrp.themovieapp.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.StartActivity;
import com.example.aleksandrp.themovieapp.adapter.ListMoveAdapter;
import com.example.aleksandrp.themovieapp.entity.Move;
import com.example.aleksandrp.themovieapp.retrofit.MovieDbService;
import com.example.aleksandrp.themovieapp.settings.StaticClass;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoveListFragment extends Fragment {

    private Context mContext;
    private StartActivity mStartActivity;
    private List<Move> mListMoves;
    private ListMoveAdapter mMoveAdapter;
    private RecyclerView mRecyclerView;

    @TargetApi(Build.VERSION_CODES.M)
    public MoveListFragment(StartActivity mStartActivity) {
        // Required empty public constructor
        this.mStartActivity = mStartActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_list, container, false);

        mContext = getActivity();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view_params);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mListMoves = getListIconUri();
        mMoveAdapter = new ListMoveAdapter(mListMoves, mContext);

        mRecyclerView.setAdapter(mMoveAdapter);
        return mView;
    }



    private List<Move> getListIconUri() {
        List<Move> mListPath = new ArrayList<>();

        if (StaticClass.getFilter().equals(mContext.getString(R.string.top))) {
            mListPath = loadListMoves(mContext.getString(R.string.top_filter));
        } else if (StaticClass.getFilter().equals(mContext.getString(R.string.favorite))) {
            mListPath = loadListMoves(mContext.getString(R.string.top_filter));
        } else mListPath = loadListMoves(mContext.getString(R.string.popular_filter));

        return mListPath;
    }


    private List<Move> loadListMoves(String mFilter) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDbService git = retrofit.create(MovieDbService.class);
        Call call = git.getListFilter(mFilter, mContext.getString(R.string.api_key));
        call.enqueue(new Callback<Move>() {


            @Override
            public void onResponse(Call<Move> call, Response<Move> response) {

                mStartActivity.mProgressBar.setVisibility(View.GONE);
                final Move model = response.body();

                if (model == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        Log.e("error", responseBody.toString());
                    }
                } else {
                    //200

                }
            }

            @Override
            public void onFailure(Call<Move> call, Throwable t) {

                mStartActivity.mProgressBar.setVisibility(View.GONE);

                if (t instanceof UnknownHostException) {
                    Snackbar.make(mStartActivity.mProgressBar,
                            R.string.check_internet, Snackbar.LENGTH_LONG).show();
                }
            }
        });

//
//        for (int i = 0; i < 1555 ; i++) {
//            if (i % 2 == 0) {
//                mListPath.add(new Move("Title " + i, "Description " + i, "http://i.imgur.com/DvpvklR.png", i));
//            } else {
//                mListPath.add(new Move("Title " + i, "Description " + i, "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", i));
//            }
//        }

        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.popular_menu:
                text = mContext.getString(R.string.popular_filter);
                break;
            case R.id.rop_menu:
                text = mContext.getString(R.string.top_filter);
                break;
            case R.id.favorit_menu:
                text = mContext.getString(R.string.popular_filter);
                break;
        }

        StaticClass.setFilter(text);     // set param filter moves

        mListMoves = getListIconUri();
        mMoveAdapter.notifyDataSetChanged();
        Toast.makeText(mStartActivity, text, Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

}

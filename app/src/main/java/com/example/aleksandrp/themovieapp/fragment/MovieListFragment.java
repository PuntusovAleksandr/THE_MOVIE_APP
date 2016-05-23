package com.example.aleksandrp.themovieapp.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.StartActivity;
import com.example.aleksandrp.themovieapp.adapter.ListMovieAdapter;
import com.example.aleksandrp.themovieapp.db.RealmDb;
import com.example.aleksandrp.themovieapp.entity.ItemMovie;
import com.example.aleksandrp.themovieapp.entity.Movie;
import com.example.aleksandrp.themovieapp.settings.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    private StartActivity mStartActivity;
    private List<ItemMovie> mListMovies;
    private ListMovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private GridLayoutManager linearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Movie mMovie;


    int currentPage = 1;
    private boolean mIsLoading = false;

    @TargetApi(Build.VERSION_CODES.M)
    public MovieListFragment(StartActivity mStartActivity) {
        // Required empty public constructor
        this.mStartActivity = mStartActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_list, container, false);

        mContext = getActivity();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view_params);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            linearLayoutManager = new GridLayoutManager(mContext, 2);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayoutManager = new GridLayoutManager(mContext, 3);
        }
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mListMovies = new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setOnScrollListener(mRecyclerViewOnScrollListener);

        getListIconUri();

        return mView;
    }


    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!mIsLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            getListIconUri();
                        }
                    }
                }
            };


    private void getListIconUri() {

        if (StaticClass.getFilter().equals(mContext.getString(R.string.favorite_filter))) {
            loadListMoviesFromDb();
        } else if (StaticClass.getFilter().equals(mContext.getString(R.string.top_filter))) {
            loadListMovies(mContext.getString(R.string.top_filter));
        } else loadListMovies(mContext.getString(R.string.popular_filter));

    }

    private void loadListMoviesFromDb() {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovie.setItemMovies(RealmDb.getInstance(mContext).getMoviesFavorite());
                setMovie(mMovie);
            }
        });
        mStartActivity.mProgressBar.setVisibility(View.GONE);
        mIsLoading = true;
    }


    private void loadListMovies(String mFilter) {

        LoadParams mLoadParams = new LoadParams(mContext, mFilter);
        mLoadParams.execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.group_menu, true);
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
                text = mContext.getString(R.string.favorite_filter);
                break;
        }

        StaticClass.setFilter(text);     // set param filter movies

        currentPage = 1;

        getListIconUri();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (StaticClass.getFilter().equals(mContext.getString(R.string.favorite_filter)) && mMovie == null) {
            mMovie = new Movie();
            mMovie.setPage("1");
        }
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
        if (mListMovies != null) {
            mListMovies.clear();
            mListMovies = null;
        }
        mListMovies = mMovie.getItemMovies();
        if (mMovieAdapter != null) {
//            mMovieAdapter.notifyDataSetChanged();
            mMovieAdapter = null;
        }
//        else
        mMovieAdapter = new ListMovieAdapter(mListMovies, mContext);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void onRefresh() {
        mListMovies.clear();
        currentPage = 1;
        getListIconUri();
    }


    public class LoadParams extends AsyncTask<Object, Object, Object> {

        private String mFilter, mRequest, TAG = "TAG";
        private Context mContext;
        private Movie mMovie = new Movie();
        private List<ItemMovie> mMovies = new ArrayList<>();

        public LoadParams(Context mContext, String mFilter) {
            this.mContext = mContext;
            this.mFilter = mFilter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mStartActivity.mProgressBar.setVisibility(View.VISIBLE);

            mIsLoading = false;
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(Object[] params) {
            try {
                mRequest = doRequest();
                if (!mRequest.isEmpty()) {
                    try {
                        setParamsToUi(mRequest);
                    } catch (JSONException mE) {
                        mE.printStackTrace();
                        System.out.println("Error reading JSON " + mE.getMessage());
                    }
                    Log.i(TAG, "FINISH" + mRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mSwipeRefreshLayout.setRefreshing(false);
            mStartActivity.mProgressBar.setVisibility(View.GONE);
            Log.i(TAG, "START" + mRequest);
            setMovie(mMovie);
            currentPage++;
        }

        /**
         * parse JSON in movie
         *
         * @param mRequest
         * @throws JSONException
         */
        private void setParamsToUi(String mRequest) throws JSONException {


            JSONObject jObj = new JSONObject(mRequest);
            String page = jObj.getString("page");
            JSONObject mObjectMovie = null;

            JSONArray jObjJSONArray = jObj.getJSONArray("results");
            for (int i = 0; i < jObjJSONArray.length(); i++) {
                mObjectMovie = jObjJSONArray.getJSONObject(i);
                String mId = mObjectMovie.getString("id");
                String mOriginal_title = mObjectMovie.getString("original_title");
                String mOverview = mObjectMovie.getString("overview");
                String mRelease_date = mObjectMovie.getString("release_date");
                String mPoster_path = mObjectMovie.getString("poster_path");
                String vote_average = mObjectMovie.getString("vote_average") + "/10";
                ItemMovie mItemMovie = new ItemMovie(
                        mId,
                        mOriginal_title,
                        mOverview,
                        mRelease_date,
                        mPoster_path,
                        vote_average
                );
                mMovies.add(mItemMovie);
            }
            mMovie.setPage(page);
            mMovie.setItemMovies(mMovies);

        }

        /**
         * @return responce from server
         */
        String doRequest() throws UnsupportedEncodingException {

            String data = URLEncoder.encode("api_key", "UTF-8")
                    + "=" + URLEncoder.encode((mContext.getString(R.string.api_key)), "UTF-8");

            data = data + "&" + URLEncoder.encode("page", "UTF-8")
                    + "=" + URLEncoder.encode(currentPage + "", "UTF-8");

            String text = "";
            BufferedReader reader = null;

            // Send data
            try {
                // Defined URL  where to send data
                URL url = new URL(mContext.getString(R.string.base_url) + mFilter + data);
                // Send GGT data request
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.setRequestProperty("Content-length", "0");
                connect.setUseCaches(false);
                connect.setAllowUserInteraction(false);
                connect.setConnectTimeout(3000);
                connect.setReadTimeout(3000);
                connect.connect();

                // Get the server response
                int status = connect.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        // Read Server Response
                        reader = new BufferedReader(new InputStreamReader(
                                connect.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        text = sb.toString();
                        reader.close();
                }
            } catch (MalformedURLException ex) {

            } catch (IOException ex) {

            }

            return text;
        }
    }
}

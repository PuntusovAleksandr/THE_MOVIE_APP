package com.example.aleksandrp.themovieapp.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.StartActivity;
import com.example.aleksandrp.themovieapp.entity.ItemMovie;
import com.example.aleksandrp.themovieapp.entity.Movie;
import com.squareup.picasso.Picasso;

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
public class ShowMovieFragment extends Fragment {

    private ItemMovie mItemMovie;
    private Context mContext;
    private StartActivity mStartActivity;

    private TextView mDuration;


    public ShowMovieFragment() {
    }

    public ShowMovieFragment(StartActivity mStartActivity, ItemMovie mItemMovie) {
        // Required empty public constructor
        this.mStartActivity = mStartActivity;
        this.mItemMovie = mItemMovie;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_show_movie, container, false);
        mContext = getActivity();
        if (mItemMovie.getRuntime() == null ||
                mItemMovie.getRuntime().equals("")) {
            LoadDataMove mLoadParams = new LoadDataMove(mContext, mItemMovie.getId());
            mLoadParams.execute();
        };

        initUi(mView);
        return mView;
    }

    private void initUi(View mView) {
        ImageView mIconMovie = (ImageView) mView.findViewById(R.id.iv_movie_details);
        TextView mTitle = (TextView) mView.findViewById(R.id.tv_title),
                mYear = (TextView) mView.findViewById(R.id.tv_year),
                mRating = (TextView) mView.findViewById(R.id.tv_raiting),
                mDescription = (TextView) mView.findViewById(R.id.tv_description);
        mDuration = (TextView) mView.findViewById(R.id.tv_duration);


        Button btFavorite = (Button) mView.findViewById(R.id.bt_favorite);
        btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 22.05.2016 will add to database
            }
        });

        ListView mListPlaers = (ListView) mView.findViewById(R.id.lv_players);


        String mPathIcon = mContext.getString(R.string.url_icon) +
                mItemMovie.getBackdrop_path();
        Picasso.with(mContext)
                .load(mPathIcon)
                .placeholder(R.drawable.doownload)
                .error(R.mipmap.ic_launcher)
                .into(mIconMovie);

        mTitle.setText(mItemMovie.getOriginal_title());

        mYear.setText(mItemMovie.getRelease_date());
        mRating.setText(mItemMovie.getPopularity());
        mDescription.setText((mItemMovie.getOverview()));

        if (mItemMovie.getRuntime() != null) {
            mDuration.setText(mItemMovie.getRuntime());
        }

    }


    private void setMovie(String mText) {
        mDuration.setText(mText);
        mItemMovie.setRuntime(mText);
    }


    public class LoadDataMove extends AsyncTask<Object, Object, Object> {

        private String mId, mRequest, TAG = "TAG";
        private Context mContext;
        private Movie mMovie = new Movie();
        private List<ItemMovie> mMovies = new ArrayList<>();

        public LoadDataMove(Context mContext, String mId) {
            this.mContext = mContext;
            this.mId = mId+"?";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mStartActivity.mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Object[] params) {
            try {
                mRequest = doRequest();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mStartActivity.mProgressBar.setVisibility(View.GONE);
            Log.i(TAG, "START" + mRequest);
            String text = "";
            if (!mRequest.isEmpty()) {
                try {
                    text = setParamsToUi(mRequest);
                } catch (JSONException mE) {
                    mE.printStackTrace();
                    System.out.println("Error reading JSON " + mE.getMessage());
                }
                Log.i(TAG, "FINISH" + mRequest);
            }
            setMovie(text);
        }

        /**
         * parse JSON in movie
         *
         * @param mRequest
         * @throws JSONException
         */
        private String setParamsToUi(String mRequest) throws JSONException {


            JSONObject jObj = new JSONObject(mRequest);
            String runtime = jObj.getString("runtime") + mContext.getString(R.string.minute);
            mItemMovie.setRuntime(runtime);
            return runtime;
        }

        /**
         * @return responce from server
         */
        String doRequest() throws UnsupportedEncodingException {

            String data = URLEncoder.encode("api_key", "UTF-8")
                    + "=" + URLEncoder.encode((mContext.getString(R.string.api_key)), "UTF-8");

            String text = "";
            BufferedReader reader = null;


            // Send data
            try {
                // Defined URL  where to send data
                URL url = new URL(mContext.getString(R.string.base_url) + mId + data);
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

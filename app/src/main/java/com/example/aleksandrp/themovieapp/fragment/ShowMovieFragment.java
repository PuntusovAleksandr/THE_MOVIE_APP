package com.example.aleksandrp.themovieapp.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMovieFragment extends Fragment {

    private ItemMovie mMovie;
    private Context mContext;
    private StartActivity mStartActivity;

    public ShowMovieFragment() {
    }

    public ShowMovieFragment(StartActivity mStartActivity, ItemMovie mMovie) {
        // Required empty public constructor
        this.mStartActivity = mStartActivity;
        this.mMovie = mMovie;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_show_movie, container, false);
        mContext = getActivity();
        initUi(mView);

        return mView;
    }

    private void initUi(View mView) {
        ImageView mIconMovie = (ImageView) mView.findViewById(R.id.iv_movie_details);
        TextView mTitle = (TextView) mView.findViewById(R.id.tv_title),
                mYear = (TextView) mView.findViewById(R.id.tv_year),
                mDuration = (TextView) mView.findViewById(R.id.tv_duration),// TODO: absent in Api
                mRating = (TextView) mView.findViewById(R.id.tv_raiting),
                mDescription = (TextView) mView.findViewById(R.id.tv_description);

        Button btFavorite = (Button) mView.findViewById(R.id.bt_favorite);

        ListView mListPlaers = (ListView) mView.findViewById(R.id.lv_players);


        String mPathIcon = mContext.getString(R.string.url_icon) +
                mMovie.getBackdrop_path();
        Picasso.with(mContext)
                .load(mPathIcon)
                .error(R.mipmap.ic_launcher)
                .into(mIconMovie);

        mTitle.setText(mMovie.getOriginal_title());

        mYear.setText(mMovie.getRelease_date());
//        mDuration.setText(mMovie.getOverview()); // TODO: absent in Api
        mRating.setText(mMovie.getPopularity());
        mDescription.setText((mMovie.getOverview()));

    }

}

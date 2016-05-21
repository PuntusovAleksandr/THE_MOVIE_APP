package com.example.aleksandrp.themovieapp.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.adapter.ListMoveAdapter;
import com.example.aleksandrp.themovieapp.entity.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoveListFragment extends Fragment {

    private Context mContext;

    public MoveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_list, container, false);

        mContext = getActivity();

        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view_params);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        ListMoveAdapter  mMoveAdapter = new ListMoveAdapter(getListIconUri(), mContext);

        mRecyclerView.setAdapter(mMoveAdapter);
        return mView;
    }

    private List<Move> getListIconUri() {
        List<Move> mListPath = new ArrayList<>();

        for (int i = 0; i < 1555 ; i++) {
            if (i % 2 == 0) {
                mListPath.add(new Move("Title " + i, "Description " + i, "http://i.imgur.com/DvpvklR.png", i));
            } else {
                mListPath.add(new Move("Title " + i, "Description " + i, "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", i));
            }
        }

        return mListPath;
    }

}

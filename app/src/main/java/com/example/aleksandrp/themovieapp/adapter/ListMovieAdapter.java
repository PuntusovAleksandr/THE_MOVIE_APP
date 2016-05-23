package com.example.aleksandrp.themovieapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.entity.ItemMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AleksandrP on 18.05.2016.
 */
public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ItemViewHolder> {

    private List<ItemMovie> mListPath;
    private Context mContext;

    private ClickSelectMovie mClickListener;

    public ListMovieAdapter(List<ItemMovie> mListPath, Context mContext) {
        this.mListPath = mListPath;
        this.mContext = mContext;

        if (mContext instanceof ClickSelectMovie) {
            mClickListener = (ClickSelectMovie) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement ClickSelectMovie");
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cv);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie);

        }

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final ItemMovie mMovie = mListPath.get(position);

//         set image in imageView
        String mPathIcon = mContext.getString(R.string.url_icon) +
                mMovie.getBackdrop_path();
        Picasso.with(mContext)
                .load(mPathIcon)
                .fit()
                .placeholder(R.drawable.doownload)
                .into(holder.mImageView);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onSelectMovie(mMovie);
            }
        });
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_movie, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if (mListPath == null) return 0;
        return mListPath.size();
    }


    public interface ClickSelectMovie {
        void onSelectMovie(ItemMovie path);
    }
}

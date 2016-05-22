package com.example.aleksandrp.themovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.params.StaticParams;

import java.util.List;

/**
 * Created by AleksandrP on 22.05.2016.
 */

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> implements StaticParams {

    List<String> videoList;
    Context mContext;

    public BoxAdapter(Context context, List<String> videoList) {
        this.videoList = videoList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String path = videoList.get(position);

        holder.textTitle.setText(mContext.getString(R.string.player) + (position + 1));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (path.contains(StaticParams.ORIGIN)) {
                    String mReplace = path.replace(StaticParams.ORIGIN, "");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mReplace));
                    mContext.startActivity(browserIntent);
                } else {
                    // TODO: 22.05.2016 open in Api Youtube
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cv_card);
            textTitle = (TextView) itemView.findViewById(R.id.tv_item_player);
        }
    }
}

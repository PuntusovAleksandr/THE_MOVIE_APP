package com.example.aleksandrp.themovieapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aleksandrp.themovieapp.R;
import com.example.aleksandrp.themovieapp.entity.Move;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AleksandrP on 18.05.2016.
 */
public class ListMoveAdapter extends RecyclerView.Adapter<ListMoveAdapter.ItemViewHolder> {

    private List<Move> mListPath;
    private Context mContext;

    private ClickSelectMove mClickListener;

    public ListMoveAdapter(List<Move> mListPath, Context mContext) {
        this.mListPath = mListPath;
        this.mContext = mContext;

        if (mContext instanceof ClickSelectMove) {
            mClickListener = (ClickSelectMove) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement ClickSelectMove");
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cv);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_move);

        }

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Move mMove = mListPath.get(position);

//        Handler mHandler = new Handler();
//        mHandler.post(new Runnable() {
//                          @Override
//                          public void run() {
//                              Picasso.with(mContext)
//                                      .load(path)
//                                      .placeholder(R.mipmap.ic_launcher)
//                                      .error(R.mipmap.ic_launcher)
//                                      .into(holder.mImageView);
//                          }
//                      }
//        );
//         set image in imageView
        Picasso.with(mContext)
                .load(mMove.getPathUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mImageView);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onSelectMove(mMove);
            }
        });
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_move, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if (mListPath == null) return 0;
        return mListPath.size();
    }


    public interface ClickSelectMove {
        void onSelectMove(Move path);
    }
}

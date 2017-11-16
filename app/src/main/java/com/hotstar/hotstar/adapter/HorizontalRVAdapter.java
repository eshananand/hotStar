package com.hotstar.hotstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotstar.hotstar.R;
import com.hotstar.hotstar.models.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eshan on 11/16/17.
 */

public class HorizontalRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = HorizontalRVAdapter.class.getSimpleName();
    private List<Movie> mDataList;
    private int mRowIndex = -1;
    private Activity context;
    private int lastPosition = -1;
    private static final long FADE_DURATION = 300;

    public HorizontalRVAdapter(List<Movie> data, Activity context) {
        mDataList = data;
        this.context = context;

    }

    public void setData(List<Movie> data ) {
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        public TextView name;
        @BindView(R.id.tv_year)
        public TextView year;
        @BindView(R.id.tv_rating)
        public ImageView rating;
        @BindView(R.id.iv_poster_preview)
        public ImageView poster;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) rawHolder;
        holder.name.setText(mDataList.get(position).getName());
        holder.year.setText(mDataList.get(position).getYearOfRelease());
        Log.d(TAG,"image value is " + mDataList.get(position).getImage() );
        Glide.with(context).load(mDataList.get(position).getImage()).into(holder.poster);
        setScaleAnimation(holder.itemView, position);
    }

    private void setScaleAnimation(View view, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}
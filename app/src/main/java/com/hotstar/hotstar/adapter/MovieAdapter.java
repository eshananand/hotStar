package com.hotstar.hotstar.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotstar.hotstar.R;
import com.hotstar.hotstar.models.Movie;
import com.hotstar.hotstar.util.RecyclerClickListner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eshan on 11/16/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>
{

    private List<Movie> moviesList;
    private Activity context;
    private RecyclerClickListner mClickListner;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private HorizontalRVAdapter horizontalAdapter;
        @BindView(R.id.category_item_name_tv)
        TextView categoryName;
        @BindView(R.id.horizontal_list)
        RecyclerView horizontalList;


        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
            horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new HorizontalRVAdapter(moviesList, context);
            horizontalList.setAdapter(horizontalAdapter);
        }


    }

    public MovieAdapter(Activity context, List<Movie> moviesList, RecyclerClickListner listener) {
        this.moviesList = moviesList;
        this.context = context;
        mClickListner = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.categoryName.setText(moviesList.get(position).getName());
    }




    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

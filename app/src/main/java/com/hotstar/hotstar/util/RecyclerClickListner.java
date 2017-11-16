package com.hotstar.hotstar.util;

import android.view.View;

import com.hotstar.hotstar.models.Movie;

/**
 * Created by pallav on 14/11/17.
 */

public interface RecyclerClickListner
{
    public void recyclerViewListClicked(View v, int position, Movie movie);
}

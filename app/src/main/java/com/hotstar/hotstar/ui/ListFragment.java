package com.hotstar.hotstar.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotstar.hotstar.app.App;
import com.hotstar.hotstar.adapter.MovieAdapter;
import com.hotstar.hotstar.R;
import com.hotstar.hotstar.models.DaoSession;
import com.hotstar.hotstar.models.MovieDao;
import com.hotstar.hotstar.util.RecyclerClickListner;
import com.hotstar.hotstar.models.Movie;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements RecyclerClickListner
{

    private final int limit = 5;
    private int page_num = 1;
    private boolean loading;

    @BindView(R.id.recyclerview)
    RecyclerView rvMovieList;
    private static final String TAG = ListFragment.class.getSimpleName();
    private MovieDao movieDao;
    int visibleItemCount = 0, previousTotalItemCount = 0,totalItemCount = 0,firstVisibleItemPosition = 0;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private List<Movie> filteredMovieList = new ArrayList<>();

    public ListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_list, container, false);
  //      setupRecyclerView(rv);

        return rv;
    }


    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (totalItemCount < previousTotalItemCount)
            {
                visibleItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getChildCount();
                totalItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getItemCount();
                previousTotalItemCount = totalItemCount;
                firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            }
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                page_num++;
            }

 //           if (!isLoading && !isLastPage) {
                if (!loading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    loadMoreItems();
    //            }
            }
        }
    };

    private void loadMoreItems()
    {

        QueryBuilder qb = movieDao.queryBuilder();
        qb.limit(limit).offset(limit * page_num).orderDesc(MovieDao.Properties.Id);
        qb.build().forCurrentThread();
        List<Movie> tempMovieList = qb.list();
        movieList.addAll(tempMovieList);
        movieAdapter.notifyDataSetChanged();
        loading = true;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        movieDao = daoSession.getMovieDao();
        QueryBuilder qb = movieDao.queryBuilder();
        qb.limit(limit).offset(limit * page_num).orderDesc(MovieDao.Properties.Id);
        qb.build().forCurrentThread();
        loading = false;
        List<Movie> tempMovieList = qb.list();
        movieList.addAll(tempMovieList);

        movieAdapter = new MovieAdapter(getActivity(),movieList,ListFragment.this);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setNestedScrollingEnabled(true);
        rvMovieList.setAdapter(movieAdapter);
        rvMovieList.addOnScrollListener(recyclerViewOnScrollListener);

    }


    @Override
    public void recyclerViewListClicked(View v, int position, Movie movie)
    {

    }




}

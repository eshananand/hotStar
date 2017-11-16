package com.hotstar.hotstar.app;

import android.app.Application;

import com.hotstar.hotstar.models.DaoMaster;
import com.hotstar.hotstar.models.DaoSession;
import com.hotstar.hotstar.models.Movie;
import com.hotstar.hotstar.models.MovieDao;
import com.hotstar.hotstar.models.MovieList;
import com.hotstar.hotstar.util.AppUtils;
import com.hotstar.hotstar.util.DbOpenHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eshan on 11/16/17.
 */

public class App extends Application
{
    private DaoSession mDaoSession;
    MovieDao movieDao;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(new DbOpenHelper(this, "movie_rating.db").getWritableDb()).newSession();
        movieDao = getDaoSession().getMovieDao();
        if (movieDao.queryBuilder().count() < 10) {
            loadJSONFromAsset();
        }
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    private void loadJSONFromAsset() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getApplicationContext().getAssets().open("movie_list.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String json = new String(buffer, "UTF-8");
                    MovieList movies = AppUtils.parseJson(json, MovieList.class);
                    for (Movie movie : movies.getMovies()) {
                        movieDao.insert(movie);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}

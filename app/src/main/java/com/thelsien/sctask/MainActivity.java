package com.thelsien.sctask;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieSearchAsyncTask.MovieSearchListener {

    private RecyclerView mMoviesListView;
    private ArrayList<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesListView = findViewById(R.id.rv_movies_list);
        mMoviesListView.setLayoutManager(new GridLayoutManager(this, 2));

        if (savedInstanceState == null) {
            new MovieSearchAsyncTask(this).execute("al");
        } else {
            mMovies = savedInstanceState.getParcelableArrayList("movies_list");
            mMoviesListView.setAdapter(new MoviesListAdapter(mMovies));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies_list", mMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSearchSuccess(List<Movie> movies) {
        mMovies = (ArrayList<Movie>) movies;
        mMoviesListView.setAdapter(new MoviesListAdapter(mMovies));
    }

    @Override
    public void onSearchFailed() {
        Toast.makeText(this, R.string.error_search, Toast.LENGTH_LONG).show();
    }
}

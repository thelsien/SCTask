package com.thelsien.sctask;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieSearchAsyncTask.MovieSearchListener, SearchView.OnQueryTextListener {

    private RecyclerView mMoviesListView;
    private ArrayList<Movie> mMovies;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMoviesListView();

        if (savedInstanceState == null) {
            new MovieSearchAsyncTask(this).execute("al");
        } else {
            mMovies = savedInstanceState.getParcelableArrayList("movies_list");
            mMoviesListView.setAdapter(new MoviesListAdapter(mMovies));
        }
    }

    private void setupMoviesListView() {
        mMoviesListView = findViewById(R.id.rv_movies_list);
        mMoviesListView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);

        return true;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        new MovieSearchAsyncTask(this).execute(query);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

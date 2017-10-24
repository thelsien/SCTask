package com.thelsien.sctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieSearchAsyncTask.MovieSearchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MovieSearchAsyncTask(this).execute("al");
    }

    @Override
    public void onSearchSuccess(List<Movie> movies) {
        for (Movie movie : movies) {
            Log.d("MainActivity", movie.getId() + " " + movie.getPosterPath() + " " + movie.getBudget());
        }
    }

    @Override
    public void onSearchFailed() {

    }
}

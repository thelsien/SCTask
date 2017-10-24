package com.thelsien.sctask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.thelsien.sctask.Config.BASE_SEARCH_URL;

public class MovieSearchAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private MovieSearchListener mListener;
    private List<Movie> movies = new ArrayList<>();

    public MovieSearchAsyncTask(MovieSearchListener listener) {
        this.mListener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... queryParams) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_SEARCH_URL + "&query=" + queryParams[0])
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject resultObject = new JSONObject(response.body().string());

            if (resultObject.has("errors")) {
                return null;
            }

            parseSearchedMovies(resultObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return movies;
    }

    private void parseSearchedMovies(JSONObject resultObject) {
        JSONArray searchedMovies = resultObject.optJSONArray("results");

        JSONObject movieObject;
        for (int i = 0; i < searchedMovies.length(); i++) {
            movieObject = searchedMovies.optJSONObject(i);
            movies.add(new Movie(
                    movieObject.optInt("id"),
                    movieObject.optString("poster_path", ""),
                    movieObject.optInt("budget", 0)
            ));
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies == null) {
            mListener.onSearchFailed();
            return;
        }

        mListener.onSearchSuccess(movies);
    }

    public interface MovieSearchListener {
        void onSearchSuccess(List<Movie> movies);

        void onSearchFailed();
    }
}

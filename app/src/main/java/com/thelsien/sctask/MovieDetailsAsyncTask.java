package com.thelsien.sctask;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.thelsien.sctask.Config.BASE_DETAIL_URL;
import static com.thelsien.sctask.Config.BASE_SEARCH_URL;

/**
 * Created by frodo on 2017-10-24.
 */

public class MovieDetailsAsyncTask extends AsyncTask<Void, Void, Void> {
    private MovieDetailListener mListener;
    private int mMovieId;
    private int mMoviePosition;

    private int mBudget;

    public MovieDetailsAsyncTask(int movieId, int position, MovieDetailListener listener) {
        this.mListener = listener;
        this.mMovieId = movieId;
        this.mMoviePosition = position;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format(BASE_DETAIL_URL, mMovieId))
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject resultObject = new JSONObject(response.body().string());

            if (resultObject.has("success") && !resultObject.optBoolean("success", true)) {
                return null;
            }

            mBudget = resultObject.optInt("budget", 0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (mBudget > 0) {
            mListener.onDetailsSuccess(mMoviePosition, mBudget);
        }
    }

    public interface MovieDetailListener {
        void onDetailsSuccess(int position, int budget);
    }
}

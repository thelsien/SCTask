package com.thelsien.sctask;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder> implements MovieDetailsAsyncTask.MovieDetailListener {
    private List<Movie> mMovies;

    public MoviesListAdapter(List<Movie> movies) {
        super();

        mMovies = movies;
    }

    @Override
    public MoviesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gridItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie_card, parent, false);

        return new MoviesListViewHolder(gridItem);
    }

    @Override
    public void onBindViewHolder(final MoviesListViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        if (movie.getBudget() == 0) {
            getMovieDetails(movie.getId(), position);
        }

        holder.budgetView.setText(String.valueOf(movie.getBudget()));
        Glide.with(holder.posterView.getContext())
                .load(Config.BASE_IMG_URL + movie.getPosterPath())
                .into(holder.posterView);
    }

    private void getMovieDetails(int movieId, int position) {
        new MovieDetailsAsyncTask(movieId, position, this).execute();
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    @Override
    public void onDetailsSuccess(int position, int budget) {
        mMovies.get(position).setBudget(budget);
        notifyItemChanged(position);
    }

    class MoviesListViewHolder extends RecyclerView.ViewHolder {

        public TextView budgetView;
        public ImageView posterView;

        public MoviesListViewHolder(View itemView) {
            super(itemView);

            budgetView = itemView.findViewById(R.id.tv_budget);
            posterView = itemView.findViewById(R.id.iv_poster);
        }
    }
}

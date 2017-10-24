package com.thelsien.sctask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder> {
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

        holder.budgetView.setText(String.valueOf(movie.getBudget()));
        Glide.with(holder.posterView.getContext())
                .load(Config.BASE_IMG_URL + movie.getPosterPath())
                .into(holder.posterView);
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
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

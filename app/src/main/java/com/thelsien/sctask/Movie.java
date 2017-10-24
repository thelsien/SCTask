package com.thelsien.sctask;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private int _id;
    private String poster_path;
    private int budget;

    public Movie(int id, String poster_path, int budget) {
        this._id = id;
        this.poster_path = poster_path;
        this.budget = budget;
    }

    protected Movie(Parcel in) {
        _id = in.readInt();
        poster_path = in.readString();
        budget = in.readInt();
    }

    public int getId() {
        return _id;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
        parcel.writeString(poster_path);
        parcel.writeInt(budget);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

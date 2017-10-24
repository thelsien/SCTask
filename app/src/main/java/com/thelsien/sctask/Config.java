package com.thelsien.sctask;

public class Config {
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public static String BASE_SEARCH_URL = Config.BASE_URL + "search/movie?api_key=" + BuildConfig.MOVIE_DB_API_KEY;
    public static String BASE_DETAIL_URL = Config.BASE_URL + "movie/%d?api_key=" + BuildConfig.MOVIE_DB_API_KEY;
    public static String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";
}

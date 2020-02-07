package com.demo.moviesdemobyankit.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("/3/movie/top_rated")
    Call<Movies> getMoviesByTopRating(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("/3/search/movie")
    Call<Movies> getSearchedMovie(@Query("api_key") String apiKey, @Query("query") String search);

    @GET("/3/discover/movie?with_people=287")
    Call<Movies> getBestOfBrad(@Query("api_key") String apiKey);

}

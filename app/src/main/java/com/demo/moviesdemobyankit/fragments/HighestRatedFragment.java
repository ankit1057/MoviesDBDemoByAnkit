package com.demo.moviesdemobyankit.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.moviesdemobyankit.R;
import com.demo.moviesdemobyankit.adapters.MoviesAdapter;
import com.demo.moviesdemobyankit.utils.Constants;
import com.demo.moviesdemobyankit.utils.Movies;
import com.demo.moviesdemobyankit.utils.MoviesService;
import com.demo.moviesdemobyankit.utils.Result;
import com.demo.moviesdemobyankit.utils.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HighestRatedFragment extends Fragment {
    private RecyclerView moviesRecycler;
    private MoviesAdapter moviesAdapter;
    List<Result> moviesList;

    public HighestRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highest_rated, container, false);
        moviesRecycler = view.findViewById(R.id.moviesRecycler);
        moviesList = new ArrayList<>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        moviesRecycler.setLayoutManager(gridLayoutManager);
        moviesAdapter = new MoviesAdapter(getContext(), moviesList);
        moviesRecycler.setAdapter(moviesAdapter);

        getHighestRatedMovies(1);


        return view;
    }

    private void getHighestRatedMovies(int currPage) {
        MoviesService moviesService = RetrofitInstance.getService();
        Call<Movies> call;
        call = moviesService.getMoviesByTopRating(Constants.API_KEY, currPage);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (response.isSuccessful()) {
                    moviesList.addAll(response.body().getResults());
                    moviesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Something went wrong " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(getContext(), "Exception "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

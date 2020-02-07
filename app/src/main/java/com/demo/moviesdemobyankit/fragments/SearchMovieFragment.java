package com.demo.moviesdemobyankit.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment {
    private RecyclerView moviesRecycler;
    private MoviesAdapter moviesAdapter;
    List<Result> moviesList;
    EditText searchText;
    ProgressDialog progressdialog;

    public SearchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        moviesRecycler = view.findViewById(R.id.moviesRecycler);
        searchText = view.findViewById(R.id.etSearchText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(s).length() > 3) {
                    progressdialog = new ProgressDialog(getContext());
                    progressdialog.setMessage("Please Wait....");
                    // progressdialog.show();
                    getSearchedMovie(String.valueOf(s));
                }

            }
        });

        moviesList = new ArrayList<>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        moviesRecycler.setLayoutManager(gridLayoutManager);
        moviesAdapter = new MoviesAdapter(getContext(), moviesList);
        moviesRecycler.setAdapter(moviesAdapter);

        getSearchedMovie(String.valueOf(searchText.getText()));


        return view;
    }

    private void getSearchedMovie(String text) {
        MoviesService moviesService = RetrofitInstance.getService();
        Call<Movies> call;
        call = moviesService.getSearchedMovie(Constants.API_KEY, text);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (progressdialog != null) {
                    //     progressdialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (moviesList.size() > 0) {
                        moviesList.clear();
                    }
                    moviesList.addAll(response.body().getResults());
                    moviesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Something went wrong " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(getContext(), "Exception " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

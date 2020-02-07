package com.demo.moviesdemobyankit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.moviesdemobyankit.R;
import com.demo.moviesdemobyankit.utils.Movies;
import com.demo.moviesdemobyankit.utils.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesVH> {

    Context context;
    List<Result> moviesList;

    public MoviesAdapter(Context context, List<Result> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MoviesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new MoviesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesVH holder, final int position) {
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+moviesList.get(position).getPosterPath()).into(holder.moviesCover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Selected:"+moviesList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MoviesVH extends RecyclerView.ViewHolder {
        private ImageView moviesCover;
        MoviesVH(@NonNull View itemView) {
            super(itemView);
            moviesCover=itemView.findViewById(R.id.movieImage);
        }
    }
}

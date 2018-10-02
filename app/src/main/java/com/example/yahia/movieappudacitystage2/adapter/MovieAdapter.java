package com.example.yahia.movieappudacitystage2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;
import com.example.yahia.movieappudacitystage2.R;
import com.example.yahia.movieappudacitystage2.utils.UtilsHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;
    private MovieClickListener movieClickListener;

    public MovieAdapter(List<Movie> movieList, Context context, MovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.context = context;
        this.movieClickListener = movieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Picasso.with(context).load(UtilsHelper.IMAGE_PATH+"/w185"+movieList.get(position).getPoster_path()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_item_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieClickListener.onMovieClickListener(movieList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface MovieClickListener {
         void onMovieClickListener(Movie movie);
    }
}

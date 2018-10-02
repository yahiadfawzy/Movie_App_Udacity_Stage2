package com.example.yahia.movieappudacitystage2.fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yahia.movieappudacitystage2.R;
import com.example.yahia.movieappudacitystage2.adapter.TraillerAdapter;
import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;
import com.example.yahia.movieappudacitystage2.data.trailler_data.Trailler;
import com.example.yahia.movieappudacitystage2.data.trailler_data.TraillerData;
import com.example.yahia.movieappudacitystage2.data_base.MovieRoomDataBase;
import com.example.yahia.movieappudacitystage2.utils.UtilsHelper;
import com.example.yahia.movieappudacitystage2.web_services.MovieApi;
import com.example.yahia.movieappudacitystage2.web_services.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment implements TraillerAdapter.TrailerClickListener{
    //TODO CREAT DETAILSFRAGMENT VIEW
    Movie movie;
    ArrayList<String> traillers_keys;
    RecyclerView recyclerView;
    TraillerAdapter adapter;
    TextView lang,title,date,overView,hint;
    RatingBar ratingBar;
    ImageView imageView,back;
    LinearLayout linearLayout;
    MovieRoomDataBase movieDatabase;
    FloatingActionButton floatingActionButtonon;
    private ImageView imageViewfav;
    private boolean fav;
    public void setResult(Movie movie) {
        this.movie = movie;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment,container,false);

        IntilizeViews(rootView);

        if(savedInstanceState!=null){
            movie=savedInstanceState.getParcelable("movie");
            traillers_keys=savedInstanceState.getStringArrayList("keys");
            fav=savedInstanceState.getBoolean("fav");
        }



        if(movie!=null) {
            Picasso.with(getContext()).load(UtilsHelper.IMAGE_PATH+"/w185" + movie.getPoster_path()).into(imageView);
            Picasso.with(getContext()).load(UtilsHelper.IMAGE_PATH+"/w500" + movie.getBackdrop_path()).into(back);
            lang.setText(movie.getOriginal_language());
            date.setText(movie.getRelease_date());
            overView.setText(movie.getOverview());
            title.setText(movie.getTitle());
            ratingBar.setRating(movie.getVote_average().floatValue()/2);
            if(savedInstanceState==null)  {
                checkFavourites();
            }
            if(fav==true){
                imageViewfav.setVisibility(View.VISIBLE);
                floatingActionButtonon.setImageResource(android.R.drawable.ic_menu_delete);
            }else{
                imageViewfav.setVisibility(View.GONE);
                floatingActionButtonon.setImageResource(android.R.drawable.ic_menu_add);
            }
            if(UtilsHelper.itemWidth==160){
                back.setVisibility(View.GONE);
            }else {
                back.setVisibility(View.VISIBLE);
            }
        }else{
            hint.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }

        if(UtilsHelper.hasNetworkConnection(getContext())&&movie!=null){

            if(traillers_keys==null){
               LoadDataFromApi();
            }else {
                updateList();
            }
        }



        floatingActionButtonon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movie!=null){
                    if(fav){
                        movieDatabase.movieDao().deleteMovie(movie);
                        floatingActionButtonon.setImageResource(android.R.drawable.ic_menu_add);
                        imageViewfav.setVisibility(View.GONE);
                        fav=false;
                    }else {
                        movieDatabase.movieDao().insertMovie(movie);
                        floatingActionButtonon.setImageResource(android.R.drawable.ic_menu_delete);
                        imageViewfav.setVisibility(View.VISIBLE);
                        fav=true;
                    }
                }
            }
        });

        return rootView;
    }

    private void checkFavourites() {
       movieDatabase = MovieRoomDataBase.getMovieDatabaseInstance(getContext());
       if(movieDatabase.movieDao().getMovie(movie.getId())==null){
            //TODO is not favorite
            fav=false;
        }else{
            //TODO is favorite
            fav=true;
        }
    }

    private void LoadDataFromApi(){
        Toast.makeText(getActivity(),"load",Toast.LENGTH_SHORT).show();
        MovieApi movieApi = RetrofitInstance.getRetrofitInstance().create(MovieApi.class);
        Call<TraillerData> call = movieApi.getTraillerData(movie.getId()+"");
        call.enqueue(new Callback<TraillerData>() {
            @Override
            public void onResponse(Call<TraillerData> call, Response<TraillerData> response) {
                TraillerData traillerData = response.body();
                List<Trailler> res = traillerData.getResults();
                traillers_keys = new ArrayList<>();
                for(int i=0;i<res.size();i++){
                    traillers_keys.add(res.get(i).getKey());
                }
                updateList();
            }

            @Override
            public void onFailure(Call<TraillerData> call, Throwable t) {
                Log.e("xXXXXXXXXXXXXx",t.getLocalizedMessage());
            }
        });

    }

    void updateList(){
        adapter = new TraillerAdapter(traillers_keys.size(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movie",movie);
        outState.putStringArrayList("keys",traillers_keys);
    }

    @Override
    public void OnTraillerClickLisner(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + traillers_keys.get(position)));
        startActivity(intent);
    }

    private void IntilizeViews(View rootView){
        floatingActionButtonon =rootView.findViewById(R.id.float_button);
        imageViewfav=rootView.findViewById(R.id.favourite);
        imageViewfav.setVisibility(View.GONE);
        lang=rootView.findViewById(R.id.lang);
        title=rootView.findViewById(R.id.title);
        date=rootView.findViewById(R.id.date);
        overView=rootView.findViewById(R.id.over_view);
        hint=rootView.findViewById(R.id.text_hint);
        ratingBar=rootView.findViewById(R.id.rate);
        recyclerView=rootView.findViewById(R.id.trailer_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageView=rootView.findViewById(R.id.poster_image);
        back=rootView.findViewById(R.id.backImage_view);
        linearLayout=rootView.findViewById(R.id.liner_content);
    }
}

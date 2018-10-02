package com.example.yahia.movieappudacitystage2.fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yahia.movieappudacitystage2.R;
import com.example.yahia.movieappudacitystage2.activites.MainActivity;
import com.example.yahia.movieappudacitystage2.adapter.MovieAdapter;
import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;
import com.example.yahia.movieappudacitystage2.data.movie_data.MovieData;
import com.example.yahia.movieappudacitystage2.data_base.MovieRoomDataBase;
import com.example.yahia.movieappudacitystage2.utils.EndlessRecyclerViewScrollListener;
import com.example.yahia.movieappudacitystage2.utils.UtilsHelper;
import com.example.yahia.movieappudacitystage2.web_services.MovieApi;
import com.example.yahia.movieappudacitystage2.web_services.RetrofitInstance;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    //TODO CREAT MOVIEFRAGENT VIEW
    private static final String TAG = "DetailsFragment";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView refrechTextView;
    private GridLayoutManager gridLayoutManager;
    private FrameLayout noInternetConnectionLayout;
    private MovieAdapter.MovieClickListener movieClickListener;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private String sortCertiteria = UtilsHelper.TOP_RATED;
    private int pageNum = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO get refrnce to recycleViewClicLisner
        movieClickListener=(MovieAdapter.MovieClickListener)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.movie_fragment,container,false);
        movieList = new ArrayList<>();
        IntilizeView(rootView);
        //TODO restore data
        if(savedInstanceState!=null){
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList("movie_list");
            movieList.addAll(movies);
            pageNum        = savedInstanceState.getInt("page_numer");
            sortCertiteria = savedInstanceState.getString("sort_ceriteria");
            movieAdapter.notifyDataSetChanged();
        }else if(UtilsHelper.hasNetworkConnection(getContext())){
            //TODO load data from api if have connection
            LoadDataFromApi(1);
            progressBar.setVisibility(View.VISIBLE);
        }
        //todo if there is no connectio
        if(!UtilsHelper.hasNetworkConnection(getContext())){
              noInternetConnectionLayout.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private void IntilizeView(View rootView) {
        //TODO intilize views
        setHasOptionsMenu(true);
        MainActivity.toolbar.setTitle(sortCertiteria);
        progressBar=rootView.findViewById(R.id.progress_bar);
        recyclerView=rootView.findViewById(R.id.movie_fragment_recycle_view);
        gridLayoutManager=new GridLayoutManager(getContext(),
                                              UtilsHelper.calculateNoOfColumns(getContext(),UtilsHelper.itemWidth),
                                              LinearLayoutManager.VERTICAL,
                                              false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        noInternetConnectionLayout=rootView.findViewById(R.id.connection_layout);
        movieAdapter = new MovieAdapter(movieList,getContext(),movieClickListener);
        recyclerView.setAdapter(movieAdapter);
        refrechTextView = rootView.findViewById(R.id.refrech_button);
        refrechTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UtilsHelper.hasNetworkConnection(getContext())){
                    if(movieList.size()!=0){
                        pageNum++;
                    }
                    //TODO we have connection
                    if(sortCertiteria!=UtilsHelper.FAVOURITE){
                        //todo load data from api
                        LoadDataFromApi(pageNum);
                        progressBar.setVisibility(View.VISIBLE);
                        noInternetConnectionLayout.setVisibility(View.GONE);
                    } else {
                        MovieRoomDataBase movieDatabase = MovieRoomDataBase.getMovieDatabaseInstance(getContext());
                        movieList.addAll(movieDatabase.movieDao().getMovies());
                        movieAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                } else{
                    //TODO still no connection
                    noInternetConnectionLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    new CountDownTimer(400, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            noInternetConnectionLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }.start();
                }
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(sortCertiteria!=UtilsHelper.FAVOURITE) {
                    if(UtilsHelper.hasNetworkConnection(getContext())){
                        pageNum++;
                        LoadDataFromApi(pageNum);
                        progressBar.setVisibility(View.VISIBLE);
                    }else {
                        //TODO no connection on refrech load data use page number;
                        noInternetConnectionLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void LoadData(String sort){
        if(UtilsHelper.hasNetworkConnection(getContext())){
            //todo there is aconnetion when sort ceriteria changed
            progressBar.setVisibility(View.VISIBLE);
            noInternetConnectionLayout.setVisibility(View.GONE);
            if(!sortCertiteria.equals(sort)||movieList.size()==0){
                sortCertiteria = sort;
                movieList.clear();
                movieAdapter.notifyDataSetChanged();
                switch (sort){
                    case UtilsHelper.POPULAR:
                        LoadDataFromApi(1);
                        break;
                    case UtilsHelper.TOP_RATED:
                        LoadDataFromApi(1);
                        break;
                    case UtilsHelper.FAVOURITE:
                        MovieRoomDataBase movieDatabase = MovieRoomDataBase.getMovieDatabaseInstance(getContext());
                        this.movieList.addAll(movieDatabase.movieDao().getMovies());
                        movieAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }

        } else if(!sortCertiteria.equals(sort)){
          //TODO no connection when sort ceriteria changed
            sortCertiteria=sort;
            movieList.clear();
            movieAdapter.notifyDataSetChanged();
            pageNum=1;
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void LoadDataFromApi(int pageNum){
        MovieApi movieApi = RetrofitInstance.getRetrofitInstance().create(MovieApi.class);
        Call<MovieData> call = movieApi.getMovieData(sortCertiteria,pageNum);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                movieList.addAll(response.body().getResults());
                movieAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG,t.getLocalizedMessage()+"hhh");
                noInternetConnectionLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_ceriteria_menu,menu);
        switch (sortCertiteria){
            case UtilsHelper.FAVOURITE:
                menu.findItem(R.id.action_sort_favorites).setChecked(true);
                break;
            case UtilsHelper.TOP_RATED:
                menu.findItem(R.id.action_sort_rating).setChecked(true);
                break;
            case UtilsHelper.POPULAR:
                menu.findItem(R.id.action_sort_popularity).setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_favorites:
                item.setChecked(true);
                MainActivity.toolbar.setTitle(UtilsHelper.FAVOURITE);
                this.LoadData(UtilsHelper.FAVOURITE);
                break;
            case R.id.action_sort_popularity:
                item.setChecked(true);
                MainActivity.toolbar.setTitle(UtilsHelper.POPULAR);
                this.LoadData(UtilsHelper.POPULAR);
                break;
            case R.id.action_sort_rating:
                item.setChecked(true);
                MainActivity.toolbar.setTitle(UtilsHelper.TOP_RATED);
                this.LoadData(UtilsHelper.TOP_RATED);
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie_list", (ArrayList<? extends Parcelable>) movieList);
        outState.putInt("page_numer",pageNum);
        outState.putString("sort_ceriteria",sortCertiteria);
    }
}

package com.example.yahia.movieappudacitystage2.activites;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.example.yahia.movieappudacitystage2.R;
import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;
import com.example.yahia.movieappudacitystage2.fragment.DetailsFragment;
import com.example.yahia.movieappudacitystage2.utils.UtilsHelper;
import com.squareup.picasso.Picasso;


public class DetailsActivity extends AppCompatActivity {

    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        movie = getIntent().getParcelableExtra("movie");
        ImageView imageView = findViewById(R.id.toolImage);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movie.getTitle());
            Picasso.with(this).load(UtilsHelper.IMAGE_PATH+ "/w500" + movie.getBackdrop_path()).into(imageView);
        }
        if(savedInstanceState==null){
            //TODO add details fragment
            DetailsFragment detailsFragment=new DetailsFragment();
            detailsFragment.setResult(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.movie_detail_container, detailsFragment)
                    .commit();
        }
    }
}

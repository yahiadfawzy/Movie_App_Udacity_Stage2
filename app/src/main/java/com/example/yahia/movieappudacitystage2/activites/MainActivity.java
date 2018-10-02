package com.example.yahia.movieappudacitystage2.activites;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.yahia.movieappudacitystage2.R;
import com.example.yahia.movieappudacitystage2.adapter.MovieAdapter;
import com.example.yahia.movieappudacitystage2.data.movie_data.Movie;
import com.example.yahia.movieappudacitystage2.fragment.DetailsFragment;
import com.example.yahia.movieappudacitystage2.fragment.MovieFragment;
import com.example.yahia.movieappudacitystage2.utils.UtilsHelper;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener{

    private boolean mToPayne;
    public static Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
            if(findViewById(R.id.main_content_600)!=null) {
              //TODO small width600
              mToPayne=true;
              UtilsHelper.itemWidth = 320;
              //TODO ADD TWO FRAGMENT
                if(savedInstanceState==null) {
                MovieFragment movieFragment = new MovieFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_600,movieFragment)
                        .commit();
                DetailsFragment deatailsFragment=new DetailsFragment();
                deatailsFragment.setResult(null);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_600_details,deatailsFragment)
                        .commit();
            }
            }else {
               //TODO ordinary layout
               mToPayne=false;
               UtilsHelper.itemWidth = 160;
               //TODO ADD ONE FRAGMENT
                if(savedInstanceState==null){
                MovieFragment movieFragment = new MovieFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_container,movieFragment)
                        .commit();
                }
            }
        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClickListener(Movie movie) {
        if(mToPayne==true){
            DetailsFragment deatailsFragment=new DetailsFragment();
            deatailsFragment.setResult(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_600_details,deatailsFragment)
                    .commit();
        }
        else{
            Intent intent = new Intent(this,DetailsActivity.class);
            intent.putExtra("movie",movie);
            startActivity(intent);
        }
    }
}

package com.example.yahia.movieappudacitystage2.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

public  class UtilsHelper {

    public static final String ApiKey = "api_key=832f13a97b5d2df50ecf0dbc8a0f46ae";
    public static final String IMAGE_PATH = "http://image.tmdb.org/t/p";

    public static final String FAVOURITE = "favourite";
    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR   = "popular";

    public static int itemWidth;
    public static int calculateNoOfColumns(Context context,int width) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float noOfColumnsFloat = dpWidth / width;
        int x = (int) noOfColumnsFloat;
        if(noOfColumnsFloat-x >= 0.5){
            return x+1;
        }
        return x;
    }

    public static boolean hasNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public int dpToPx(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}

package com.example.yahia.movieappudacitystage2.data.movie_data;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieData implements Parcelable {

    @SerializedName("results")
    private List<Movie> movieList = null;

     public final static Creator<MovieData> CREATOR = new Creator<MovieData>() {
      @SuppressWarnings({"unchecked"})
      public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }
      public MovieData[] newArray(int size) {
            return (new MovieData[size]);
        }
      };

    protected MovieData(Parcel in) {
        in.readList(this.movieList, (Movie.class.getClassLoader()));
    }

    public MovieData(List<Movie> results) {
        super();
        this.movieList = results;
    }


    public List<Movie> getResults() {
        return movieList;
    }

    public void setResults(List<Movie> results) {
        this.movieList = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(movieList);
    }

    public int describeContents() {
        return 0;
    }
}
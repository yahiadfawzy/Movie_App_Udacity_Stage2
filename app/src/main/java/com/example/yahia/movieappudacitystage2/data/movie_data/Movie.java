package com.example.yahia.movieappudacitystage2.data.movie_data;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @PrimaryKey
    private int id;
    private Double vote_average;
    private String poster_path;
    private String original_language;
    private String title;
    private String backdrop_path;
    private String overview;
    private String release_date;

    public final static Creator<Movie> CREATOR = new Creator<Movie>() {
        @SuppressWarnings({"unchecked"})
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }
    };

    @Ignore
    protected Movie(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.vote_average = ((Double) in.readValue((double.class.getClassLoader())));
        this.poster_path = ((String) in.readValue((String.class.getClassLoader())));
        this.original_language = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.backdrop_path = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.release_date = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Movie(int id, Double vote_average, String poster_path, String original_language, String title, String backdrop_path, String overview, String release_date) {
        super();
        this.id = id;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }


    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(vote_average);
        dest.writeValue(poster_path);
        dest.writeValue(original_language);
        dest.writeValue(title);
        dest.writeValue(backdrop_path);
        dest.writeValue(overview);
        dest.writeValue(release_date);
    }

    public int describeContents() {
        return 0;
    }
}
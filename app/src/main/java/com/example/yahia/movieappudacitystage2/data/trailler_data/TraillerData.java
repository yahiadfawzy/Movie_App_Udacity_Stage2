package com.example.yahia.movieappudacitystage2.data.trailler_data;
import java.util.List;
/**
  Created by YAHIA on 8/1/2018.
  **/
public class TraillerData {
    private List<Trailler> results = null;

    public TraillerData(List<Trailler> results) {
        super();
        this.results = results;
    }

    public List<Trailler> getResults() {
        return results;
    }

    public void setResults(List<Trailler> results) {
        this.results = results;
    }

}

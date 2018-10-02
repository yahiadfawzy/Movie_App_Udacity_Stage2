package com.example.yahia.movieappudacitystage2.data.trailler_data;
/**
 * Created by YAHIA on 8/1/2018.
 */
public class Trailler {
    private String id;
    private String key;
    private String name;

    public Trailler(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
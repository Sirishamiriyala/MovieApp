package com.example.sirisha.moviecopy;

/**
 * Created by sirisha on 17-05-2018.
 */

public class MoviePOJO {
    String key,type;
    public MoviePOJO(String key, String type) {
        this.key=key;
        this.type=type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

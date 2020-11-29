package com.gtechnologies.videogplus.Model;

/**
 * Created by Hp on 3/28/2018.
 */

public class Tag {

    int id;
    String title;

    public Tag() {
    }

    public Tag(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

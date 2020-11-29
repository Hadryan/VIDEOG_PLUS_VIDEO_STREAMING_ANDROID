package com.gtechnologies.videogplus.Model;

/**
 * Created by Hp on 3/28/2018.
 */

public class Category {

    int id;
    String title;
    String titleBn;
    String status;

    public Category() {
    }

    public Category(int id, String title, String titleBn, String status) {
        this.id = id;
        this.title = title;
        this.titleBn = titleBn;
        this.status = status;
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

    public String getTitleBn() {
        return titleBn;
    }

    public void setTitleBn(String titleBn) {
        this.titleBn = titleBn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

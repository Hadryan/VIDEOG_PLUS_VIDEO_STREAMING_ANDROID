package com.gtechnologies.videogplus.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hp on 4/1/2018.
 */

public class Video implements Serializable {

    List<Content> contentList;

    public Video() {
    }

    public Video(List<Content> contentList) {
        this.contentList = contentList;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }
}

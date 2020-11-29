package com.gtechnologies.videogplus.Model;

import java.util.List;

/**
 * Created by Hp on 3/28/2018.
 */

public class ContentResponse {

    int code;
    List<Content> data;
    String message;

    public ContentResponse() {
    }

    public ContentResponse(int code, List<Content> data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Content> getContents() {
        return data;
    }

    public void setContents(List<Content> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.gtechnologies.videogplus.Model;

public class Subscription {

    int id;
    int track_id;
    String price;
    String expiry_date;
    String comment;
    String msisdn;
    String token;

    public Subscription() {
    }

    public Subscription(int id, int track_id, String price, String expiry_date, String comment, String msisdn, String token) {
        this.id = id;
        this.track_id = track_id;
        this.price = price;
        this.expiry_date = expiry_date;
        this.comment = comment;
        this.msisdn = msisdn;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

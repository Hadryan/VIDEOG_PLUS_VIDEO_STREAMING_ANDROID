package com.gtechnologies.videogplus.Model;

import java.io.Serializable;

public class Bkash implements Serializable {

    String msisdn;
    int code;
    String status;
    String expireTime;
    String url;

    public Bkash() {
    }

    public Bkash(String msisdn, int code, String status, String expireTime, String url) {
        this.msisdn = msisdn;
        this.code = code;
        this.status = status;
        this.expireTime = expireTime;
        this.url = url;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

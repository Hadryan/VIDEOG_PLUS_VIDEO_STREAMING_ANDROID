package com.gtechnologies.videogplus.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hp on 3/28/2018.
 */

public class Content implements Serializable {

    int id;
    Category category;
    String brief;
    String path720;
    String briefBn;
    List<Tag> tags;
    String dim;
    String banner;
    String title;
    String idHide;
    String titleBn;
    String highlight;
    int duration;
    String createdAt;
    String path480;
    String sticky;
    String path360;
    String path240;
    String path144;
    String path1080;
    String status;
    String image;
    String thumbnail;
    String bannerImage;
    String premium;

    public Content() {
    }

    public Content(int id, Category category, String brief, String path720, String briefBn, List<Tag> tags, String dim, String banner, String title, String idHide, String titleBn, String highlight, int duration, String createdAt, String path480, String sticky, String path360, String path240, String path144, String path1080, String status, String image, String thumbnail, String bannerImage, String premium) {
        this.id = id;
        this.category = category;
        this.brief = brief;
        this.path720 = path720;
        this.briefBn = briefBn;
        this.tags = tags;
        this.dim = dim;
        this.banner = banner;
        this.title = title;
        this.idHide = idHide;
        this.titleBn = titleBn;
        this.highlight = highlight;
        this.duration = duration;
        this.createdAt = createdAt;
        this.path480 = path480;
        this.sticky = sticky;
        this.path360 = path360;
        this.path240 = path240;
        this.path144 = path144;
        this.path1080 = path1080;
        this.status = status;
        this.image = image;
        this.thumbnail = thumbnail;
        this.bannerImage = bannerImage;
        this.premium = premium;
    }

    public Content(int id, Category category, String brief, String path720, String briefBn, List<Tag> tags, String dim, String banner, String title, String idHide, String titleBn, String highlight, String createdAt, String path480, String sticky, String path360, String path240, String path144, String path1080, String status, String image, String thumbnail, String bannerImage) {
        this.id = id;
        this.category = category;
        this.brief = brief;
        this.path720 = path720;
        this.briefBn = briefBn;
        this.tags = tags;
        this.dim = dim;
        this.banner = banner;
        this.title = title;
        this.idHide = idHide;
        this.titleBn = titleBn;
        this.highlight = highlight;
        this.createdAt = createdAt;
        this.path480 = path480;
        this.sticky = sticky;
        this.path360 = path360;
        this.path240 = path240;
        this.path144 = path144;
        this.path1080 = path1080;
        this.status = status;
        this.image = image;
        this.thumbnail = thumbnail;
        this.bannerImage = bannerImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPath720() {
        return path720;
    }

    public void setPath720(String path720) {
        this.path720 = path720;
    }

    public String getBriefBn() {
        return briefBn;
    }

    public void setBriefBn(String briefBn) {
        this.briefBn = briefBn;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdHide() {
        return idHide;
    }

    public void setIdHide(String idHide) {
        this.idHide = idHide;
    }

    public String getTitleBn() {
        return titleBn;
    }

    public void setTitleBn(String titleBn) {
        this.titleBn = titleBn;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPath480() {
        return path480;
    }

    public void setPath480(String path480) {
        this.path480 = path480;
    }

    public String getSticky() {
        return sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public String getPath360() {
        return path360;
    }

    public void setPath360(String path360) {
        this.path360 = path360;
    }

    public String getPath240() {
        return path240;
    }

    public void setPath240(String path240) {
        this.path240 = path240;
    }

    public String getPath144() {
        return path144;
    }

    public void setPath144(String path144) {
        this.path144 = path144;
    }

    public String getPath1080() {
        return path1080;
    }

    public void setPath1080(String path1080) {
        this.path1080 = path1080;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }
}

package com.solannae.findthelyrics;

import com.google.gson.annotations.SerializedName;

public class DeezerArtistModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;
    @SerializedName("picture")
    private String picture;
    @SerializedName("picture_small")
    private String picture_small;
    @SerializedName("picture_medium")
    private String picture_medium;
    @SerializedName("picture_big")
    private String picture_big;
    @SerializedName("picture_xl")
    private String picture_xl;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getPicture() {
        return picture;
    }

    public String getPicture_small() {
        return picture_small;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public String getPicture_xl() {
        return picture_xl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public void setPicture_xl(String picture_xl) {
        this.picture_xl = picture_xl;
    }
}

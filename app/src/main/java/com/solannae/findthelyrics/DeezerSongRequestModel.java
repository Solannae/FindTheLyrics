package com.solannae.findthelyrics;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DeezerSongRequestModel {
    @SerializedName("data")
    private ArrayList<DeezerSongModel> data;
    @SerializedName("total")
    private int total;
    @SerializedName("next")
    private String next;

    public ArrayList<DeezerSongModel> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public String getNext() {
        return next;
    }

    public void setData(ArrayList<DeezerSongModel> data) {
        this.data = data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

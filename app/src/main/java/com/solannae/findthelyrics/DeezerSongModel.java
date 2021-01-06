package com.solannae.findthelyrics;

import com.google.gson.annotations.SerializedName;

public class DeezerSongModel {
    @SerializedName("id")
    private int id;
    @SerializedName("readable")
    private boolean readable;
    @SerializedName("title")
    private String title;
    @SerializedName("title_short")
    private String title_short;
    @SerializedName("title_version")
    private String title_version;
    @SerializedName("link")
    private String link;
    @SerializedName("duration")
    private int duration;
    @SerializedName("rank")
    private int rank;
    @SerializedName("explicit_lyrics")
    private boolean explicit_lyrics;
    @SerializedName("preview")
    private String preview;
    @SerializedName("artist")
    private DeezerArtistModel artist;
    @SerializedName("album")
    private DeezerAlbumModel album;

    public int getId() {
        return id;
    }

    public boolean isReadable() {
        return readable;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_short() {
        return title_short;
    }

    public String getTitle_version() {
        return title_version;
    }

    public String getLink() {
        return link;
    }

    public int getDuration() {
        return duration;
    }

    public int getRank() {
        return rank;
    }

    public boolean isExplicit_lyrics() {
        return explicit_lyrics;
    }

    public String getPreview() {
        return preview;
    }

    public DeezerArtistModel getArtist() {
        return artist;
    }

    public DeezerAlbumModel getAlbum() {
        return album;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_short(String title_short) {
        this.title_short = title_short;
    }

    public void setTitle_version(String title_version) {
        this.title_version = title_version;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setExplicit_lyrics(boolean explicit_lyrics) {
        this.explicit_lyrics = explicit_lyrics;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public void setArtist(DeezerArtistModel artist) {
        this.artist = artist;
    }

    public void setAlbum(DeezerAlbumModel album) {
        this.album = album;
    }
}

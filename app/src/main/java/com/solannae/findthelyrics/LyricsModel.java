package com.solannae.findthelyrics;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LyricsModel {
    @SerializedName("error")
    private ArrayList<String> error;
    @SerializedName("results")
    private ResultItem results;

    public ArrayList<String> getError() {
        return error;
    }

    public void setError(ArrayList<String> error) {
        this.error = error;
    }

    public ResultItem getResults() {
        return results;
    }

    public void setResults(ResultItem results) {
        this.results = results;
    }


    class LyricsLine {
        @SerializedName("lrc_timestamp")
        private String lrc_timestamp;
        @SerializedName("milliseconds")
        private int milliseconds;
        @SerializedName("duration")
        private int duration;
        @SerializedName("line")
        private String line;

        public String getLrc_timestamp() {
            return lrc_timestamp;
        }

        public void setLrc_timestamp(String lrc_timestamp) {
            this.lrc_timestamp = lrc_timestamp;
        }

        public int getMilliseconds() {
            return milliseconds;
        }

        public void setMilliseconds(int milliseconds) {
            this.milliseconds = milliseconds;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }
    }

    class ResultItem {
        @SerializedName("LYRICS_ID")
        private int lyrics_id;
        @SerializedName("LYRICS_SYNC_JSON")
        private ArrayList<LyricsLine> lyrics_sync_json;
        @SerializedName("LYRICS_TEXT")
        private String lyrics_text;
        @SerializedName("LYRICS_COPYRIGHT")
        private String lyrics_copyright;
        @SerializedName("LYRICS_WRITERS")
        private String lyrics_writers;

        public int getLyrics_id() {
            return lyrics_id;
        }

        public void setLyrics_id(int lyrics_id) {
            this.lyrics_id = lyrics_id;
        }

        public ArrayList<LyricsLine> getLyrics_sync_json() {
            return lyrics_sync_json;
        }

        public void setLyrics_sync_json(ArrayList<LyricsLine> lyrics_sync_json) {
            this.lyrics_sync_json = lyrics_sync_json;
        }

        public String getLyrics_text() {
            return lyrics_text;
        }

        public void setLyrics_text(String lyrics_text) {
            this.lyrics_text = lyrics_text;
        }

        public String getLyrics_copyright() {
            return lyrics_copyright;
        }

        public void setLyrics_copyright(String lyrics_copyright) {
            this.lyrics_copyright = lyrics_copyright;
        }

        public String getLyrics_writers() {
            return lyrics_writers;
        }

        public void setLyrics_writers(String lyrics_writers) {
            this.lyrics_writers = lyrics_writers;
        }
    }
}

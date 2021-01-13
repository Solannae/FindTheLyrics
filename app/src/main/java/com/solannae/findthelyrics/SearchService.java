package com.solannae.findthelyrics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("/search")
    Call<DeezerSongRequestModel> getSongList(@Query("q") String param);

    @GET("/ajax/gw-light.php")
    Call<LyricsModel> getLyrics(@Query("method") String method, @Query("api_version") String api_version, @Query("api_token") String api_token, @Query("sng_id") int id);
}

package com.solannae.findthelyrics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("/search")
    Call<DeezerSongRequestModel> getSongList(@Query("q") String param);
}

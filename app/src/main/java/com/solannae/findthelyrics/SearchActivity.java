package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.SongListClickListener {
    private RecyclerView searchView;
    private TextView searchTextInput;
    private Button searchButton;

    private DeezerSongRequestModel response;
    private DeezerSongModel[] dataset;
    private LyricsModel playedLyrics;
    private String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.songList);
        searchTextInput = findViewById(R.id.searchTextInput);
        searchButton = findViewById(R.id.searchButton);
        GetAuthToken();

        searchView.setHasFixedSize(true);
        searchView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onSearchClick(View view)
    {
        if (searchTextInput.getText().length() > 0) {
            GetSongList(searchTextInput.getText().toString());
        }
    }

    private void GetAuthToken()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.deezer.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        SearchService service = retrofit.create(SearchService.class);
        service.getAuthToken("deezer.getUserData", 3, "1.0", "", 1).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    try {
                        String respBody = response.body();
                        auth_token = (new JSONObject(respBody)).getJSONObject("results").getString("checkForm");
                    } catch (JSONException e) {
                        Log.e("Retrofit", "Failed to read JSON Cookies");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit", "Failed to get Auth_Token");
            }
        });
    }

    private void GetSongList(String param)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.deezer.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchService service = retrofit.create(SearchService.class);
        service.getSongList(param).enqueue(new Callback<DeezerSongRequestModel>() {
            @Override
            public void onResponse(Call<DeezerSongRequestModel> call, Response<DeezerSongRequestModel> res) {
                response = res.body();
                PopulateRecyclerView();
            }

            @Override
            public void onFailure(Call<DeezerSongRequestModel> call, Throwable t) {
                Log.e("Retrofit", "Failed to get songs from Deezer API");
            }
        });
    }

    public void GetLyrics(int songId)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.deezer.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchService service = retrofit.create(SearchService.class);
        service.getLyrics("song.getLyrics", "1.0", auth_token, songId).enqueue(new Callback<LyricsModel>() {
            @Override
            public void onResponse(Call<LyricsModel> call, Response<LyricsModel> response) {
                if (response.body().getResults() != null) {
                    playedLyrics = response.body();
                }
            }

            @Override
            public void onFailure(Call<LyricsModel> call, Throwable t) {
                Log.e("Retrofit", "Failed to get lyrics from Deezer API. Auth_Token: " + auth_token);
            }
        });
    }

    private void PopulateRecyclerView()
    {
        dataset = new DeezerSongModel[response.getData().size()];
        response.getData().toArray(dataset);
        SearchAdapter adapter = new SearchAdapter(dataset, this);
        searchView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(int position) {
        GetLyrics(dataset[position].getId());

        if (playedLyrics != null) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("lyrics", playedLyrics);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Couldn't find any lyrics for that song: " + dataset[position].getId(), Toast.LENGTH_SHORT).show();
        }
    }
}
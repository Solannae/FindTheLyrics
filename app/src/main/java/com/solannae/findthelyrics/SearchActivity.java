package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView searchView;
    private TextView searchTextInput;
    private Button searchButton;

    private DeezerSongRequestModel response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.songList);
        searchTextInput = findViewById(R.id.searchTextInput);
        searchButton = findViewById(R.id.searchButton);

        searchView.setHasFixedSize(true);
        searchView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onSearchClick(View view)
    {
        if (searchTextInput.getText().length() > 0) {
            GetSongList(searchTextInput.getText().toString());
        }
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

    private void PopulateRecyclerView()
    {
        DeezerSongModel[] dataset = new DeezerSongModel[response.getData().size()];
        response.getData().toArray(dataset);
        SearchAdapter adapter = new SearchAdapter(dataset);
        searchView.setAdapter(adapter);
    }
}
package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private StorageReader reader;
    private Button Playbutton;
    private Button LBbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Playbutton = (Button) findViewById(R.id.playbutton);
        Playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeezerLoginActivity();
            }
        });
        LBbutton = (Button) findViewById(R.id.leaderboardbutton);
        LBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeaderBoardActivity();
            }
        });

        reader = new StorageReader(this);
        database = reader.getWritableDatabase();
    }

    /*public void onClick(View view)
    {
        //Intent intent = new Intent(this, DeezerLoginActivity.class);
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }*/

    public void openDeezerLoginActivity(){
        Intent intent = new Intent(this, DeezerLoginActivity.class);
        startActivity(intent);
    }

    public void openLeaderBoardActivity(){
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        reader.close();
        super.onDestroy();
    }
}
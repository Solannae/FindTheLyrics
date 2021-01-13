package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchableInfo;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private StorageReader reader;
    private Button Playbutton;
    private Button LBbutton;
    private String auth_token;

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

    private void checkIfAnyValidAuthToken() {
        String[] projection = { StorageContract.StorageEntry.AUTH_COLUMN_KEY };
        String selection = StorageContract.StorageEntry.AUTH_COLUMN_EXPIRATION_TIME + " >= ?";
        String[] argument = { Calendar.getInstance().getTime().toString() };
        String sortOrder = StorageContract.StorageEntry.AUTH_COLUMN_EXPIRATION_TIME + " DESC";

        Cursor cursor = database.query(StorageContract.StorageEntry.AUTH_TABLE_NAME,
                projection,
                selection,
                argument,
                null,
                null,
                sortOrder);

        if (cursor.moveToNext()) {
            auth_token = cursor.getString(cursor.getColumnIndexOrThrow(StorageContract.StorageEntry.AUTH_COLUMN_KEY));
        }
        cursor.close();
    }

    public void openDeezerLoginActivity(){
        checkIfAnyValidAuthToken();

        if (auth_token.isEmpty()) {
            Intent intent = new Intent(this, DeezerLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("auth", auth_token);
            startActivity(intent);
        }
    }

    public void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
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
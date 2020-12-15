package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private StorageReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reader = new StorageReader(this);
    }

    public void onClick(View view)
    {
        Intent intent = new Intent(this, DeezerLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        reader.close();
        super.onDestroy();
    }
}
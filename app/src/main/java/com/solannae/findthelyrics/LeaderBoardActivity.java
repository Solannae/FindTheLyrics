package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LeaderBoardActivity extends AppCompatActivity {
    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        
        scoreView = findViewById(R.id.scoreTextView);

        scoreView.setText(getIntent().getStringExtra("message"));
    }

    public void onPlayAgain(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("query", getIntent().getStringExtra("query"));
        intent.putExtra("lyrics", getIntent().getStringExtra("lyrics"));
        startActivity(intent);
        finish();
    }

    public void onChangeTrack(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    public void onMainMenu(View view) {
        finish();
    }
}
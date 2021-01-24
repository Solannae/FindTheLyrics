package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class GameActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayer ytPlayer;
    private YouTubePlayerView youTubeView;
    private MyPlaybackEventListener playbackEventListener;
    private MyPlayerStateChangeListener playerStateChangeListener;

    private String query;
    private JSONArray lyrics;
    private TextView lyricsView;
    private TextView answerView;
    private Button submitButton;

    private JSONObject nextLine;
    private Handler handler;
    private Runnable refresh;

    private String answer;
    private long answerStart;
    private int toGuess;
    private int currentIndex;

    private String videoId = "0RU_05zpETo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        query = getIntent().getStringExtra("query");

        try {
            lyrics = new JSONArray(getIntent().getStringExtra("lyrics"));
            for (int i = 0; i < lyrics.length(); ++i)
            {
                JSONObject obj = lyrics.getJSONObject(i);
                if (!obj.has("duration"))
                {
                    lyrics.remove(i);
                    --i;
                }
            }
            toGuess = (new Random()).nextInt(lyrics.length() / 2 - 6) + 6;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lyricsView = findViewById(R.id.lyricsView);
        handler = new Handler(Looper.getMainLooper());
        refresh = () -> displayLyrics();

        answerView = findViewById(R.id.answerView);
        submitButton = findViewById(R.id.submitButton);

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();

        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(YTConfig.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        // youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        this.ytPlayer = youTubePlayer;

        if (!b) {
            getFirstVideoId(query);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(String.valueOf(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            youTubeView.initialize(YTConfig.YOUTUBE_API_KEY, this);
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void getFirstVideoId(String keyword){
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q="+keyword+"&type=video&key="+YTConfig.YOUTUBE_API_KEY;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Volley response", response.toString());
                            JSONArray items = response.getJSONArray("items");
                            JSONObject jo = items.getJSONObject(0);
                            JSONObject id = jo.getJSONObject("id");
                            String vId = id.getString("videoId");
                            System.out.println(vId);
                            videoId = vId;
                            ytPlayer.cueVideo(videoId);
                            Thread.sleep(5000);
                            ytPlayer.play();
                            Thread.sleep(300);
                            displayLyrics();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley response", "An error occurred.");
            }
        });
        queue.add(request);
    }

    private void displayLyrics() {
        if (currentIndex == toGuess)
        {
            ytPlayer.pause();
            handler.removeCallbacks(refresh);
            submitButton.setEnabled(true);
            answerStart = System.nanoTime();

            try {
                answer = nextLine.getString("line");
                int words = answer.split(" ").length;
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < words; ++i)
                {
                    line.append("___ ");
                }
                lyricsView.setText(line.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else if (nextLine != null)
        {
            try {
                lyricsView.setText(nextLine.getString("line"));
                handler.postDelayed(refresh, nextLine.getInt("duration"));
                lyrics.remove(0);
                nextLine = lyrics.getJSONObject(0);
                ++currentIndex;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                nextLine = lyrics.getJSONObject(0);
                handler.postDelayed(refresh, nextLine.getInt("milliseconds"));
                ++currentIndex;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Validate(View view)
    {
        long seconds = (int)((System.nanoTime() - answerStart) / 1000000000);

        int correctnessShare = 75;
        int speedShare = 25;
        int score = 0;

        String text1 = answer.toLowerCase();
        String text2 = answerView.getText().toString().toLowerCase();
        Log.d("Answer", "Provided: " + text2 + " || Expected: " + text1);

        String[] text1split = text1.split(" ");
        String[] text2split = text2.split(" ");

        for (int i = 0; i < text1split.length; ++i)
        {
            if (text1split[i].equals(text2split[i]))
            {
                score += (int)(correctnessShare / text1split.length);
            }
        }

        score += (seconds < speedShare) ? speedShare - seconds : 0;

        Intent intent = new Intent(this, LeaderBoardActivity.class);

        if (text1.equals(text2))
        {
            intent.putExtra("message", "Congratulations!\nYou guessed the correct answer.\nScore: " + score);
        }
        else
        {
            intent.putExtra("message", "Unlucky ! The answer was:\n" + text1 + "\nScore: " + score);
        }

        intent.putExtra("query", getIntent().getStringExtra("query"));
        intent.putExtra("lyrics", getIntent().getStringExtra("lyrics"));
        startActivity(intent);
        finish();
    }
}
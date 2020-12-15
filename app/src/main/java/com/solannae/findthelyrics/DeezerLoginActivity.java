package com.solannae.findthelyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;

public class DeezerLoginActivity extends AppCompatActivity {
    private WebView deezerWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_login);
        deezerWebview = findViewById(R.id.deezerWebview);

        DeezerWebViewClient client = new DeezerWebViewClient(this);
        deezerWebview.setWebViewClient(client);
        deezerWebview.getSettings().setJavaScriptEnabled(true);
        deezerWebview.loadUrl("https://connect.deezer.com/oauth/auth.php?app_id=" + client.getAppId() + "&redirect_uri=" + client.getRedirectUrl());
    }

    public void oauthLogin(final String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StorageReader dbReader = new StorageReader(getApplicationContext());
                SQLiteDatabase db = dbReader.getWritableDatabase();

                String token = response.split("&")[0].split("=")[1];
                String expiration = response.split("&")[1].split("=")[1];
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, Integer.parseInt(expiration));

                ContentValues values = new ContentValues();
                values.put(StorageContract.StorageEntry.AUTH_COLUMN_KEY, token);
                values.put(StorageContract.StorageEntry.AUTH_COLUMN_EXPIRATION_TIME, calendar.getTime().toString());
                db.insert(StorageContract.StorageEntry.AUTH_TABLE_NAME, null, values);

                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FINDTHELYRICS", "Error during OAuth second callback");
            }
        });

        queue.add(request);
    }
}
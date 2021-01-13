package com.solannae.findthelyrics;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DeezerWebViewClient extends WebViewClient {
    private final String redirectUrl = "http://localhost";
    private final int appId = 449382;
    private final String oauthUrl = "https://connect.deezer.com/oauth/access_token.php";
    private DeezerLoginActivity linkedActivity;

    public DeezerWebViewClient(DeezerLoginActivity activity)
    {
        linkedActivity = activity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon)
    {
        if (url.indexOf(redirectUrl) == 0)
        {
            view.stopLoading();
            Log.d("FINDTHELYRICS", url);
            linkedActivity.oauthLogin(url);
        }
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getAppId() {
        return appId;
    }
}

package com.solannae.findthelyrics;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DeezerWebViewClient extends WebViewClient {
    private final String redirectUrl = "http://localhost";
    private final String cookiesUrl = "https://www.deezer.com";
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
            view.loadUrl(cookiesUrl);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (url.indexOf(cookiesUrl) == 0)
        {
            String sid = "";
            String[] cookies = CookieManager.getInstance().getCookie(url).split(";");
            for (int i = 0; i < cookies.length; ++i)
            {
                if (cookies[i].indexOf("sid") == 0)
                {
                    sid = cookies[i];
                }
            }
            Log.d("SID", sid);
            linkedActivity.startWithSid(sid);
        }
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getAppId() {
        return appId;
    }
}

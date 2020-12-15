package com.solannae.findthelyrics;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class DeezerWebViewClient extends WebViewClient {
    private final String redirectUrl = "http://localhost";
    private final int appId = 449382;
    private final String appSecret = "7ef61a9828604defb38153fc3a0138b6";
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
            String code = Uri.parse(url).getQueryParameter("code");
            String finalUrl = oauthUrl + "?app_id=" + appId + "&secret=" + appSecret + "&code=" + code;
            linkedActivity.oauthLogin(finalUrl);
        }
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getAppId() {
        return appId;
    }
}

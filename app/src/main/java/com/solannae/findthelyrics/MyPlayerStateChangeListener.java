package com.solannae.findthelyrics;

import android.text.TextUtils;

import com.google.android.youtube.player.YouTubePlayer;

public class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

    YouTubePlayer player;

    /*public MyPlayerStateChangeListener(YouTubePlayer player){
        this.player = player;
    }*/


    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {
        if(player != null)
            player.play(); //auto play

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }



}

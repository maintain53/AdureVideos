package com.example.nwokoye.adurevideos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by NWOKOYE on 8/31/2017.
 */
public class playerfragment extends YouTubeBaseActivity {
    public static final String API_KEY =  "AIzaSyDUshymlQw-ae_qRCxJrOrk7ucAGZ9FTSc";


    String VIDEO_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        VIDEO_ID = extras.getString("id");
        setContentView(R.layout.playerfragment);

        FragmentManager fm = getFragmentManager();
        String tag = YouTubePlayerFragment.class.getSimpleName();
        YouTubePlayerFragment playerFragment = (YouTubePlayerFragment) fm.findFragmentByTag(tag);
        if (playerFragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            playerFragment = YouTubePlayerFragment.newInstance();
            ft.add(android.R.id.content, playerFragment, tag);
            ft.commit();
        }

        playerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setFullscreen(true);
                youTubePlayer.loadVideo(VIDEO_ID);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(playerfragment.this, "Error while initializing YouTubePlayer.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

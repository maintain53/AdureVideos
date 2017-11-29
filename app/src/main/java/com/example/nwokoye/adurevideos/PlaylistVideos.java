package com.example.nwokoye.adurevideos;

import com.google.api.services.youtube.model.Video;
import java.util.ArrayList;

/**
 * Created by NWOKOYE on 8/31/2017.
 */
public class PlaylistVideos extends ArrayList<Video> {
    public final String playlistId;
    private String mNextPageToken;

    public PlaylistVideos(String id) {
        playlistId = id;
    }

    public void setNextPageToken(String nextPageToken) {
        mNextPageToken = nextPageToken;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }

}

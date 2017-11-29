package com.example.nwokoye.adurevideos.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nwokoye.adurevideos.ApiKey;
import com.example.nwokoye.adurevideos.BuildConfig;
import com.example.nwokoye.adurevideos.CardlistAdapter;
import com.example.nwokoye.adurevideos.GetpalylistAyncstask;
import com.example.nwokoye.adurevideos.PlaylistVideos;
import com.example.nwokoye.adurevideos.R;
import com.example.nwokoye.adurevideos.cardcomedy;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NWOKOYE on 8/31/2017.
 */
public class comedy extends Fragment {
    private ArrayList<String> mPlaylistTitles;
    private RecyclerView mRecyclerView;
    private PlaylistVideos mPlaylistVideos;
    private RecyclerView.LayoutManager mLayoutManager;
    private cardcomedy mPlaylistCardAdapter;
    private YouTube mYouTubeDataApi;
    private String  playlistid = "PLWz5rJ2EKKc-riD21lnOjVYBqSkNII3_k";
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ApiKey.YOUTUBE_API_KEY.startsWith("YOUR_API_KEY")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setMessage("Edit ApiKey.java and replace \"YOUR_API_KEY\" with your Applications Browser API Key")
                    .setTitle("Missing API Key")
                    .setNeutralButton("Ok, I got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (savedInstanceState == null) {
            mYouTubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();
        }}

    public comedy() {
        // Required empty public constructor
    }

    public void setYouTubeDataApi(YouTube api) {
        mYouTubeDataApi = api;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // set the Picasso debug indicator only for debug builds
        Picasso.with(getActivity()).setIndicatorsEnabled(BuildConfig.DEBUG);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.youtube_recycler_view_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        Resources resources = getResources();
        if (resources.getBoolean(R.bool.isTablet)) {
            // use a staggered grid layout if we're on a large screen device
            mLayoutManager = new StaggeredGridLayoutManager(resources.getInteger(R.integer.columns), StaggeredGridLayoutManager.VERTICAL);
        } else {
            // use a linear layout on phone devices
            mLayoutManager = new LinearLayoutManager(getActivity());
        }

        mRecyclerView.setLayoutManager(mLayoutManager);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // if we have a playlist in our retained fragment, use it to populate the UI
        if (mPlaylistVideos != null) {
            // reload the UI with the existing playlist.  No need to fetch it again
            reloadUi(mPlaylistVideos, false);
        } else {
            // otherwise create an empty playlist using the first item in the playlist id's array
            mPlaylistVideos = new PlaylistVideos(playlistid);
            // and reload the UI with the selected playlist and kick off fetching the playlist content
            reloadUi(mPlaylistVideos, true);


        }}

    private void reloadUi(final PlaylistVideos playlistVideos, boolean fetchPlaylist) {
        // initialize the cards adapter
        initCardAdapter(playlistVideos);

        if (fetchPlaylist) {
            // start fetching the selected playlistVideos contents
            new GetpalylistAyncstask(mYouTubeDataApi) {
                @Override
                public void onPostExecute(Pair<String, List<Video>> result) {
                    handleGetPlaylistResult(playlistVideos, result);
                }
            }.execute(playlistVideos.playlistId, playlistVideos.getNextPageToken());
        }
    }

    private void initCardAdapter(final PlaylistVideos playlistVideos) {
        // create the adapter with our playlistVideos and a callback to handle when we reached the last item
        mPlaylistCardAdapter = new cardcomedy(playlistVideos, new LastItemReachedListener() {
            @Override
            public void onLastItem(int position, String nextPageToken) {
                new GetpalylistAyncstask(mYouTubeDataApi) {
                    @Override
                    public void onPostExecute(Pair<String, List<Video>> result) {
                        handleGetPlaylistResult(playlistVideos, result);
                    }
                }.execute(playlistVideos.playlistId, playlistVideos.getNextPageToken());
            }
        });
        mRecyclerView.setAdapter(mPlaylistCardAdapter);
    }

    private void handleGetPlaylistResult(PlaylistVideos playlistVideos, Pair<String, List<Video>> result) {
        if (result == null) return;
        final int positionStart = playlistVideos.size();
        playlistVideos.setNextPageToken(result.first);
        playlistVideos.addAll(result.second);
        mPlaylistCardAdapter.notifyItemRangeInserted(positionStart, result.second.size());
    }

    /**
     * Interface used by the {@link CardlistAdapter} to inform us that we reached the last item in the list.
     */
    public interface LastItemReachedListener {
        void onLastItem(int position, String nextPageToken);
    }
}

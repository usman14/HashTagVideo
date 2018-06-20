package com.example.usman.youtubehashtag.Activities;

import android.os.Bundle;
import android.widget.Toast;

import com.example.usman.youtubehashtag.Database_Objects.Realm_DataBase;
import com.example.usman.youtubehashtag.R;
import com.example.usman.youtubehashtag.Video_Objects.YoutubeConnector;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import io.realm.Realm;


public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;
    Realm realm;
    Realm_DataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        realm=Realm.getDefaultInstance();
        dataBase=new Realm_DataBase();
        playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        playerView.initialize(YoutubeConnector.KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if(!b) {

                    youTubePlayer.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
                    dataBase.Add_History(realm,getIntent().getStringExtra("Title"),getIntent().getStringExtra("VIDEO_ID"),
                    getIntent().getStringExtra("ThumbnailUrl"));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Initialization Failed", Toast.LENGTH_LONG).show();
    }
}

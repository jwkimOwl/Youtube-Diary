package com.techtown.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class ReadActivity extends YouTubeBaseActivity implements OnInitializedListener{
    private YouTubePlayerView playerView;
    TextView dateView,commentView;
    String Comment,Date;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent=getIntent();
        dateView=(TextView)findViewById(R.id.DateView);
        Date=intent.getStringExtra("DATE");
        dateView.setText(Date);
        commentView=(TextView)findViewById(R.id.CommentView);
        Comment=intent.getStringExtra("COMMENT");
        commentView.setText(Comment);
        playerView = (YouTubePlayerView)findViewById(R.id.player_view);
        playerView.initialize(YoutubeConnector.KEY, (OnInitializedListener) this);

    }
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
    }

    //method called if the YouTubePlayerView succeeds to load the video
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
                                        boolean restored) {

        //initialise the video player only if it is not restored or is not yet set
        if(!restored){

            //cueVideo takes video ID as argument and initialise the player with that video
            //this method just prepares the player to play the video
            //but does not download any of the video stream until play() is called
            player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }
}

package com.gtechnologies.videogplus.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;
import com.gtechnologies.videogplus.Adapter.SpinnerAdapter;
import com.gtechnologies.videogplus.Interface.PlayerListener;
import com.gtechnologies.videogplus.Library.ExoPlayerVideoHandler;
import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.Model.Content;
import com.gtechnologies.videogplus.Model.Video;
import com.gtechnologies.videogplus.R;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Hp on 4/2/2018.
 */

public class FullscreenVideoActivity extends AppCompatActivity implements PlayerListener {

    private boolean destroyVideo = true;
    List<Content> contents;
    int index;
    ImageButton nextBtn, fullScreen;
    Utility utility = new Utility(this);
    SimpleExoPlayer player;
    GifImageView loadingImage;
    //ComponentListener componentListener;
    PlayerView exoPlayerView;
    ImageView backBtn;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ImageView dropdown;
    String[] val;
    boolean isFirstTime = true;
    LinearLayout qualityLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utility.setFullScreen();
        setContentView(R.layout.layout_fullscreen);
        fullScreen = (ImageButton)findViewById(R.id.exo_fullscreen);
        loadingImage = (GifImageView)findViewById(R.id.loadingImage);
        exoPlayerView = (PlayerView)findViewById(R.id.video_player);
        nextBtn = (ImageButton)findViewById(R.id.exo_next_custom);
        backBtn = (ImageView)findViewById(R.id.backBtn);
        spinner = (Spinner) findViewById(R.id.quality_spinner);
        dropdown = (ImageView) findViewById(R.id.dropdown);
        qualityLayout = (LinearLayout) findViewById(R.id.quality_layout);
        if(Util.SDK_INT>getResources().getInteger(R.integer.sdk)){
            fullScreen.setVisibility(View.GONE);
        }
        else{
            fullScreen.setImageResource(R.drawable.ic_fullscreen_off);
        }
        //Video video = (Video) getIntent().getExtras().get("contents");
        /*index = getIntent().getExtras().getInt("index");
        ExoPlayerVideoHandler.getInstance().setIndex(index);*/
        //contents = video.getContentList();
        //componentListener = new ComponentListener();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playNextVideo();
                ExoPlayerVideoHandler.getInstance().playNextVideo();
            }
        });
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroyVideo = false;
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.SDK_INT<=getResources().getInteger(R.integer.sdk)) {
                    destroyVideo = false;
                }
                finish();
            }
        });
        spinnerAdapter = new SpinnerAdapter(this);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        /*dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!isFirstTime) {
                    loadingImage.setVisibility(View.VISIBLE);
                    long playerPosition = ExoPlayerVideoHandler.getInstance().getPlayerPosition();
                    ExoPlayerVideoHandler.getInstance().releasePlayer();
                    ExoPlayerVideoHandler.getInstance().setVideoResolution(val[position]);
                    ExoPlayerVideoHandler.getInstance().playVideo();
                    ExoPlayerVideoHandler.getInstance().setPlayerPosition(playerPosition+1);
                }
                else {
                    isFirstTime = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        qualityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initiatePlayer();
    }

    private void setSpinnerValue(){
        String resolution = ExoPlayerVideoHandler.getInstance().getVideoResolution();
        val = getResources().getStringArray(R.array.quality_array);
        int index = 0;
        for(int i=0; i<val.length; i++){
            if(val[i].equals(resolution)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
    }


    private void initiatePlayer() {
        //String url = getString(R.string.image_url)+contents.get(ExoPlayerVideoHandler.getInstance().getIndex()).getPath360();
        //ExoPlayerVideoHandler exoPlayerVideoHandler = ExoPlayerVideoHandler.getInstance();
        /*player = ExoPlayerVideoHandler.getInstance()
                .prepareExoPlayerForUri(this,
                        Uri.parse(url), (SimpleExoPlayerView) exoPlayerView);
        player.addListener(componentListener);
        ExoPlayerVideoHandler.getInstance().goToForeground();*/
        //ExoPlayerVideoHandler.getInstance().setIndex(index);
        ExoPlayerVideoHandler.getInstance().initiateLoaderController(
                this,
                loadingImage,
                exoPlayerView,
                ExoPlayerVideoHandler.getInstance().getVideoResolution()!=null?ExoPlayerVideoHandler.getInstance().getVideoResolution():getString(R.string.default_resolution),
                this
        );
        ExoPlayerVideoHandler.getInstance().playVideo();
        //ExoPlayerVideoHandler.getInstance().goToForeground();
        setSpinnerValue();
    }

/*    private void releasePlayer() {
        if (player != null) {
            player.removeListener(componentListener);
            player.release();
            player = null;
        }
    }*/

/*    public void playNextVideo() {
        loadingImage.setVisibility(View.VISIBLE);
        releasePlayer();
        index = ExoPlayerVideoHandler.getInstance().getIndex();
        index++;
        if(index==contents.size()){
            index=0;
        }
        ExoPlayerVideoHandler.getInstance().setIndex(index);
        initiatePlayer();
    }*/

   /* private class ComponentListener extends com.google.android.exoplayer2.Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    loadingImage.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    loadingImage.setVisibility(View.GONE);
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    loadingImage.setVisibility(View.GONE);
                    playNextVideo();
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d("VideoG", "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }*/

    @Override
    public void onBackPressed(){
        destroyVideo = false;
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();
        ExoPlayerVideoHandler.getInstance().removeListener();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(Util.SDK_INT>getResources().getInteger(R.integer.sdk)){
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
        else{
            if(destroyVideo){
                ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
            }
        }
    }

    @Override
    public void listenPlayerEvent() {
        String resolution = ExoPlayerVideoHandler.getInstance().getLastVideoResolution();
        val = getResources().getStringArray(R.array.quality_array);
        int index = 0;
        for(int i=0; i<val.length; i++){
            if(val[i].equals(resolution)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
//        View itemView = (View)spinner.getChildAt(0);
//        long itemId = spinner.getAdapter().getItemId(0);
//        spinner.performItemClick(itemView, 0, itemId);
//        View itemViewAgain = (View)spinner.getChildAt(index);
//        long itemIdAgain = spinner.getAdapter().getItemId(index);
//        spinner.performItemClick(itemViewAgain, index, itemIdAgain);
    }

    @Override
    public void nextVideo() {
        //do nothing
    }
}


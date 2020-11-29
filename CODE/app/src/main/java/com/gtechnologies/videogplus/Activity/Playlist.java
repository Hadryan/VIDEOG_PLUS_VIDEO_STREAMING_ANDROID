package com.gtechnologies.videogplus.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.gtechnologies.videogplus.Adapter.SpinnerAdapter;
import com.gtechnologies.videogplus.Adapter.SuggestionAdapter;
import com.gtechnologies.videogplus.Http.ContentApiClient;
import com.gtechnologies.videogplus.Http.ContentApiInterface;
import com.gtechnologies.videogplus.Interface.PlayerListener;
import com.gtechnologies.videogplus.Interface.SuggesationInterface;
import com.gtechnologies.videogplus.Library.ExoPlayerVideoHandler;
import com.gtechnologies.videogplus.Library.KeyWord;
import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.Model.Content;
import com.gtechnologies.videogplus.Model.ContentResponse;
import com.gtechnologies.videogplus.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 3/29/2018.
 */

public class Playlist extends AppCompatActivity implements PlayerListener, SuggesationInterface {

    PlayerView playerView;
    SimpleExoPlayer player;
    ImageView backBtn, nextTrackImage, premium;
    Utility utility = new Utility(this);
    DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    GifImageView loadingImage, loadingImageList;
    ImageButton nextBtn, fullScreen;
    Dialog dialog;
    boolean isExoPlayerFullscreen = false;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ImageView dropdown;
    String[] val;
    boolean isFirstTime = true;
    LinearLayout qualityLayout;
    TextView nextTrackTitle, nextTrackBrief, nextTrack, noData, suggestionTrack;
    ListView listView;
    ContentApiInterface apiInterface = ContentApiClient.getBaseClient().create(ContentApiInterface.class);
    ContentResponse contentResponse;
    List<Content> contentList;
    SuggestionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        playerView = findViewById(R.id.video_player);
        backBtn = (ImageView)findViewById(R.id.backBtn);
        loadingImage = (GifImageView)findViewById(R.id.loadingImage);
        loadingImageList = (GifImageView)findViewById(R.id.loadingImageList);
        nextBtn = (ImageButton)findViewById(R.id.exo_next_custom);
        fullScreen = (ImageButton)findViewById(R.id.exo_fullscreen);
        spinner = (Spinner) findViewById(R.id.quality_spinner);
        dropdown = (ImageView) findViewById(R.id.dropdown);
        premium = (ImageView) findViewById(R.id.premium);
        qualityLayout = (LinearLayout) findViewById(R.id.quality_layout);
        nextTrackImage = (ImageView) findViewById(R.id.nextTrackImage);
        nextTrackTitle = (TextView) findViewById(R.id.nextTrackTitle);
        nextTrackBrief = (TextView) findViewById(R.id.nextTrackBrief);
        suggestionTrack = (TextView) findViewById(R.id.suggestionTrack);
        noData = (TextView) findViewById(R.id.noData);
        nextTrack = (TextView) findViewById(R.id.nextTrack);
        listView = (ListView) findViewById(R.id.listview);
        utility.setFont(nextTrackTitle);
        utility.setFont(nextTrackBrief);
        utility.setFont(nextTrack);
        utility.setFont(suggestionTrack);
        utility.setFont(noData);
        HashMap<String, Integer> screenResoltion = utility.getScreenRes();
        int widthPX = screenResoltion.get(KeyWord.SCREEN_WIDTH);
        float widthDP = utility.convertPixelsToDp(widthPX);
        float heightDP = (float) (widthDP/1.7647);
        int heightPX = (int) utility.convertDpToPixel(heightDP);
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.width = widthPX;
        params.height = heightPX;
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExoPlayerVideoHandler.getInstance().playNextVideo();
            }
        });
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Playlist.this, FullscreenVideoActivity.class);
                startActivity(intent);
            }
        });
        spinnerAdapter = new SpinnerAdapter(this);
        spinner.setAdapter(spinnerAdapter);
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
        nextTrack.setText(utility.getLangauge().equals("bn")?getString(R.string.next_bn):getString(R.string.next));
        suggestionTrack.setText(utility.getLangauge().equals("bn")?getString(R.string.suggestion_bn):getString(R.string.suggestion));
    }

    private void getSuggestedVideo(){
        utility.hideAndShowView(new View[]{loadingImageList, listView, noData}, loadingImageList);
        Call<ContentResponse> call = apiInterface.getSuggestedVideo(getString(R.string.authorization_key), utility.getMsisdn(), "", ExoPlayerVideoHandler.getInstance().getCurrentVideoInfo().getId());
        call.enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                if(response.isSuccessful()&&response.code()==200){
                    contentResponse = response.body();
                    if(contentResponse.getCode()==200){
                        contentList = contentResponse.getContents();
                        if(contentList.size()>0){
                            utility.hideAndShowView(new View[]{loadingImageList, listView, noData}, listView);
                            adapter = new SuggestionAdapter(Playlist.this, contentList, Playlist.this);
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);
                        }
                        else{
                            utility.hideAndShowView(new View[]{loadingImageList, listView, noData}, noData);
                        }
                    }
                    else{
                        utility.hideAndShowView(new View[]{loadingImageList, listView, noData}, noData);
                    }
                }
                else{
                    utility.hideAndShowView(new View[]{loadingImageList, listView, noData}, noData);
                }
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                utility.hideAndShowView(new View[]{loadingImageList, listView, noData}, noData);
            }
        });
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

/*    private void initializePlayer(String url) {
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                new DefaultLoadControl());
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.addListener(componentListener);
    }*/


/*    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private MediaSource buildDashMediaSource(Uri uri) {
        DataSource.Factory manifestDataSourceFactory =
                new DefaultHttpDataSourceFactory("ua");
        DashChunkSource.Factory dashChunkSourceFactory =
                new DefaultDashChunkSource.Factory(
                        new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
        return new DashMediaSource.Factory(dashChunkSourceFactory,
                manifestDataSourceFactory).createMediaSource(uri);
    }*/


    @Override
    public void onStart() {
        super.onStart();
        /*if (Util.SDK_INT > 23) {
            initializePlayer(getString(R.string.image_url)+contents.get(index).getPath360());
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        initiatePlayer();
        setNextSongInfo();
        getSuggestedVideo();
        /*hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer(getString(R.string.image_url)+contents.get(index).getPath360());
        }*/
    }

    private void initiatePlayer() {
        //String url = getString(R.string.image_url)+contents.get(ExoPlayerVideoHandler.getInstance().getIndex()).getPath360();
        //ExoPlayerVideoHandler exoPlayerVideoHandler = ExoPlayerVideoHandler.getInstance();
        /*player = ExoPlayerVideoHandler.getInstance()
                .prepareExoPlayerForUri(this,
                        Uri.parse(url), (SimpleExoPlayerView) playerView);
        ExoPlayerVideoHandler.getInstance().goToForeground();
        player.setPlayWhenReady(true);
        player.addListener(componentListener);*/
        //ExoPlayerVideoHandler.getInstance().setIndex(index);
        ExoPlayerVideoHandler.getInstance().initiateLoaderController(
                this,
                loadingImage,
                playerView,
                ExoPlayerVideoHandler.getInstance().getVideoResolution()!=null?ExoPlayerVideoHandler.getInstance().getVideoResolution():getString(R.string.default_resolution),
                this);
        //ExoPlayerVideoHandler.getInstance().goToForeground();
        ExoPlayerVideoHandler.getInstance().playVideo();
        setSpinnerValue();
    }

 /*   @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }*/

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().removeListener();
        ExoPlayerVideoHandler.getInstance().goToBackground();
        /*if (Util.SDK_INT <= 23) {
            releasePlayer();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(utility.getPlayingFrom().equals(KeyWord.PLAYING_NORMAL)) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
/*        if (Util.SDK_INT > 23) {
            releasePlayer();
        }*/
    }

/*    private void releasePlayer() {
        if (player != null) {
            player.removeListener(componentListener);
            player.release();
            player = null;
        }
    }

    private class ComponentListener extends com.google.android.exoplayer2.Player.DefaultEventListener {

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
                    //playNextVideo();
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d("VideoG", "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }*/

/*    private void playNextVideo() {
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

/*    private void initFullscreenDialog() {

        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
*//*                if (isExoPlayerFullscreen)
                    closeFullscreenDialog();*//*
                super.onBackPressed();
            }
        };
    }*/

/*    private void openFullscreenDialog() {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        playerView.setResizeMode( AspectRatioFrameLayout.RESIZE_MODE_FIT);
        dialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Player.this, R.drawable.ic_full_screen));
        isExoPlayerFullscreen = true;
        dialog.show();

    }*/

/*    private void closeFullscreenDialog() {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//        ((ViewGroup) playerView.getParent()).removeView(playerView);
//        LinearLayout videoMainLayout = (LinearLayout) findViewById(R.id.video_player_layout);
//        RelativeLayout relativeLayout = (RelativeLayout)videoMainLayout.findViewById(R.id.video_player_relative);
//        relativeLayout.addView(playerView);
//        isExoPlayerFullscreen = false;
        dialog.dismiss();
        //mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fullscreen_expand));
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    }

    @Override
    public void nextVideo() {
        setNextSongInfo();
    }


    private void setNextSongInfo(){
        Content content = ExoPlayerVideoHandler.getInstance().getNextVideoInfo();
        if(content.getPremium().equals("yes")) {
            premium.setVisibility(View.VISIBLE);
        }
        else{
            premium.setVisibility(View.GONE);
        }
        nextTrackTitle.setText(
                utility.getLangauge().equals("bn")?
                utility.decodeBase64(content.getTitleBn()): content.getTitle());
        nextTrackBrief.setText(
                utility.getLangauge().equals("bn")?
                        utility.decodeBase64(content.getBriefBn()): content.getBrief());
        Picasso.with(this)
                .load(getString(R.string.image_url)+ content.getImage())
                .error(R.drawable.rg)
                .placeholder(R.drawable.rg)
                .into(nextTrackImage);
        getSuggestedVideo();
    }

    @Override
    public void refreshPlayer(int position) {
        Intent intent = new Intent(this, Playlist.class);
        ExoPlayerVideoHandler.getInstance().setContentList(contentList);
        ExoPlayerVideoHandler.getInstance().setIndex(position);
        utility.setPlayingFrom(KeyWord.PLAYING_SUGGESTION);
        finish();
        startActivity(intent);
    }
}

package com.gtechnologies.videogplus.Library;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Switch;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.gtechnologies.videogplus.Http.ContentApiClient;
import com.gtechnologies.videogplus.Http.ContentApiInterface;
import com.gtechnologies.videogplus.Interface.PlayerListener;
import com.gtechnologies.videogplus.Model.Content;
import com.gtechnologies.videogplus.R;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 4/2/2018.
 */

public class ExoPlayerVideoHandler {

    private static ExoPlayerVideoHandler instance;

    public static ExoPlayerVideoHandler getInstance(){
        if(instance == null){
            instance = new ExoPlayerVideoHandler();

        }
        return instance;
    }
    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;
    DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    List<Content> contentList;
    int index = 0;
    ComponentListener componentListener;
    GifImageView loadingImage;
    PlayerView exoPlayerView;
    Context context;
    Utility utility;
    String videoResolution;
    String lastVideoResolution = "240p";
    PlayerListener playerListener;
    ContentApiInterface apiInterface = ContentApiClient.getBaseClient().create(ContentApiInterface.class);

    private ExoPlayerVideoHandler(){}

    public void initiateLoaderController(Context context, GifImageView gifImageView, PlayerView exoPlayerView, String videoResolution, PlayerListener playerListener){
        componentListener = new ComponentListener();
        this.loadingImage = gifImageView;
        this.exoPlayerView = exoPlayerView;
        this.context = context;
        utility = new Utility(this.context);
        if(player!=null){
            exoPlayerView.setPlayer(player);
        }
        setVideoResolution(videoResolution);
        this.playerListener = playerListener;
    }

    public void setVideoResolution(String videoResolution){
        this.lastVideoResolution = this.videoResolution;
        this.videoResolution = videoResolution;
    }

    public String getVideoResolution(){
        return videoResolution;
    }

    public void removeListener(){
        if(player!=null){
            player.removeListener(componentListener);
        }
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    public void setContentList(List<Content> contentList){
        this.contentList = contentList;
    }

    public void releasePlayer() {
        if (player != null) {
            player.removeListener(componentListener);
            player.release();
            player = null;
        }
    }

    public void setComponentListener(){
        player.addListener(componentListener);
    }

    private class ComponentListener extends com.google.android.exoplayer2.Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    utility.showToast("Resolution not supported");
                    playerListener.listenPlayerEvent();
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    loadingImage.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    loadingImage.setVisibility(View.GONE);
                    break;
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    loadingImage.setVisibility(View.GONE);
                    playNextVideo();
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            utility.logger("changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }

    public Content getCurrentVideoInfo(){
        return contentList.get(index);
    }

    public void playNextVideo() {
        index = ExoPlayerVideoHandler.getInstance().getIndex();
        index++;
        if(index==contentList.size()){
            index=0;
        }
        if(contentList.get(index).getPremium().equals("yes")){
            if(utility.isSubscribed(contentList.get(index).getId())){
                loadingImage.setVisibility(View.VISIBLE);
                releasePlayer();
                playVideo();
                playerListener.nextVideo();
            }
            else{
                playNextVideo();
            }
        }
        else {
            loadingImage.setVisibility(View.VISIBLE);
            releasePlayer();
            playVideo();
            playerListener.nextVideo();
        }
    }

    public Content getNextVideoInfo(){
        int point = 0;
        if((index+1)<contentList.size()){
            point = index+1;
        }
        return contentList.get(point);
    }

    private Uri getUrl(){
        String url = context.getString(R.string.image_url);
        switch (videoResolution){
            case "144p":
                url += contentList.get(index).getPath144();
                break;
            case "240p":
                url += contentList.get(index).getPath240();
                break;
            case "360p":
                url += contentList.get(index).getPath360();
                break;
            case "480p":
                url += contentList.get(index).getPath480();
                break;
            case "720p":
                url += contentList.get(index).getPath720();
                break;
            case "1080p":
                url += contentList.get(index).getPath1080();
                break;
            default:
                url += contentList.get(index).getPath360();

        }
        return Uri.parse(url);
    }

    public long getPlayerPosition(){
        return player.getContentPosition();
    }

    public void setPlayerPosition(long playerPosition){
        player.seekTo(playerPosition);
    }

    public String getLastVideoResolution(){
        return lastVideoResolution;
    }

    public void playVideo(){
        try {
            Uri uri = getUrl();
            if (context != null && uri != null && exoPlayerView != null) {
                if (!uri.equals(playerUri) || player == null) {
                    // Create a new player if the player is null or
                    // we want to play a new video
                    TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                    player = ExoPlayerFactory.newSimpleInstance(
                            new DefaultRenderersFactory(context),
                            new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                            new DefaultLoadControl());
                    playerUri = uri;
                    // Do all the standard ExoPlayer code here...
                    // Prepare the player with the source.
                    MediaSource mediaSource = buildMediaSource(uri);
                    player.prepare(mediaSource, false, true);
                }
                player.clearVideoSurface();
                player.setVideoSurfaceView(
                        (SurfaceView) exoPlayerView.getVideoSurfaceView());
                player.seekTo(player.getCurrentPosition() + 1);
                exoPlayerView.setPlayer(player);
                player.setPlayWhenReady(true);
                logPlay();
                player.addListener(componentListener);
            }
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
    }

    private void logPlay(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", "8800000000000");
            jsonObject.put("contentId", contentList.get(index).getId());
            jsonObject.put("operation", 1);
            jsonObject.put("playerTime", player.getCurrentPosition());
            jsonObject.put("callbackApi", "");
            jsonObject.put("remoteIp","0.0.0.1");
            jsonObject.put("userAgent","0");
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Call<ResponseBody> call = apiInterface.postLog(context.getString(R.string.authorization_key), utility.getMsisdn(), "", requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200 && response.isSuccessful()) {
                            JSONObject object = new JSONObject(response.body().string());
                            if(object.optInt("code")==200){
                                utility.logger("Record Logged");
                            }
                            else{
                                utility.logger("Record Not Logged");
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                        }
                    }
                    catch (Exception ex){
                        utility.logger(ex.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.logger(t.toString());
                }
            });
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
    }

    public SimpleExoPlayer getPlayer(){
        return player;
    }

    public void releaseVideoPlayer(){
        if(player != null)
        {
            player.release();
        }
        player = null;
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public void goToBackground(){
        if(player != null){
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    public void goToForeground(){
        if(player != null){
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }

}

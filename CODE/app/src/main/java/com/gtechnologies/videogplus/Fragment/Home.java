package com.gtechnologies.videogplus.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.exoplayer2.util.Util;
import com.gtechnologies.videogplus.Activity.BrowserActivity;
import com.gtechnologies.videogplus.Activity.FullscreenVideoActivity;
import com.gtechnologies.videogplus.Activity.Playlist;
import com.gtechnologies.videogplus.Http.ContentApiClient;
import com.gtechnologies.videogplus.Http.ContentApiInterface;
import com.gtechnologies.videogplus.Http.GhooriApiClient;
import com.gtechnologies.videogplus.Http.GhooriApiInterface;
import com.gtechnologies.videogplus.Library.ExoPlayerVideoHandler;
import com.gtechnologies.videogplus.Library.KeyWord;
import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.Model.Bkash;
import com.gtechnologies.videogplus.Model.ContentResponse;
import com.gtechnologies.videogplus.Model.Content;
import com.gtechnologies.videogplus.R;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 9/11/2017.
 */

public class Home extends Fragment {

    Context context;
    Utility utility;
    ContentApiInterface apiInterface = ContentApiClient.getBaseClient().create(ContentApiInterface.class);
    GhooriApiInterface ghooriApiInterface = GhooriApiClient.getBaseClient().create(GhooriApiInterface.class);
    ContentResponse banner;
    List<Content> contents;
    SliderLayout sliderLayout;
    HashMap<String, Integer> screens = new HashMap<>();
    HashMap<Integer, String> hash_file_maps;
    TabLayout tabLayout;
    ViewPager viewPager;
    int item = 3;
    Bkash bkash;

    public Home() {
    }

    @SuppressLint("ValidFragment")
    public Home(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, null);
        if(context==null){
            context = getContext();
        }
        utility = new Utility(context);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ProfileAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        if (isAdded()) {
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        tabLayout.setupWithViewPager(viewPager);
                        changeTabsFont();
                    } catch (Exception ex) {
                        utility.call_error(ex);
                    }
                }
            });
        }
        screens = utility.getScreenRes();
        ViewGroup.LayoutParams params = sliderLayout.getLayoutParams();
        params.width = screens.get(KeyWord.SCREEN_WIDTH);
        params.height = (screens.get(KeyWord.SCREEN_WIDTH) / 3);
        sliderLayout.setLayoutParams(params);
        initiateBanner();
        return view;
    }

    class ProfileAdapter extends FragmentPagerAdapter {

        public ProfileAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Video(context);
                case 1:
                    return new Drama(context);
                case 2:
                    return new Movie(context);
                /*case 2: return new ScheduleFragment(context);*/
            }
            return null;
        }

        @Override
        public int getCount() {
            return item;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    String topTitle = getResources().getString(R.string.song_tab);
                    if (utility.getLangauge().equals("bn")) {
                        topTitle = getResources().getString(R.string.song_tab_bn);
                    }
                    return topTitle;
                }
                case 1: {
                    String topTitle = getResources().getString(R.string.drama_tab);
                    if (utility.getLangauge().equals("bn")) {
                        topTitle = getResources().getString(R.string.drama_tab_bn);
                    }
                    return topTitle;
                }
                case 2: {
                    String topTitle = getResources().getString(R.string.movie_tab);
                    if (utility.getLangauge().equals("bn")) {
                        topTitle = getResources().getString(R.string.movie_tab_bn);
                    }
                    return topTitle;
                }
                /*case 2: return "Schedule";*/
            }
            return null;
        }
    }

    private void changeTabsFont() {
        try {
            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
            int tabsCount = vg.getChildCount();
            for (int j = 0; j < tabsCount; j++) {
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
                int tabChildsCount = vgTab.getChildCount();
                for (int i = 0; i < tabChildsCount; i++) {
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView) {
                        utility.setFont((TextView) tabViewChild);
                        ((TextView) tabViewChild).setTextSize(16);
                    }
                }
            }
        } catch (Exception ex) {
            utility.call_error(ex);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //MyApplication.getInstance().trackScreenView("Footer Fragment");
    }

    private void initiateBanner() {
        if (utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getBanner(context.getString(R.string.authorization_key), utility.getMsisdn(), "");
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        try {
                            banner = response.body();
                            contents = banner.getContents();
                            hash_file_maps = new HashMap<Integer, String>();
                            for (int i = 0; i < contents.size(); i++) {
                                //JSONObject high = jsonHighlight.optJSONObject(i);
                                final Content content = contents.get(i);
                                hash_file_maps.put(content.getId(), content.getBanner());

                                DefaultSliderView defaultSliderView = new DefaultSliderView(context);
                                final int finalI = i;
                                defaultSliderView
                                        .image(context.getString(R.string.image_url) + content.getBannerImage())
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                            @Override
                                            public void onSliderClick(BaseSliderView slider) {
                                                /*utility.setPlayingFrom(KeyWord.PLAYING_NORMAL);
                                                if(Util.SDK_INT<=context.getResources().getInteger(R.integer.sdk)) {
                                                    Intent intent = new Intent(context, Playlist.class);
                                                    //intent.putExtra("contents", new Video(contents));
                                                    ExoPlayerVideoHandler.getInstance().setContentList(contents);
                                                    ExoPlayerVideoHandler.getInstance().setIndex(finalI);
                                                    context.startActivity(intent);
                                                }
                                                else{
                                                    Intent intent = new Intent(context, FullscreenVideoActivity.class);
                                                    ExoPlayerVideoHandler.getInstance().setContentList(contents);
                                                    ExoPlayerVideoHandler.getInstance().setIndex(finalI);
                                                    context.startActivity(intent);
                                                }*/
                                                bkash = utility.getBkashSubscription();
                                                if (utility.getBkashSubscription().getMsisdn().length() == 0) {
                                                    utility.makeSubscriptionDialog(false);
                                                } else {
                                                    if(utility.isNetworkAvailable()) {
                                                        viewBkashStatus(finalI);
                                                    }
                                                    else{
                                                        if(System.currentTimeMillis()<Long.parseLong(utility.getBkashSubscription().getExpireTime())){
                                                            playContent(finalI);
                                                        }
                                                        else{
                                                            utility.showToast("You are not subscribed");
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                defaultSliderView.bundle(new Bundle());
                                defaultSliderView.getBundle()
                                        .putString("extra", content.getBanner());
                                sliderLayout.addSlider(defaultSliderView);
                            }
//                        for (final String name : hash_file_maps.keySet()) {
//                            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
//                            defaultSliderView
//                                    .image(context.getString(R.string.image_url)+hash_file_maps.get(name))
//                                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                                        @Override
//                                        public void onSliderClick(BaseSliderView slider) {
//                                                        /*VendorProfile vendorProfile = new VendorProfile(context, name);
//                                                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
//                                                                .add(R.id.containerView, vendorProfile)
//                                                                .addToBackStack(null)
//                                                                .commit();*/
//                                            utility.showToast(String.valueOf(name));
//                                        }
//                                    });
//                            defaultSliderView.bundle(new Bundle());
//                            defaultSliderView.getBundle()
//                                    .putString("extra", name);
//                            sliderLayout.addSlider(defaultSliderView);
//                        }
                            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            sliderLayout.setCustomAnimation(new DescriptionAnimation());
                            sliderLayout.setDuration(10000);
                            sliderLayout.setCurrentPosition(contents.size() - 1);
                            sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    //utility.logger("Page Changed: "+position+" Link: "+jsonArray.optJSONObject(position).optString("coverImg"));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                            utility.logger(contents.toString());
                        } catch (Exception ex) {
                            Log.d("RESULT", ex.toString());
                        }
                    } else {
                        utility.showToast("Response Code " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ContentResponse> call, Throwable t) {
                    utility.logger(t.toString());
                    utility.showToast(context.getString(R.string.http_error));
                }
            });
        } else {
            utility.showToast(context.getString(R.string.no_internet));
        }
    }

    private void viewBkashStatus(final int position){
        try {
            utility.showProgress(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mdn", bkash.getMsisdn());
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
            final Call<ResponseBody> call = ghooriApiInterface.viewBkashStatus(context.getString(R.string.bkash_authorization_key), requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    utility.hideProgress();
                    if (response.isSuccessful() && response.code() == 200) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            Bkash b = new Bkash();
                            b.setMsisdn(bkash.getMsisdn());
                            b.setStatus(object.optString("status"));
                            b.setUrl(object.optString("url"));
                            b.setExpireTime(object.optString("expireTime"));
                            utility.setBkashSubscription(b);
                            if (b.getStatus().equals("subscribed")) {
                                playContent(position);
                            } else {
                                activeBkash();
                            }
                        }
                        catch (Exception ex){
                            utility.logger(ex.toString());
                        }
                    } else {
                        utility.showToast(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.hideProgress();
                    //initiateView();
                }
            });
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
    }

    private void activeBkash(){
        try {
            utility.showProgress(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mdn", bkash.getMsisdn());
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
            Call<ResponseBody> call = ghooriApiInterface.activateBkash(context.getString(R.string.bkash_authorization_key), requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    utility.hideProgress();
                    if (response.isSuccessful() && response.code() == 200) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            Bkash b = new Bkash();
                            b.setMsisdn(bkash.getMsisdn());
                            b.setStatus(object.optString("status"));
                            b.setUrl(object.optString("url"));
                            b.setExpireTime(object.optString("expireTime"));
                            utility.setBkashSubscription(b);
                            Intent intent = new Intent(context, BrowserActivity.class);
                            intent.putExtra("url", b.getUrl());
                            context.startActivity(intent);
                        }
                        catch (Exception ex){
                            utility.logger(ex.toString());
                        }
                        //initiateView();
                    } else {
                        utility.showToast(String.valueOf(response.code()));
                        //initiateView();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.hideProgress();
                    //initiateView();
                }
            });
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
    }

    private void playContent(int position) {
        utility.setPlayingFrom(KeyWord.PLAYING_NORMAL);
        if (Util.SDK_INT <= context.getResources().getInteger(R.integer.sdk)) {
            Intent intent = new Intent(context, Playlist.class);
            //intent.putExtra("contents", new Video(contents));
            ExoPlayerVideoHandler.getInstance().setContentList(contents);
            ExoPlayerVideoHandler.getInstance().setIndex(position);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, FullscreenVideoActivity.class);
            ExoPlayerVideoHandler.getInstance().setContentList(contents);
            ExoPlayerVideoHandler.getInstance().setIndex(position);
            context.startActivity(intent);
        }
    }
}


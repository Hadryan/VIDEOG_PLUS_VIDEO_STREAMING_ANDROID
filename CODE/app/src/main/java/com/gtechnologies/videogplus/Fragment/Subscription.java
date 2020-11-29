package com.gtechnologies.videogplus.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtechnologies.videogplus.Activity.BrowserActivity;
import com.gtechnologies.videogplus.Http.ContentApiClient;
import com.gtechnologies.videogplus.Http.ContentApiInterface;
import com.gtechnologies.videogplus.Http.GhooriApiClient;
import com.gtechnologies.videogplus.Http.GhooriApiInterface;
import com.gtechnologies.videogplus.Interface.LanguageInterface;
import com.gtechnologies.videogplus.Interface.SubscriptionInterfacce;
import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.Model.Bkash;
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

public class Subscription extends Fragment implements SubscriptionInterfacce {

    Context context;
    Utility utility;
    TextView tvNumberLabel, tvNumber, tvStatusLabel, tvStatus, tvMessage, tvExpiryLabel, tvExpiry;
    //RadioGroup rgPackage;
    //RadioButton rbMonthly, rbFree;
    Button btnUnsub;
    LanguageInterface languageInterface;
    GhooriApiInterface apiInterface = GhooriApiClient.getBaseClient().create(GhooriApiInterface.class);
    LinearLayout subscriberView, unsubscriberView;
    TextView unsubscriberMessage;
    Button unsubscriberBtn;
    boolean isActive = false;
    Bkash bkash;
    LinearLayout llExpireTime;

    public Subscription() {
    }

    @SuppressLint("ValidFragment")
    public Subscription(Context context, LanguageInterface languageInterface) {
        this.context = context;
        utility = new Utility(this.context, this);
        this.languageInterface = languageInterface;
        bkash = utility.getBkashSubscription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_layout, null);
        tvNumberLabel = (TextView) view.findViewById(R.id.tv_number_label);
        tvNumber = (TextView) view.findViewById(R.id.tv_number);
        tvStatusLabel = (TextView) view.findViewById(R.id.tv_status_label);
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        llExpireTime = (LinearLayout) view.findViewById(R.id.ll_expiretime);
/*        tvPlan = (TextView) view.findViewById(R.id.tv_plan);
        rgPackage = (RadioGroup) view.findViewById(R.id.rg_package);
        rbMonthly = (RadioButton) view.findViewById(R.id.rb_monthly);
        rbFree = (RadioButton) view.findViewById(R.id.rb_free);*/
        btnUnsub = (Button) view.findViewById(R.id.btn_unsubscribe);
        subscriberView = (LinearLayout) view.findViewById(R.id.subscriberView);
        unsubscriberView = (LinearLayout) view.findViewById(R.id.unsubscriberView);
        unsubscriberMessage = (TextView) view.findViewById(R.id.unsubscribeMessage);
        unsubscriberBtn = (Button) view.findViewById(R.id.unsubscribeBtn);
        tvExpiryLabel = (TextView) view.findViewById(R.id.tv_expiry_label);
        tvExpiry = (TextView) view.findViewById(R.id.tv_expiry);
        utility.setFonts(new View[]{tvExpiryLabel, tvExpiry, tvNumberLabel, tvNumber, tvStatusLabel, tvStatus, tvMessage, btnUnsub, unsubscriberMessage, unsubscriberBtn});
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("bkash.payment");
        context.registerReceiver(broadcastReceiver, intentFilter);
        return view;
    }

    private void initiateView() {
        bkash = utility.getBkashSubscription();
        if(bkash.getMsisdn().length()==0){
            unsubscriberView.setVisibility(View.VISIBLE);
            subscriberView.setVisibility(View.GONE);
            unsubscriberMessage.setText(utility.getLangauge().equals("bn")?context.getString(R.string.unsub_msg_bn):context.getString(R.string.unsub_msg_en));
            unsubscriberBtn.setText(utility.getLangauge().equals("bn")?context.getString(R.string.unsub_btn_bn):context.getString(R.string.unsub_btn_en));
        }
        else{
            viewBkashStatus();
        }
        unsubscriberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utility.makeSubscriptionDialog(true);
            }
        });
        btnUnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bkash.getStatus().equals("unsubscribed")){
                    activeBkash();
                }
                else{
                    deactiveBkash();
                }
            }
        });
    }
    @Override
    public void activate() {
        utility.showToast("Activated");
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void viewStatus() {

    }

    private void initiateWithNumberView(){
        bkash = utility.getBkashSubscription();
        unsubscriberView.setVisibility(View.GONE);
        subscriberView.setVisibility(View.VISIBLE);
        tvNumberLabel.setText(utility.getLangauge().equals("bn")?context.getString(R.string.number_bn):context.getString(R.string.number_en));
        tvStatusLabel.setText(utility.getLangauge().equals("bn")?context.getString(R.string.verified_bn):context.getString(R.string.verified_en));
        tvExpiryLabel.setText(utility.getLangauge().equals("bn")?context.getString(R.string.expired_bn):context.getString(R.string.expired_en));
        tvMessage.setText(utility.getLangauge().equals("bn")?context.getString(R.string.message_bn):context.getString(R.string.message_en));
        btnUnsub.setText(utility.getLangauge().equals("bn")?(bkash.getStatus().equals("unsubscribed")?context.getString(R.string.sub_bn):context.getString(R.string.unsub_bn)):(bkash.getStatus().equals("unsubscribed")?context.getString(R.string.sub_en):context.getString(R.string.unsub_en)));
        tvNumber.setText(utility.getLangauge().equals("bn")?utility.convertToBangle(bkash.getMsisdn().substring(2)):bkash.getMsisdn().substring(2));
        tvStatus.setText(utility.getLangauge().equals("bn")?(bkash.getStatus().equals("subscribed")?context.getString(R.string.yes_bn):context.getString(R.string.no_bn)):(bkash.getStatus().equals("subscribed")?"Yes":"No"));
        tvExpiry.setText(utility.getLangauge().equals("bn")?utility.convertToBangle(utility.getDate(Long.parseLong(bkash.getExpireTime()))):utility.getDate(Long.parseLong(bkash.getExpireTime())));
        if(bkash.getStatus().equals("subscribed")){
            btnUnsub.setVisibility(View.GONE);
            llExpireTime.setVisibility(View.VISIBLE);
        }
        else{
            llExpireTime.setVisibility(View.GONE);
            btnUnsub.setVisibility(View.VISIBLE);
        }
    }

    private void viewBkashStatus(){
        try {
            utility.showProgress(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mdn", bkash.getMsisdn());
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
            Call<ResponseBody> call = apiInterface.viewBkashStatus(getString(R.string.bkash_authorization_key), requestBody);
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
                            initiateWithNumberView();
                        }
                        catch (Exception ex){
                            utility.logger(ex.toString());
                        }
                    } else {
                        utility.showToast(String.valueOf(response.code()));
                        initiateView();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.hideProgress();
                    initiateView();
                }
            });
        }
        catch (Exception ex){
            utility.hideProgress();
            utility.logger(ex.toString());
        }
    }

    private void deactiveBkash(){
        try {
            utility.showProgress(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mdn", bkash.getMsisdn());
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
            Call<ResponseBody> call = apiInterface.deactivateBkash(getString(R.string.bkash_authorization_key), requestBody);
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
                            initiateView();
                        }
                        catch (Exception ex){
                            utility.logger(ex.toString());
                        }
                    } else {
                        utility.showToast(String.valueOf(response.code()));
                        initiateView();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.hideProgress();
                    initiateView();
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
            Call<ResponseBody> call = apiInterface.activateBkash(getString(R.string.bkash_authorization_key), requestBody);
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
                            if(b.getUrl().length()>0) {
                                Intent intent = new Intent(context, BrowserActivity.class);
                                intent.putExtra("url", b.getUrl());
                                context.startActivity(intent);
                            }
                            else{
                                utility.showToast("Error");
                            }
                        }
                        catch (Exception ex){
                            utility.logger(ex.toString());
                        }
                        //initiateView();
                    } else {
                        utility.showToast(String.valueOf(response.code()));
                        initiateView();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.hideProgress();
                    initiateView();
                }
            });
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initiateView();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initiateView();
    }

    @Override
    public void numberSet() {
        bkash = utility.getBkashSubscription();
        viewBkashStatus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(broadcastReceiver);
    }
}

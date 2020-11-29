package com.gtechnologies.videogplus.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gtechnologies.videogplus.Http.GhooriApiClient;
import com.gtechnologies.videogplus.Http.GhooriApiInterface;
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

public class BrowserActivity extends AppCompatActivity {

    Utility utility = new Utility(this);
    ImageView ivBack;
    TextView tvTitle;
    WebView wvAll;
    String url;
    GhooriApiInterface contentApiInterface = GhooriApiClient.getBaseClient().create(GhooriApiInterface.class);
    Bkash bkash;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        bkash = utility.getBkashSubscription();
        url = getIntent().getExtras().getString("url");
        ivBack = (ImageView)findViewById(R.id.iv_back);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        wvAll = (WebView)findViewById(R.id.wv_all);
        utility.setFonts(new View[]{tvTitle});
        tvTitle.setText(utility.getLangauge().equals("bn")?getString(R.string.bkash_registration_bn):getString(R.string.bkash_registration_en));
        WebSettings webSettings = wvAll.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        wvAll.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvAll.loadUrl(url);
        wvAll.setWebViewClient(new MyWebViewClient());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wvAll.addJavascriptInterface(new WebAppInterface(this), "Android");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(!mProgressDialog.isShowing()){
                mProgressDialog.show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(mContext, "Thanks for your payment", Toast.LENGTH_SHORT).show();
            viewBkashStatus();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvAll.canGoBack()) {
            wvAll.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    private void viewBkashStatus(){
        try {
            utility.showProgress(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mdn", bkash.getMsisdn());
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
            Call<ResponseBody> call = contentApiInterface.viewBkashStatus(getString(R.string.bkash_authorization_key), requestBody);
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
                            finish();
                        }
                        catch (Exception ex){
                            utility.logger(ex.toString());
                        }
                    } else {
                        utility.showToast(String.valueOf(response.code()));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    utility.hideProgress();
                    finish();
                }
            });
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
    }
}

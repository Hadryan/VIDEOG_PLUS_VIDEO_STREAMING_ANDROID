package com.gtechnologies.videogplus.Library;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.telephony.SmsMessage;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gtechnologies.videogplus.Activity.MainActivity;
import com.gtechnologies.videogplus.Interface.SubscriptionInterfacce;
import com.gtechnologies.videogplus.Model.Bkash;
import com.gtechnologies.videogplus.Model.Subscription;
import com.gtechnologies.videogplus.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by fojlesaikat on 8/24/17.
 */

public class Utility {

    public static final String SUBS_MOBILE = "mobile";
    public static final String SUBS_STATUS = "status";
    private static final String KEY_PHRASE = "BDFHJLNPpnljhfdb";


    Context context;
    ProgressDialog mProgressDialog;
    AESUtil aesUtil = new AESUtil();
    HashMap<String, String> pushApiMap;
    SubscriptionInterfacce subscriptionInterfacce;

//    BaseApiInterface apiInterface = BaseApiClient.getBaseClient().create(BaseApiInterface.class);
//    ContentApiInterface contentApiInterface = ContentApiClient.getBaseClient().create(ContentApiInterface.class);

    //EditText pinNumber;

    public Utility(Context context) {
        this.context = context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        freeMemory();
    }

    public Utility(Context context, SubscriptionInterfacce subscriptionInterfacce) {
        this.context = context;
        this.subscriptionInterfacce = subscriptionInterfacce;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        freeMemory();
    }


    public boolean isAreaRestricted() {
//        TelephonyManager tm;
//        String countryCodeValue = "";
//        try {
//            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            countryCodeValue = tm.getNetworkCountryIso();
//        } catch (Exception ex) {
//            countryCodeValue = "xx";
//        }
//        String[] country_list = context.getResources().getStringArray(R.array.country_code);
//        for (int i = 0; i < country_list.length; i++) {
//            String code = country_list[i];
//            if (countryCodeValue.equals(code)) {
//                return true;
//            }
//        }
        return false;
    }

    public void setRefreshState(boolean refreshValue) {
        SharedPreferences sharedPref = context.getSharedPreferences("REFRESH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("doRefresh", refreshValue);
        editor.commit();
    }

    public boolean getRefreshState() {
        SharedPreferences sharedPref = context.getSharedPreferences("REFRESH", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("doRefresh", false);
    }

    public void setPlayingFrom(String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("PLAYING", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("value", value);
        editor.commit();
    }

    public String getPlayingFrom() {
        SharedPreferences sharedPref = context.getSharedPreferences("PLAYING", Context.MODE_PRIVATE);
        return sharedPref.getString("value", KeyWord.PLAYING_NORMAL);
    }

    public void writeLanguage(String language) {
        SharedPreferences sharedPref = context.getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("language", language);
        editor.commit();
    }

    public String getLangauge() {
        SharedPreferences sharedPref = context.getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        return sharedPref.getString("language", "en");
    }

    public void writeSubscriptionStatus(HashMap<String, String> map) {
        SharedPreferences sharedPref = context.getSharedPreferences("SUBSCRIBE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (String key : map.keySet()) {
            editor.putString(key, map.get(key));
        }
        editor.commit();
    }

    public HashMap<String, String> getSubscriptionData() {
        HashMap<String, String> map = new HashMap<String, String>();
        SharedPreferences sharedPref = context.getSharedPreferences("SUBSCRIBE", Context.MODE_PRIVATE);
        Map<String, ?> m = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : m.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }

//    public void checkInfo() {
//        String phone, status, message;
//        phone = status = message = "";
//        HashMap<String, String> map = new HashMap<>();
//        map = getSubscriptionData();
//        if (map.size() > 0) {
//            Iterator iterator = map.keySet().iterator();
//            while (iterator.hasNext()) {
//                String key = (String) iterator.next();
//                String value = (String) map.get(key);
//                switch (key) {
//                    case SUBS_MOBILE:
//                        phone = value;
//                        break;
//                    case SUBS_STATUS:
//                        status = value;
//                        break;
//                }
//            }
//            if (status.equals("success")) {
//                try {
//                    JSONObject jsonObject = new JSONObject();
//                    JSONObject jsonAuth = new JSONObject();
//                    jsonAuth.put("username", "gseries");
//                    jsonAuth.put("api_key", "yIraI2Q5k3U79FHv");
//                    jsonAuth.put("api_secret", "W!>1/{8,&TVbVhWK");
//                    JSONObject request = new JSONObject();
//                    request.put("request", "STATUS");
//                    request.put("mobile", phone);
//                    jsonObject.put("auth", jsonAuth);
//                    jsonObject.put("request_data", request);
//                    String postBody = jsonObject.toString();
////                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody);
////                    checkSubscription(body);
//                } catch (Exception ex) {
//                    showToast(ex.toString());
//                }
//            } else {
//                generatePinDialog();
//            }
//        } else {
//            generatePinDialog();
//        }
////        HashMap<String, String> map = new HashMap<String, String>();
////        if (checkCountryISO()) {
////            map.put(SUBS_IP_FOUND, "yes");
////            writeSubscriptionStatus(map);
////            try {
////                JSONObject jsonObject = new JSONObject();
////                JSONObject jsonAuth = new JSONObject();
////                jsonAuth.put("username", "gseries");
////                jsonAuth.put("api_key", "yIraI2Q5k3U79FHv");
////                jsonAuth.put("api_secret", "W!>1/{8,&TVbVhWK");
////                JSONObject request = new JSONObject();
////                request.put("request", "STATUS");
////                request.put("mobile", finalMsisdn);
////                jsonObject.put("auth", jsonAuth);
////                jsonObject.put("request_data", request);
////                String postBody = jsonObject.toString();
////                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody);
////                checkSubscription(body);
////            } catch (Exception ex) {
////
////            }
////        } else {
////            map.put(SUBS_IP_FOUND, "no");
////            writeSubscriptionStatus(map);
////        }
//    }

//    private void generatePinDialog() {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.rating_layout);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        TextView title = (TextView) dialog.findViewById(R.id.subscription_title);
//        final EditText phoneNumber = (EditText) dialog.findViewById(R.id.phone_number);
//        Button cancelBtn = (Button) dialog.findViewById(R.id.rating_btn_cancel);
//        Button submitBtn = (Button) dialog.findViewById(R.id.rating_btn_submit);
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                if (isNetworkAvailable()) {
//                    try {
//                        if (phoneNumber.length() > 0) {
//                            pushApiMap = new HashMap<String, String>();
//                            try {
//                                pushApiMap.put("user", aesUtil.encrypt("radiog", KEY_PHRASE));
//                                pushApiMap.put("password", aesUtil.encrypt("radiog9865", KEY_PHRASE));
//                                pushApiMap.put("mobile", phoneNumber.getText().toString());
//                                pushApiMap.put("msisdn", aesUtil.encrypt(phoneNumber.getText().toString(), KEY_PHRASE));
//                                pushApiMap.put("package", aesUtil.encrypt("1185", KEY_PHRASE));
//                                pushApiMap.put("txn", String.valueOf(System.currentTimeMillis()));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            String urlString = "http://pt5.etisalat.ae/Moneta/pushPIN.htm?" +
//                                    "usr=" + pushApiMap.get("user") +
//                                    "&pwd=" + pushApiMap.get("password") +
//                                    "&msisdn=" + pushApiMap.get("msisdn") +
//                                    "&packageid=" + pushApiMap.get("package") +
//                                    "&txnid=" + pushApiMap.get("txn");
//                            URL url = new URL(urlString);
//                            new EtisalatPushAPI().execute(url);
//                            //generatePin(pushApiMap);
//                        } else {
//                            showToast("Please Write Number");
//                        }
//                    } catch (Exception ex) {
//                        //utility.showToast(ex.toString());
//                        Log.d("RESULT", ex.toString());
//                    }
//                } else {
//                    showToast("No Internet");
//                }
//            }
//        });
//        dialog.setCancelable(false);
//        dialog.show();
//    }


//    private class EtisalatPushAPI extends AsyncTask<URL, Integer, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            showProgress();
//        }
//
//        protected String doInBackground(URL... urls) {
//            try {
//                //String url = "http://pt5.etisalat.ae/Moneta/pushPIN.htm?usr=BcH9ZG4i9nKL7vjMvO%2BXtg%3D%3D%0A&pwd=akyH7JWTJTUUZKbkrAndEQ%3D%3D%0A&msisdn=uwokd6xSIdrIpjyzaSiMeA%3D%3D%0A&packageid=5veSuTKhoeUM14IJK1A5Wg%3D%3D%0A&txnid=82321892";
//                URL obj = urls[0];
//                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//                // optional default is GET
//                con.setRequestMethod("GET");
//                int responseCode = con.getResponseCode();
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                //showToast(response.toString());
//                return response.toString();
//            } catch (Exception ex) {
//                showToast(ex.toString());
//                return "error";
//            }
//        }
//
//        protected void onPostExecute(String result) {
//            hideProgress();
//            try {
//                String status = result.substring(0, result.lastIndexOf('|'));
//                String token = result.substring(result.lastIndexOf('|') + 1);
//                if (status.equals("pin_sent")) {
//                    pushApiMap.put("token", token);
//                    checkPinDialog();
//                } else {
//                    showToast(status);
//                }
//            } catch (Exception ex) {
//                showToast(ex.toString());
//            }
//        }
//
//    }

    private class EtisalatConfirmAPI extends AsyncTask<URL, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected String doInBackground(URL... urls) {
            try {
                //String url = "http://pt5.etisalat.ae/Moneta/pushPIN.htm?usr=BcH9ZG4i9nKL7vjMvO%2BXtg%3D%3D%0A&pwd=akyH7JWTJTUUZKbkrAndEQ%3D%3D%0A&msisdn=uwokd6xSIdrIpjyzaSiMeA%3D%3D%0A&packageid=5veSuTKhoeUM14IJK1A5Wg%3D%3D%0A&txnid=82321892";
                URL obj = urls[0];
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //showToast(response.toString());
                return response.toString();
            } catch (Exception ex) {
                showToast(ex.toString());
                return "error";
            }
        }

        protected void onPostExecute(String result) {
            hideProgress();
            try {
                String status = result.substring(0, result.lastIndexOf('|'));
                String token = result.substring(result.lastIndexOf('|') + 1);
                HashMap<String, String> map = new HashMap<String, String>();
                if (status.equals("success") || status.equals("Already_Active")) {
                    showToast("Verified");
                    map.put(SUBS_MOBILE, pushApiMap.get("mobile"));
                    map.put(SUBS_STATUS, "success");
                } else {
                    showToast("Not Verified");
                    map.put(SUBS_MOBILE, pushApiMap.get("mobile"));
                    map.put(SUBS_STATUS, "failed");
                }
                writeSubscriptionStatus(map);
            } catch (Exception ex) {
                showToast(ex.toString());
            }
        }

    }

//    private void generatePin(final HashMap<String, String> map) {
//        showProgress();
//        Call<String> call = etisalatApiInterface.pinPushAPI(
//                map.get("user"),
//                map.get("password"),
//                map.get("msisdn"),
//                map.get("package"),
//                map.get("txn")
//        );
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                hideProgress();
//                if (response.isSuccessful() && response.code() == 200) {
//                    try {
//                        String result = response.body();
//                        String status = result.substring(0, result.lastIndexOf('|'));
//                        String token = result.substring(result.lastIndexOf('|')+1);
//                        if (status.equals("pin_sent")) {
//                            map.put("token",aesUtil.encrypt(token,KEY_PHRASE));
//                            checkPinDialog(map);
//                        } else {
//                            showToast(status);
//                        }
//                    } catch (Exception ex) {
//                        Log.d("RESULT", ex.toString());
//                    }
//                } else {
//                    showToast("Response not found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("RESULT", t.toString());
//                hideProgress();
//            }
//        });
//    }

    /*
    * This function finds out the OTP Pin from String Provided*/
//    public void setPin(String message){
//        String pin = message.substring(message.indexOf("PIN:")+5,message.indexOf("PIN")+9);
//        pinNumber.setText(pin);
//    }

    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }

//    private void checkPinDialog() {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.rating_layout);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        TextView title = (TextView) dialog.findViewById(R.id.subscription_title);
//        title.setText("Verify Pin");
//        final EditText pinNumber = (EditText) dialog.findViewById(R.id.phone_number);
//        pinNumber.setHint("Enter Your Pin");
////        IntentFilter intentFilter = new IntentFilter(
////                "android.provider.Telephony.SMS_RECEIVED");
////        IncomingSMS incomingSMS = new IncomingSMS() {
////            @Override
////            public void onReceive(Context context, Intent intent) {
////                final Bundle bundle = intent.getExtras();
////                try {
////                    if (bundle != null) {
////                        final Object[] pdusObj = (Object[]) bundle.get("pdus");
////                        for (int i = 0; i < pdusObj.length; i++) {
////                            SmsMessage currentMessage = getIncomingMessage(pdusObj[i], bundle);
////                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();
////                            String senderNum = phoneNumber;
////                            String message = currentMessage.getMessageBody();
////                            try {
////                                if (senderNum.equals(context.getString(R.string.short_code))) {
////                                    showToast(message);
////                                    String pin = message.substring(message.indexOf("PIN") + 5, message.indexOf("PIN") + 9);
////                                    showToast(pin);
////                                    if (dialog.isShowing()) {
////                                        pinNumber.setText(pin);
////                                    }
////                                    break;
////                                }
////                            } catch (Exception e) {
////                                Log.d("Radio", e.toString());
////                            }
////                        }
////                    }
////                } catch (Exception ex) {
////                    // do nothing
////                }
////            }
////        };
////        context.registerReceiver(incomingSMS, intentFilter);
//        Button cancelBtn = (Button) dialog.findViewById(R.id.rating_btn_cancel);
//        Button submitBtn = (Button) dialog.findViewById(R.id.rating_btn_submit);
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                if (isNetworkAvailable()) {
//                    try {
//                        if (pinNumber.length() > 0) {
//                            pushApiMap.put("pin", aesUtil.encrypt(pinNumber.getText().toString(), KEY_PHRASE));
//                            String urlString = "http://pt5.etisalat.ae/Moneta/confirmPinSubscription.htm?" +
//                                    "usr=" + pushApiMap.get("user") +
//                                    "&pwd=" + pushApiMap.get("password") +
//                                    "&msisdn=" + pushApiMap.get("msisdn") +
//                                    "&packageid=" + pushApiMap.get("package") +
//                                    "&pin=" + pushApiMap.get("pin") +
//                                    "&token=" + pushApiMap.get("token") +
//                                    "&txnid=" + pushApiMap.get("txn");
//                            URL url = new URL(urlString);
//                            new EtisalatConfirmAPI().execute(url);
//                        } else {
//                            showToast("Please Write Number");
//                        }
//                    } catch (Exception ex) {
//                        //utility.showToast(ex.toString());
//                        Log.d("RESULT", ex.toString());
//                    }
//                } else {
//                    showToast("No Internet");
//                }
//            }
//        });
//        dialog.setCancelable(false);
//        dialog.show();
//    }

//    private void verifyPin(HashMap<String, String> map) {
//        showProgress();
//        Call<String> call = etisalatApiInterface.confirmPinSubscription(
//                map.get("user"),
//                map.get("password"),
//                map.get("msisdn"),
//                map.get("package"),
//                map.get("pin"),
//                map.get("token"),
//                map.get("txn")
//        );
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                hideProgress();
//                if (response.isSuccessful() && response.code() == 200) {
//                    try {
//                        String result = response.body();
//                        String status = result.substring(0, result.lastIndexOf('|'));
//                        String token = result.substring(result.lastIndexOf('|')+1);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        if (status.equals("success")||status.equals("Already_Active")) {
//                            showToast("Verified");
//                            map.put(SUBS_MOBILE, map.get("msisdn"));
//                            map.put(SUBS_STATUS, "success");
//                        } else {
//                            showToast("Not Verified");
//                            map.put(SUBS_MOBILE, map.get("msisdn"));
//                            map.put(SUBS_STATUS, "failed");
//                        }
//                        writeSubscriptionStatus(map);
//                    } catch (Exception ex) {
//                        Log.d("RESULT", ex.toString());
//                    }
//                } else {
//                    showToast("Response not found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("RESULT", t.toString());
//                hideProgress();
//            }
//        });
//    }

//    private void checkSubscription(RequestBody requestBody) {
//        showProgress();
//        Call<ResponseBody> call = apiInterface.getSubscription(requestBody);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                hideProgress();
//                if (response.isSuccessful() && response.code() == 200) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response.body().string());
//                        Log.d("RESULT", jsonObject.toString());
//                        JSONObject jsonResult = jsonObject.optJSONObject("result");
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(SUBS_MOBILE, jsonResult.optString("mobile"));
//                        map.put(SUBS_STATUS, jsonResult.optString("status"));
//                        writeSubscriptionStatus(map);
//                        if (jsonResult.optString("status").equals("failed")) {
//                            generatePinDialog();
//                        } else {
//                            showToast("Already Subscribed");
//                        }
//                    } catch (Exception ex) {
//                        Log.d("RESULT", ex.toString());
//                    }
//                } else {
//                    showToast("Response not found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("RESULT", t.toString());
//                hideProgress();
//            }
//        });
//    }


    /*
   ================ Show Progress Dialog ===============
   */
    public void showProgress() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }

    /*
   ================ Show Progress Dialog ===============
   */
    public void showProgress(boolean isCancelable) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }

    /*
    ================ Hide Progress Dialog ===============
    */
    public void hideProgress() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }


    /*
================ Show Toast Message ===============
*/
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /*
    ================ Error Called Function ===============
    */

    public void call_error(Exception ex) {
        String error = ex.getMessage();
        //StackTraceElement[] message = ex.getStackTrace();
        showToast(error);
        Intent intent = new Intent(context, MainActivity.class);
        /*StringBuilder builder = new StringBuilder();
        int i=1;
        for (StackTraceElement trace : message) {
            builder.append("Exception "+i+"<br>File:"+trace.getFileName()+" | Method: "+trace.getMethodName()+" | Line: "+trace.getLineNumber()+"<br>");
            i++;
        }
        builder.append("Caused By:"+ex.toString());
        intent.putExtra("error",error);
        intent.putExtra("description",builder.toString());*/
        context.startActivity(intent);
    }

    /*
    =============== Set Window FullScreen ===============
    */
    public void setFullScreen() {
        Activity activity = ((Activity) context);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
    ================ Get Screen Width ===============
    */
    public HashMap<String, Integer> getScreenRes() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        map.put(KeyWord.SCREEN_WIDTH, width);
        map.put(KeyWord.SCREEN_HEIGHT, height);
        return map;
    }

    /*
    ================ Convert PIXEL to DP ===============
    */
    public float convertPixelsToDp(float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /*
    ================ Convert DP to PIXEL ===============
    */
    public float convertDpToPixel(float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    /*
    ================ Log function ===============
     */
    public void logger(String message) {
        Log.d(context.getString(R.string.tag), message);
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String date = sdf.format(new Date());
        //writeToFile(date+" -> "+message);
    }

    /*
    =============== Set Font ===============
    */
    public void setFont(View view) {
        Typeface tf = null;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi.ttf");
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTypeface(tf);
        } else if (view instanceof EditText) {
            EditText et = (EditText) view;
            et.setTypeface(tf);
        } else if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setTypeface(tf);
        } else if (view instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) view;
            SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
            mNewTitle.setSpan(tf, 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            menuItem.setTitle(mNewTitle);
        }

    }

    /*
    =============== Set Font ===============
    */
    public void setFonts(View[] views) {
        Typeface tf = null;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi.ttf");
        for(int i=0;i<views.length; i++) {
            View view = views[i];
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTypeface(tf);
                if(getLangauge().equals("bn")) {
                    tv.setTextSize(18);
                }else{
                    tv.setTextSize(16);
                }
            } else if (view instanceof EditText) {
                EditText et = (EditText) view;
                et.setTypeface(tf);
                if(getLangauge().equals("bn")) {
                    et.setTextSize(18);
                }else{
                    et.setTextSize(16);
                }
            } else if (view instanceof Button) {
                Button btn = (Button) view;
                btn.setTypeface(tf);
                if(getLangauge().equals("bn")) {
                    btn.setTextSize(18);
                }else{
                    btn.setTextSize(16);
                }
            } else if (view instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) view;
                SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
                mNewTitle.setSpan(tf, 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                menuItem.setTitle(mNewTitle);
            }
        }
    }

    /*
   =============== Set Font ===============
   */
    public void setMenuFont(Menu menu) {
        Typeface tf = null;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi.ttf");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
            mNewTitle.setSpan(new CustomTypefaceSpan("", tf), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle.setSpan(new AbsoluteSizeSpan(16, true), 0, mNewTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            menuItem.setTitle(mNewTitle);
        }

    }

    /*
============== Base64 Decode =========
*/
    public String decodeBase64(String message) {
        String text = "Conversion Error";
        try {
            byte[] bytes = null;
            bytes = Base64.decode(message, Base64.DEFAULT);
            text = new String(bytes, "UTF-8");
        } catch (Exception ex) {
            logger(ex.toString());
        }
        return text;
    }

    /*
    ============== Base64 Encode =========
     */
    public String encodeBase64(String message) {
        String text = "Conversion Error";
        try {
            byte[] data = message.getBytes("UTF-8");
            text = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception ex) {
            logger(ex.toString());
        }
        return text;
    }


    /*
    =============== Get Image Resource ID by Name ==============
    */
    public int getResourceId(String name) {
        name = name.replace(" ", "").toLowerCase();
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }


    /*
    =============== Create Circle Background ==============
    */
//    public void customView(View v, int startColor, int endColor) {
//        int colors[] = {startColor, endColor};
//        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
//        shape.setShape(GradientDrawable.RECTANGLE);
//        shape.setGradientType(GradientDrawable.RADIAL_GRADIENT);
//        v.setBackground(shape);
//        //v.setBackgroundDrawable(shape);
//    }

    /*
    ================ Clear Text for EditText, Button, TextView ===============
    */
    public void clearText(View[] view) {
        for (View v : view) {
            if (v instanceof EditText) {
                ((EditText) v).setText("");
            } else if (v instanceof Button) {
                ((Button) v).setText("");
            } else if (v instanceof TextView) {
                ((TextView) v).setText("");
            }
        }
    }

    /*
    ================ Hide Keyboard from Screen ===============
    */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /*
    ================ Show Keyboard to Screen ===============
    */
    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /*
    ================ Hide & Show Views ===============
    */
    public void hideAndShowView(View[] views, View view) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
        view.setVisibility(View.VISIBLE);
    }


    /*
    ================ Bangla Number Converter ============
    */
    public String convertToBangle(String numbers) {
        String banglaNumber = "";
        for (int i = 0; i < numbers.length(); i++) {
            switch (numbers.charAt(i)) {
                case '1':
                    banglaNumber += context.getString(R.string.one);
                    break;
                case '2':
                    banglaNumber += context.getString(R.string.two);
                    break;
                case '3':
                    banglaNumber += context.getString(R.string.three);
                    break;
                case '4':
                    banglaNumber += context.getString(R.string.four);
                    break;
                case '5':
                    banglaNumber += context.getString(R.string.five);
                    break;
                case '6':
                    banglaNumber += context.getString(R.string.six);
                    break;
                case '7':
                    banglaNumber += context.getString(R.string.seven);
                    break;
                case '8':
                    banglaNumber += context.getString(R.string.eight);
                    break;
                case '9':
                    banglaNumber += context.getString(R.string.nine);
                    break;
                case '0':
                    banglaNumber += context.getString(R.string.zero);
                    break;
                default:
                    banglaNumber += numbers.charAt(i);
                    break;
            }
        }
        return banglaNumber;
    }


//    private long getNextSchedule() {
//        try {
//            String[] schedules = context.getResources().getStringArray(R.array.schedules);
//            long timeDistance = 0;
//            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
//            long currentTimeInMillis = calendar.getTimeInMillis();
//            for (int i = 0; i < schedules.length; i++) {
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//                String date = sdf.format(new Date()) + " " + schedules[i];
//                SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.ENGLISH);
//                Date formattedDate = sdf2.parse(date);
//                long timeInMillis = formattedDate.getTime();
//                long distance = timeInMillis - currentTimeInMillis;
//                if (distance >= 0) {
//                    if (timeDistance != 0) {
//                        if (distance < timeDistance) {
//                            timeDistance = distance;
//                        }
//                    } else {
//                        timeDistance = distance;
//                    }
//                }
//            }
//            return timeDistance;
//        } catch (Exception ex) {
//            logger(ex.toString());
//            return 0;
//        }
//    }

    /*
    ================ Write to file ============
    */
    public void writeToFile(String message) {
/*        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            String date = sdf.format(new Date());
            String value = date + " -> "+ message+"\n";
            if (isExternalStorageWritable() && isExternalStorageReadable()) {
                File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/RadioG");
                if (!directory.exists()) {
                    if(directory.mkdirs()){
                        logger("Directory Created");
                    }
                    else{
                        logger("Directory Not Created");
                    }
                }
                File file = new File(directory, "radiog.txt");
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.close();
            } else {
                logger("External/Internal Storage is not available");
            }
        }
        catch (Exception ex){
            logger(ex.toString());
        }*/
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

//    public File getAlbumStorageDir(String albumName) {
//        // Get the directory for the user's public pictures directory.
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOCUMENTS), albumName);
//        if (!file.mkdirs()) {
//            logger("Directory not created");
//        }
//        return file;
//    }

//    public void setAlarm(int hour, int minute, int requestId) {
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        boolean alarmUp = (PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_NO_CREATE) != null);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, 0);
//        if (!alarmUp) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, hour);
//            calendar.set(Calendar.MINUTE, minute);
//            if (calendar.before(Calendar.getInstance())) {
//                calendar.add(Calendar.DATE, 1);
//            }
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//            logger("Next " + requestId + " Trigger After " + 2 * 60 + " Sec(s)");
//            writeToFile("Setting Next Alarm on " + hour + ":" + minute);
//        } else {
//            writeToFile("Active Alarm " + hour + ":" + minute);
//        }
//    }

    public boolean isExternalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        }
        long availableBlocks = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        }
        return (availableBlocks * blockSize);
    }

    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        long totalBlocks = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBlocks = stat.getBlockCountLong();
        } else {
            totalBlocks = stat.getBlockCount();
        }
        return (totalBlocks * blockSize);
    }

    public String getAvailableExternalMemorySize() {
        if (isExternalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }
            long availableBlocks = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                availableBlocks = stat.getAvailableBlocks();
            }
            return formatSize(availableBlocks * blockSize);
        } else {
            return "";
        }
    }

    public String getTotalExternalMemorySize() {
        if (isExternalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }
            long totalBlocks = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalBlocks = stat.getBlockCountLong();
            } else {
                totalBlocks = stat.getBlockCount();
            }
            return formatSize(totalBlocks * blockSize);
        } else {
            return "";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    /*
   ================ Check File Size ===============
   */
//    public long checkFileSize(String fileUrl) {
//        long file_size = 0;
//        try {
//            URL url = new URL(fileUrl);
//            URLConnection urlConnection = url.openConnection();
//            urlConnection.connect();
//            file_size = urlConnection.getContentLength();
//        } catch (Exception ex) {
//            logger(ex.toString());
//        }
//        return file_size;
//    }


    /*
   ================ Check File Exists ===============
   */
//    public boolean checkIfSongExists(SongModel songModel) {
//        long file_size = 0;
//        try {
//            File directory = new File(context.getFilesDir(), "RadioG");
//            if (!directory.exists()) {
//                if (directory.mkdir()) {
//                    logger("Directory Created");
//                } else {
//                    logger("Directory Not Created");
//                }
//            }
//            File file = new File(directory.getAbsolutePath() + "/" + songModel.getId() + ".mp3");
//            if (file.exists()) {
////                long fileSize = checkFileSize(context.getString(R.string.image_url)+songModel.getLink());
////                if(file.length()<file_size){
////                    file.delete();
////                }
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception ex) {
//            logger(ex.toString());
//            return false;
//        }
//    }

    /*
    ================ USSD to Call ===============
    */
    public Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:"))
            uriString += "tel:";

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }

    /*
    ================ Convert Seconds to Hours ===============
    */
    public String convertSecondsToHour(long seconds) {
        String second = String.valueOf(seconds % 60);
        String minute = String.valueOf((seconds / 60) % 60);
        String hour = String.valueOf((seconds / 60 / 60) % 60);
        if (second.length() < 2) {
            second = "0" + second;
        }
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        if (hour.length() < 2) {
            hour = "0" + hour;
        }
        if(hour.equals("00")){
            return minute + ":" + second;
        }
        else {
            return hour + ":" + minute + ":" + second;
        }

    }

    /*
    ================ Get Send/Receive Packets ===============
    */
    public HashMap<String, Long> getNetworkInfo() {
        HashMap<String, Long> map = new HashMap<>();
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(
                PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            //get the UID for the selected app
            if (packageInfo.packageName.equals(context.getPackageName())) {
                int uid = packageInfo.uid;
                long received = TrafficStats.getUidRxBytes(uid);
                long send = TrafficStats.getUidTxBytes(uid);
                map.put("send", send /*+ getDataUsage("totalSend")*/);
                map.put("received", received /*+ getDataUsage("totalReceived")*/);
//                writeDataUsage(send, received);
                Log.v("" + uid, "Send :" + send + ", Received :" + received);
                return map;
            }
        }
        map.put("Send", Long.parseLong("0"));
        map.put("Received", Long.parseLong("0"));
        return map;
    }

    /*
    ================ Write Data Usage ===============
    */
    public void writeDataUsage(long send, long received) {
        SharedPreferences sharedPref = context.getSharedPreferences("DATA_USAGE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("send", send);
        editor.putLong("received", received);
        editor.commit();
    }

//    public void writeTotalDataUsage(long send, long received) {
//        logger("Send: "+send);
//        logger("Received: "+received);
//        SharedPreferences sharedPref = context.getSharedPreferences("DATA_USAGE", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putLong("totalSend", getDataUsage("totalSend") + send);
//        editor.putLong("totalReceived", getDataUsage("totalReceived") + received);
//        editor.commit();
//    }

    /*
    ================ Get Data Usage ===============
    */
    public long getDataUsage(String name) {
        SharedPreferences sharedPref = context.getSharedPreferences("DATA_USAGE", Context.MODE_PRIVATE);
        return sharedPref.getLong(name, 0);
    }

    /*
    ================ Garbage Collection ===============
    */
    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    /*
    ================ Get Device Info ===============
    */
    public HashMap<String, String> getDeviceInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Serial", Build.SERIAL);
        map.put("Model", Build.MODEL);
        map.put("Id", Build.ID);
        map.put("Manufacture", Build.MANUFACTURER);
        map.put("Brand", Build.BRAND);
        map.put("Type", Build.TYPE);
        map.put("User", Build.USER);
        map.put("Base", String.valueOf(Build.VERSION_CODES.BASE));
        map.put("Incremental", Build.VERSION.INCREMENTAL);
        map.put("Board", Build.BOARD);
        map.put("Brand", Build.BRAND);
        map.put("Host", Build.HOST);
        map.put("Version Code", Build.VERSION.RELEASE);
        return map;
    }

    public String validateMsisdn(String msisdn){
        if(msisdn.length()!=13){
            return "13 Digit required";
        }
        /*if(msisdn.substring(0,5).equals("88015")||msisdn.substring(0,5).equals("88016")||msisdn.substring(0,5).equals("88018")||msisdn.substring(0,5).equals("88019")){
            return "Operator not supported";
        }*/
        if(
                msisdn.substring(0,5).equals("88010")||
                        msisdn.substring(0,5).equals("88011")||
                        msisdn.substring(0,5).equals("88012")||
                        msisdn.substring(0,5).equals("88014")
                ){
            return "Invalid Operator";
        }
        return "OK";
    }

    public void clearSubscription(){
        SharedPreferences sharedPref = context.getSharedPreferences("SUBS_INFO", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPref.getAll();
        SharedPreferences.Editor editor = sharedPref.edit();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            editor.putString(entry.getKey(), "{}");
        }
        editor.commit();
    }

    public void writeMsisdn(String msisdn) {
        SharedPreferences sharedPref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("msisdn", msisdn);
        editor.commit();
        clearSubscription();
    }

    public String getMsisdn() {
        SharedPreferences sharedPref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        return sharedPref.getString("msisdn", "8800000000000");
    }

    public String getMdn(){
        String msisdnValue = getMsisdn();
        return msisdnValue.substring(3,5);
    }

    public void writeSubscriptionStatus(int trackId, Subscription subscription) {
        Gson gson = new GsonBuilder().create();
        SharedPreferences sharedPref = context.getSharedPreferences("SUBS_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(trackId), gson.toJson(subscription));
        editor.commit();
    }

    public boolean isSubscribed(int contentId) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences("SUBS_INFO", Context.MODE_PRIVATE);
            String value = sharedPref.getString(String.valueOf(contentId), "{}");
            JSONObject jsonObject = new JSONObject(value);
            if (getMdn().equals("17")) {
                return (System.currentTimeMillis() <= Long.parseLong(jsonObject.optString("expiry_date")));
            } else {
                Map<String, ?> allEntries = sharedPref.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    value = sharedPref.getString(entry.getKey(), "{}");
                    break;
                }
                jsonObject = new JSONObject(value);
                return (System.currentTimeMillis() <= Long.parseLong(jsonObject.optString("expiry_date")));
            }
        }
        catch (Exception ex){
            logger(ex.toString());
            return false;
        }
    }

    public String getPrice(int trackId) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences("SUBS_INFO", Context.MODE_PRIVATE);
            String value = sharedPref.getString(String.valueOf(trackId), "{}");
            JSONObject jsonObject = new JSONObject(value);
            String price = jsonObject.optString("price").length()==0?"1.22":jsonObject.optString("price");
            return getLangauge().equals("bn")?convertToBangle(price):price;
        }
        catch (Exception ex){
            logger(ex.toString());
            return "0.00";
        }
    }

    public Bkash getBkashSubscription() {
        SharedPreferences sharedPref = context.getSharedPreferences("BKASH", Context.MODE_PRIVATE);
        Bkash bkash = new Bkash();
        bkash.setMsisdn(sharedPref.getString("msisdn",""));
        bkash.setStatus(sharedPref.getString("status",""));
        bkash.setExpireTime(sharedPref.getString("expiry","0"));
        bkash.setUrl(sharedPref.getString("url",""));
        return bkash;
    }

    public void setBkashSubscription(Bkash bkash) {
        SharedPreferences sharedPref = context.getSharedPreferences("BKASH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("msisdn", bkash.getMsisdn());
        editor.putString("status", bkash.getStatus());
        editor.putString("expiry", bkash.getExpireTime());
        editor.putString("url", bkash.getUrl());
        editor.commit();
    }

    public void makeSubscriptionDialog(final boolean showInterface) {
        try {
            final Dialog dialog = new Dialog(context);
            HashMap<String, Integer> screenRes = getScreenRes();
            int width = screenRes.get(KeyWord.SCREEN_WIDTH);
            int height = screenRes.get(KeyWord.SCREEN_HEIGHT);
            int mywidth = (width / 10) * 8;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.number_layout);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LinearLayout numberLayout = (LinearLayout) dialog.findViewById(R.id.number_layout);
            TextView title = (TextView) dialog.findViewById(R.id.subscription_title);
            final EditText phoneNumber = (EditText) dialog.findViewById(R.id.phone_number);
            Button cancelBtn = (Button) dialog.findViewById(R.id.rating_btn_cancel);
            Button submitBtn = (Button) dialog.findViewById(R.id.rating_btn_submit);
            ViewGroup.LayoutParams params = numberLayout.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.width = mywidth;
            numberLayout.setLayoutParams(params);
            setFonts(new View[]{title, cancelBtn, submitBtn});
            title.setText(getLangauge().equals("bn") ? context.getString(R.string.bkash_text_bn) : context.getString(R.string.bkash_text_en));
            cancelBtn.setText(getLangauge().equals("bn") ? context.getString(R.string.number_cancel_btn_bn) : context.getString(R.string.number_cancel_btn_en));
            submitBtn.setText(getLangauge().equals("bn") ? context.getString(R.string.number_submit_btn_bn) : context.getString(R.string.number_submit_btn_en));
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    String msisdn = "8801" + phoneNumber.getText().toString();
                    String message = validateMsisdn(msisdn);
                    if (message.equals("OK")) {
                        Bkash bkash = new Bkash();
                        bkash.setMsisdn(msisdn);
                        bkash.setStatus("unsubscribed");
                        bkash.setExpireTime(String.valueOf(System.currentTimeMillis()));
                        bkash.setUrl("");
                        setBkashSubscription(bkash);
                        if(showInterface)
                            subscriptionInterfacce.numberSet();
                        //validatePinDialog(msisdn, showInterface);
                    } else {
                        showToast(message);
                    }
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception ex) {
            showToast(ex.toString());
        }
    }

    public String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd/MM/yyyy hh:mm", cal).toString();
        return date;
    }

}

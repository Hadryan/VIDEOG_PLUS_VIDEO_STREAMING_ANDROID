package com.gtechnologies.videogplus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.R;

import java.util.HashMap;


public class Splash extends AppCompatActivity {
    Utility utility = new Utility(this);
    //private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        utility.setFullScreen();
        setContentView(R.layout.splash_layout);
        utility.getNetworkInfo();
        HashMap<String, String> map = utility.getDeviceInfo();
        for(String key: map.keySet()){
            utility.logger(key+" -> "+map.get(key));
        }

//        newJson = new JSONTask();
//        highlightJson= new JSONTask();
//        catJson = new JSONTask();
//
//        new FinalJSONTask().execute();


        try {
            //MyApplication.getInstance().trackScreenView("Splash Screen");
            gotoMain(2500);
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
//                if (ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
//                        && ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, Manifest.permission.RECEIVE_SMS)
//                            && ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, Manifest.permission.READ_SMS)) {
//                        ActivityCompat.requestPermissions(
//                                Splash.this,
//                                new String[]{
//                                        Manifest.permission.RECEIVE_SMS,
//                                        Manifest.permission.READ_SMS
//                                },
//                                100);
//                        //utility.logger("This app requires Storage Permission as it will download clips for offline feature");
//                    } else {
//                        ActivityCompat.requestPermissions(
//                                Splash.this,
//                                new String[]{
//                                        Manifest.permission.RECEIVE_SMS,
//                                        Manifest.permission.READ_SMS
//                                },
//                                100);
//                    }
//                } else {
//                    gotoMain(6000);
//                }
//            } else {
//                gotoMain(6000);
//            }
        }
        catch (Exception ex){
            //utility.call_error(ex);
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        try {
//            switch (requestCode) {
//                case 100: {
//                    if (grantResults.length > 0
//                            && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                        gotoMain(0);
//                    } else {
//                        showDialog();
//                    }
//                    return;
//                }
//            }
//        }
//        catch (Exception ex){
//            //utility.call_error(ex);
//        }
//    }

//    private void showDialog() {
//        try {
//            Display display = getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            int width = size.x;
//            int height = size.y;
//            int mywidth = (width / 10) * 8;
//            final Dialog dialog = new Dialog(this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.setContentView(R.layout.permission_layout);
//            Button yes = (Button) dialog.findViewById(R.id.dialog_yes);
//            Button no = (Button) dialog.findViewById(R.id.dialog_no);
//            LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_layout_size);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
//            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            params.width = mywidth;
//            ll.setLayoutParams(params);
//            yes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
//                        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
//                        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivityForResult(myAppSettings, 101);
//                        finish();
//                        System.exit(0);
//                    }
//                    catch (Exception ex){
//                        utility.showToast(ex.toString());
//                        finish();
//                    }
//                }
//            });
//            no.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                    System.exit(0);
//                }
//            });
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//        catch (Exception ex){
//            //utility.call_error(ex);
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            if (requestCode == 101) {
//                if (ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
//                        && ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
//                    utility.showToast("Permission Granted");
//                    //gotoMain();
//                }
//            }
//        }
//        catch (Exception ex){
//            //utility.call_error(ex);
//        }
//    }

    private void gotoMain(final long waitTime) {
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(waitTime);
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {

                }
            }
        };
        background.start();
    }


}

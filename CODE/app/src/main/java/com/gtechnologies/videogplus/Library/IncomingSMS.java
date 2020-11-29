package com.gtechnologies.videogplus.Library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Hp on 9/13/2017.
 */

public class IncomingSMS extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try{
            if(bundle != null){
                final Object[] pdusObj = (Object[])bundle.get("pdus");
                for(int i=0; i<pdusObj.length; i++){
                    SmsMessage currentMessage = getIncomingMessage(pdusObj[i],bundle);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();
                    try
                    {
                        if (senderNum .equals("+8801923853388"))
                        {
                            //utility.setPin(message);
                        }
                    }
                    catch(Exception e){
                        Log.d("Radio", e.toString());
                    }
                }
            }
        }
        catch (Exception ex){
            // do nothing
        }
    }

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
}

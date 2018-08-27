package com.rajsuvariya.smsscrapping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by @raj on 27/08/18.
 */
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            String phoneNumber = smsMessage.getDisplayOriginatingAddress();
            String senderNum = phoneNumber;
            String messageBody = smsMessage.getMessageBody();

            Log.d("newSMS", messageBody);
            Toast.makeText(context, messageBody, Toast.LENGTH_LONG).show();
        }
    }
}

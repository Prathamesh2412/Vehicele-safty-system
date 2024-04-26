package com.example.vehicle_accident_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsListener extends BroadcastReceiver {
    String number,msg="",det="",string1="",string2="";


    ArrayList num=new ArrayList();
    ArrayList name=new ArrayList();
    AudioManager audioManager;
    TelephonyManager telephonyManager;
    int kk=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){

            //   File pictureFileDir =   new File(Environment.getExternalStorageDirectory(), "A");  // getDir();
///
            //    String numbers="";

            // Check if the SMS matches our SOS message
            SmsMessage[] messages = getMessagesFromIntent(intent);
            if(messages != null){
                for(int i = 0; i < messages.length; i++){
                    SmsMessage message = messages[i];

                    number=message.getOriginatingAddress();
                    msg=message.getDisplayMessageBody();
                    Log.d("incoming SMS", number+"----"+msg);
                    Toast.makeText(context, number+"----"+msg, Toast.LENGTH_LONG).show();
                    control.callflag=false;
                    control.smsflag=true;
                    control.mflag=false;
                    control.sms_msg=msg;
                    control.sms_num=number;

//                    FaceTrackerActivity.rnum=number;
//
//                    FaceTrackerActivity.callFlag=true;
//                    Intent j=new Intent(context,FaceTrackerActivity.class);
//                    j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    j.putExtra(EXTRA_DEVICE_ADDRESS, address);
//                    context.startActivity(j);




                }
            }
        }
        else {
            try {
                System.out.println("Receiver start");
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("incoming call",state);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    Toast.makeText(context, "Incoming Call State", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_SHORT).show();
                    audioManager.setStreamMute(AudioManager.STREAM_RING, true);
//                    MainActivity.rnum = incomingNumber;
//
//                    MainActivity.callFlag = true;
//                    Intent j = new Intent(context, MainActivity.class);
//                    j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(j);

                }
                if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                    Toast.makeText(context, "Call Received State", Toast.LENGTH_SHORT).show();
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Toast.makeText(context, "Call Idle State", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }












    private SmsMessage[] getMessagesFromIntent(Intent intent) {
        SmsMessage retMsgs[] = null;
        Bundle bdl = intent.getExtras();
        try {
            Object pdus[] = (Object[]) bdl.get("pdus");
            retMsgs = new SmsMessage[pdus.length];
            for (int n = 0; n < pdus.length; n++) {
                byte[] byteData = (byte[]) pdus[n];
                retMsgs[n] = SmsMessage.createFromPdu(byteData);
            }
        } catch (Exception e) {

        }
        return retMsgs;
    }

}

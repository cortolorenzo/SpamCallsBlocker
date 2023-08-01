package com.example.spamcallsblocker;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Date;

public class CallReceiver extends PhonecallReceiver {


    public CallReceiver() {

    }

    Context context;
    String beginNum;

    public CallReceiver(Context context, String beginNumber) {
        this.context = context;
        beginNum = beginNumber;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start) {

        PrefsManager prefs = new PrefsManager(ctx);
        beginNum = prefs.getString("number", "");



        if (NumberVerifier.checkNumberExistsInArray(Common.splitByComma(beginNum), number) ){
            super.dismissIncomingCall(ctx);
            Toast.makeText(ctx, number + " blocked!", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {

    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {

    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {

    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {

    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {

    }
}

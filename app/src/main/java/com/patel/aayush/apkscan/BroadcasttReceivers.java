package com.patel.aayush.apkscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aayush on 30-11-2017.
 */

public class BroadcasttReceivers extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            Log.e(" BroadcastReceiver ", "onReceive called "
                    + " PACKAGE_REMOVED ");
        }
        else if (intent.getAction().equals(
                "android.intent.action.PACKAGE_ADDED")) {

            Log.e(" BroadcastReceiver ", "onReceive called " + "PACKAGE_ADDED");
            Intent startIntent = context
                    .getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName());
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // getDataString() method will give the package name of the recently application installed.
            startIntent.putExtra("message", intent.getDataString().substring(8));
            startIntent.setFlags(
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT |
                            Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            );
            context.startActivity(startIntent);
        }
    }
}

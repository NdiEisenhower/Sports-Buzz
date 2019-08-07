package com.mmbadi.sportsbuzz.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NetworkReceiver extends BroadcastReceiver {
    public static final String NETWORK_TYPE = "NETWORK_TYPE";
    public static final int TYPE_NO_NETWORK = -1;
    public static final String NETWORK_LISTENER = "NETWORK_LISTENER";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Intent networkIntent = new Intent();
        networkIntent.setAction(NETWORK_LISTENER);
        if (isConnected) {
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    networkIntent.putExtra(NETWORK_TYPE, ConnectivityManager.TYPE_WIFI);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    networkIntent.putExtra(NETWORK_TYPE, ConnectivityManager.TYPE_MOBILE);
                    break;
            }
        } else {
            networkIntent.putExtra(NETWORK_TYPE, TYPE_NO_NETWORK);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkIntent);
    }
}
package com.example.rompermission.requester.impl.emui;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.UriMatcher;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;

public class TestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }, new IntentFilter("vivo.intent.action.CHECK_ALERT_WINDOW"));
//        new Handler().post()

//        ContentProvider provider;
//        provider.query()

        UriMatcher uriMatcher = new UriMatcher(-1);

        uriMatcher.addURI("com.vivo.permissionmanager.provider.permission", "float_window_apps", 3);
        uriMatcher.addURI("com.vivo.permissionmanager.provider.permission", "float_window_apps".concat("/#"), 4);

        return null;
    }
}

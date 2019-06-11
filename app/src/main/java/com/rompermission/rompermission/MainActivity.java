package com.rompermission.rompermission;

import android.Manifest.permission;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.rompermission.util.RomUtil;
import com.rompermission.PermissionCallback;
import com.rompermission.RomPermission;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * @author wuxi
 * @since 2018/7/12
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RomUtil.INSTANCE.getPhoneInfo();
    }

    public void onCheckOnly(View view) {
        if (RomPermission.check(this, new String[]{permission.CAMERA})) {
            Log.e("onCheckOnly", "success____233333333");
        } else {
            Log.e("onCheckOnly", "failure____233333333");
        }
    }

    public void onRequest(View view) {
        RomPermission.checkAndRequest(this, new String[]{permission.CAMERA}, R.string.error_permission, new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean success) {
                if (success) {
                    Log.e("onRequest", "success____233333333");
//                    installMeizuApk();
                } else {
                    Log.e("onRequest", "failure____233333333");
                }
                return null;
            }
        });
    }

}

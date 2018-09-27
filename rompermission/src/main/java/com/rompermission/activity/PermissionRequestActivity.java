package com.rompermission.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.rompermission.PermissionCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yuansui
 * @since 2018/6/6
 */
public class PermissionRequestActivity extends Activity {

    private static final String PERMISSION_KEY = "permission_key";
    private final int PERMISSION_CODE = 10000;

    private String[] mPermissions;
    private static PermissionCallback mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissions = getIntent().getStringArrayExtra(PERMISSION_KEY);
        if (mPermissions == null || mPermissions.length == 0) {
            finish();
            return;
        }
        requestPermission();
    }

    private void requestPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        startActivityForResult(intent, PERMISSION_CODE);

//        List<String> uncheckPermissions = new ArrayList<>();
//        for (String p : mPermissions) {
//            //权限检测如果未允许，添加至待申请列表
//            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
//                uncheckPermissions.add(p);
//            }
//        }
//
//        if (!uncheckPermissions.isEmpty()) {
//            String[] strings = new String[uncheckPermissions.size()];
//            uncheckPermissions.toArray(strings);
//            ActivityCompat.requestPermissions(this, strings, PERMISSION_CODE);
//        } else {
//            mCallback.onResult(true);
//            finish();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            boolean denied = false;
            for (int result : grantResults) {
                // 只要有一个是不通过的, 这一批申请都当做不通过处理
                if (result != PackageManager.PERMISSION_GRANTED) {
                    denied = true;
                    break;
                }
            }

            if (denied) {
                if (mCallback != null) {
                    mCallback.onResult(false);
                }
            } else {
                if (mCallback != null) {
                    mCallback.onResult(true);
                }
            }
        }

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERMISSION_CODE) {
            if (resultCode == RESULT_OK) {
                if (mCallback != null) {
                    mCallback.onResult(true);
                }
            } else {
                if (mCallback != null) {
                    mCallback.onResult(false);
                }
            }
        }

        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public static void permissionRequest(Context context, String[] permissions, PermissionCallback callback) {
        mCallback = callback;

        Intent i = new Intent(context, PermissionRequestActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putStringArray(PERMISSION_KEY, permissions);
        i.putExtras(b);
        context.startActivity(i);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    public static void clearCallback() {
        mCallback = null;
    }
}

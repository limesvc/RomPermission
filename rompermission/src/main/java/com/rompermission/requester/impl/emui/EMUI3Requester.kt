package com.rompermission.requester.impl.emui

import android.content.ComponentName

class EMUI3Requester : EMUIRequester() {
    override fun getComponentName(): ComponentName {
        return ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity")
    }
}
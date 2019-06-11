package com.rompermission.requester

import com.rompermission.PermissionCallback

interface IRomPermissionRequester {
    fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int, callback: PermissionCallback?): Boolean

    fun checkAndRequest(host: Any, permissions: Array<String>, message: String?, callback: PermissionCallback?): Boolean

    fun check(host: Any, permissions: Array<String>): Boolean
}
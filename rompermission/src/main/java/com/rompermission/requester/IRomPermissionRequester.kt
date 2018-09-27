package com.example.rompermission.requester

import com.example.rompermission.PermissionCallback

interface IRomPermissionRequester {
    fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int, callback: PermissionCallback?): Boolean

    fun checkAndRequest(host: Any, permissions: Array<String>, message: String, callback: PermissionCallback?): Boolean

    fun check(host: Any, permissions: Array<String>): Boolean
}
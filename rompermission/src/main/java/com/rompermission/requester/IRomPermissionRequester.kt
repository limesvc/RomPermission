package com.rompermission.requester

import com.rompermission.PermissionCallback
import com.rompermission.result.Permission
import java.util.concurrent.BlockingQueue

interface IRomPermissionRequester {
    fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int, callback: PermissionCallback?): Boolean

    fun checkAndRequest(host: Any, permissions: Array<String>, message: String?, callback: PermissionCallback?): Boolean

    fun checkAndRequest(host: Any, permissions: Array<Permission>, queue: BlockingQueue<Boolean>, callback: (Array<Permission>) -> Unit)

    fun check(host: Any, permissions: Array<String>): Boolean
}
package com.rompermission

import com.rompermission.factory.PermissionRequesterFactory
import com.rompermission.result.Permission
import com.rompermission.util.RomUtil
import kotlinx.coroutines.*

object RomPermission {
    @JvmStatic
    fun check(host: Any, permissions: Array<Permission>) {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.getPermissionRequester(romInfo)
        requester?.check(host, permissions)
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<Permission>, block: (Array<Permission>) -> Unit) {
        GlobalScope.launch {
            val requester = PermissionRequesterFactory.getPermissionRequester(RomUtil.romInfo)
            requester?.checkAndRequest(host, permissions) {
                block.invoke(it)
            }
        }
    }

    @JvmStatic
    fun checkAndRequestAsync(host: Any, permissions: Array<Permission>): Deferred<Array<Permission>> {
        val deferred = CompletableDeferred<Array<Permission>>()
        return GlobalScope.async {
            val requester = PermissionRequesterFactory.getPermissionRequester(RomUtil.romInfo)
            requester?.checkAndRequest(host, permissions) {
                deferred.complete(permissions)
            }
            deferred.await()
        }
    }
}
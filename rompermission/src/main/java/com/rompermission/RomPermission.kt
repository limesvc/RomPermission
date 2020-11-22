package com.rompermission

import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import com.rompermission.ext.getContext
import com.rompermission.factory.PermissionRequesterFactory
import com.rompermission.result.Permission
import com.rompermission.util.RomUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.coroutines.EmptyCoroutineContext

object RomPermission {
    @JvmStatic
    fun check(host: Any, permissions: Array<String>): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.getPermissionRequester(romInfo)
        return requester?.check(host, permissions) ?: false
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<String>, messageResId: Int = 0, block: ((success: Boolean) -> Unit)?): Boolean {
        val message = if (messageResId != 0) host.getContext()?.getString(messageResId) else ""
        return checkAndRequest(host, permissions, message, block)
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<String>, message: String?, block: ((success: Boolean) -> Unit)?): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.getPermissionRequester(romInfo)
        var callback: PermissionCallback? = null
        if (block != null) {
            callback = object : PermissionCallback {
                override fun onResult(success: Boolean) {
                    block(success)
                }
            }
        }
        return requester?.checkAndRequest(host, permissions, message, callback) ?: false
    }

    @JvmStatic
    fun checkAndRequestAsync(host: Any, permissions: Array<Permission>): Deferred<Array<Permission>> {
        val blockingQueue = LinkedBlockingQueue<Boolean>()
        val deferred = CompletableDeferred<Array<Permission>>()
        return GlobalScope.async {
            val requester = PermissionRequesterFactory.getPermissionRequester(RomUtil.romInfo)
            requester?.checkAndRequest(host, permissions, blockingQueue) {
                deferred.complete(permissions)
            }
            deferred.await()
        }
    }

    @JvmStatic
    fun checkAndRequest(host: Any, permissions: Array<Permission>): Array<Permission> {
        val blockingQueue = LinkedBlockingQueue<Boolean>()

        val deferred = CompletableDeferred<Array<Permission>>()
        Looper.getMainLooper().queue

//        Log.e("2333333333333333", "blockingQueue.take start")
//        val signal = blockingQueue.take()
//        Log.e("2333333333333333", "blockingQueue.take end $signal")
//
//        sequenceOf(GlobalScope.launch {
//            val requester = PermissionRequesterFactory.getPermissionRequester(RomUtil.romInfo)
//            requester?.checkAndRequest(host, permissions, blockingQueue) {
//                deferred.complete(permissions)
//            }
//        }, runBlocking {
//
//        })

        return runBlocking(deferred) {
//            val requester = PermissionRequesterFactory.getPermissionRequester(RomUtil.romInfo)
//            requester?.checkAndRequest(host, permissions) {
//                deferred.complete(permissions)
//            }
            Log.e("2333333333333333", "runBlocking")
            delay(5000)
            deferred.await()
        }
    }
}
package com.rompermission

import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.suspendCoroutine

@ObsoleteCoroutinesApi
class Test {
    val TAG = "23333333"

    fun testSuspend() {
//        suspendCoroutine<String> { "" }
//        var deferred = Deferred()
//        deferred.
        GlobalScope.launch {
            val arg1 = sunpendF1()
            val arg2 = sunpendF2()
            doLog("suspend finish arg1:$arg1  arg2:$arg2  result:${arg1 + arg2}")
        }

        runBlocking {

        }
    }

    private suspend fun sunpendF1(): Int {
        delay(1000)
        doLog("suspend fun 1")
        return 2
    }

    private suspend fun sunpendF2(): Int {
        delay(500)
        doLog("suspend fun 2")
        return 4
    }

    private fun doLog(text: String){
        Log.e(TAG, text)
    }

//    @Suppress("EXPERIMENTAL_API_USAGE")
    fun testActor(){
        val actor = GlobalScope.actor<String>(Dispatchers.Main) {
            for (s in channel) {
                doLog(s)
            }
        }

    val offer = actor.offer("1111111111")
    doLog("23333333333333 $offer")
    }

    private fun testLaunch()  {

        GlobalScope.actor<View> {
            for (view in channel) {

            }
        }
        val job = GlobalScope.launch(Dispatchers.IO) {
            delay(6000)
            Log.e(TAG, "协程执行结束 -- 线程id：${Thread.currentThread().id}")
        }
        Log.e(TAG, "主线程执行结束")
    }

    private fun testAsync(){

//        val deferred = GlobalScope.async(Dispatchers.IO) {
//            try {
//                call.execute() //已经在io线程中了，所以调用Retrofit的同步方法
//            } catch (e: ConnectException) {
//                coroutine.onFail?.invoke("网络连接出错", -1)
//                null
//            } catch (e: IOException) {
//                coroutine.onFail?.invoke("未知网络错误", -1)
//                null
//            }
//        }
    }
}
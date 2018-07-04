package com.example.rompermission.requester

interface IRomPermissionRequester {
    fun checkAndRequest(host: Any, permissions: Array<String>, requestCode: Int): Boolean

    fun check(host: Any, permissions: Array<String>): Boolean
}
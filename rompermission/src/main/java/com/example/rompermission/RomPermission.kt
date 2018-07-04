package com.example.rompermission

import com.example.rompermission.factory.PermissionRequesterFactory
import com.example.rompermission.util.RomUtil

object RomPermission {
    fun checkAndRequest(host: Any, permissions: Array<String>, resultCode: Int): Boolean {
        val romInfo = RomUtil.romInfo
        val requester = PermissionRequesterFactory.instance.getPermissionRequester(romInfo)
        return requester!!.checkAndRequest(host, permissions, resultCode)
    }
}

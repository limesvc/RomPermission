package com.rompermission.requester

import com.rompermission.result.Permission

interface IRomPermissionRequester {
    fun check(host: Any, permissions: Array<Permission>)

    fun checkAndRequest(host: Any, permissions: Array<Permission>, callback: (Array<Permission>) -> Unit)
}
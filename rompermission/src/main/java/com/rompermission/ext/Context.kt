package com.rompermission.ext

import android.content.Context

/**
 * @author wuxi
 * @since 2019/6/11
 */

fun Any.getContext(): Context? {
    return when {
        this is android.support.v4.app.Fragment -> this.requireContext()
        this is android.app.Fragment -> activity
        this is Context -> this
        else -> null
    }
}
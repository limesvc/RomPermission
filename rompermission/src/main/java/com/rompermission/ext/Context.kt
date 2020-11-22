package com.rompermission.ext

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * @author wuxi
 * @since 2019/6/11
 */

fun Any.getContext(): Context? {
    return when {
        this is Fragment -> this.requireContext()
        this is android.app.Fragment -> activity
        this is Context -> this
        else -> null
    }
}
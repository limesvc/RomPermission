package com.rompermission.result

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.StringRes

class Permission(val permission: String, private val message: String = "", @StringRes private val messageId: Int = 0) {
    var permitted: Boolean = false

    fun toastError(context: Context) {
        val tips = if (message.isNotEmpty()) message else if (messageId != 0) context.getString(messageId) else message
        if (tips.isNotEmpty()) {
            Toast.makeText(context, tips, Toast.LENGTH_LONG).show()
        }
    }
}
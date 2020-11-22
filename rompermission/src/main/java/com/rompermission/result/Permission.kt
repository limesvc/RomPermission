package com.rompermission.result

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.StringRes

class Permission(val permission: String, val message: String = "", @StringRes val messageId: Int = 0) : Parcelable {
    var permitted: Boolean = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
        permitted = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(permission)
        parcel.writeString(message)
        parcel.writeInt(messageId)
        parcel.writeByte(if (permitted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Permission> {
        override fun createFromParcel(parcel: Parcel): Permission {
            return Permission(parcel)
        }

        override fun newArray(size: Int): Array<Permission?> {
            return arrayOfNulls(size)
        }
    }

    fun toastError(context: Context) {
        val tips = if (message.isNotEmpty()) message else if (messageId != 0) context.getString(messageId) else message
        if (tips.isNotEmpty()) {
            Toast.makeText(context, tips, Toast.LENGTH_LONG).show()
        }
    }
}
package com.rompermission.factory

import com.example.rompermission.requester.impl.DefaultRequester
import java.lang.Exception

abstract class RomRequesterFactory {
    abstract fun getRequester(romVersion: String): DefaultRequester

    /**
     * 作用域[low, high)
     */
    protected fun isBetween(low: String, high: String, now: String): Boolean {
        try {
            return compare(now, low) and !compare(now, high)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * origin >= target -> true
     * origin < target -> false
     */
    private fun compare(origin: String, target: String): Boolean {
        val originArray = getStringArray(origin)
        val targetArray = getStringArray(target)

        for (i in 0 until originArray.size) {
            if (targetArray.size > i && originArray[i] > targetArray[i]) {
                return true
            } else if (targetArray.size > i && originArray[i] < targetArray[i]) {
                return false
            }
        }
        //正好相等
        return true
    }

    private fun getStringArray(str: String): List<Int> {
        val array = mutableListOf<Int>()

        val builder = StringBuilder()
        for (char in str) {
            if (char.isDigit()) {
                builder.append(char)
            } else {
                if (builder.isNotEmpty()) {
                    array.add(Integer.parseInt(builder.toString()))
                    builder.setLength(0)
                }
            }
        }
        if (builder.isNotEmpty()) {
            array.add(Integer.parseInt(builder.toString()))
            builder.setLength(0)
        }

        return array
    }
}

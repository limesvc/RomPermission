package com.example.rompermission.util

import android.os.Environment
import android.util.Log

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.util.Enumeration
import java.util.Properties

class BuildProperties @Throws(IOException::class)
private constructor() {

    private val TAG = "BuildProperties"
    private val properties: Properties

    val isEmpty: Boolean
        get() = properties.isEmpty

    init {
        properties = Properties()
        // 读取系统配置信息build.prop类
        try {
            properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Can not load BuildProperties!!!")
        }
    }

    fun containsKey(key: Any): Boolean {
        return properties.containsKey(key)
    }

    fun containsValue(value: Any): Boolean {
        return properties.containsValue(value)
    }

    fun entrySet(): Set<Map.Entry<Any, Any>> {
        return properties.entries
    }

    fun getProperty(name: String): String {
        return properties.getProperty(name)
    }

    fun getProperty(name: String, defaultValue: String): String {
        return properties.getProperty(name, defaultValue)
    }

    fun keys(): Enumeration<Any> {
        return properties.keys()
    }

    fun keySet(): Set<Any> {
        return properties.keys
    }

    fun size(): Int {
        return properties.size
    }

    fun values(): Collection<Any> {
        return properties.values
    }

    companion object {

        @Throws(IOException::class)
        fun newInstance(): BuildProperties {
            return BuildProperties()
        }
    }
}
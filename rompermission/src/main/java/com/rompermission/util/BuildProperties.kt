package com.rompermission.util

import android.os.Environment
import android.util.Log
import java.io.*

import java.lang.Exception
import java.util.Enumeration
import java.util.Properties

class BuildProperties @Throws(IOException::class)
private constructor() {

    private val TAG = "BuildProperties"
    private val properties: Properties = Properties()
    private var loadFail = false

    val isEmpty: Boolean
        get() = properties.isEmpty

    init {
        // 读取系统配置信息build.prop类
        try {
            properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
        } catch (e: Exception) {
            loadFail = true
            Log.e(TAG, "Can not load BuildProperties!!!")
        }
    }

    fun containsKey(key: String): Boolean {
        if (loadFail) {
            return getSystemProperty(key).isNotEmpty()
        }
        return properties.containsKey(key)
    }

    fun containsValue(value: Any): Boolean {
        return properties.containsValue(value)
    }

    fun entrySet(): Set<Map.Entry<Any, Any>> {
        return properties.entries
    }

    fun getProperty(name: String): String {
        if (loadFail) {
            return getSystemProperty(name)
        }
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

    fun getSystemProperty(propName: String): String {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            Log.e("BuildProperties", "Unable to read sysprop $propName", ex)
            return ""
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    Log.e("BuildProperties", "Exception while closing InputStream", e)
                }

            }
        }
        return line
    }
}
package com.rompermission.util

import android.text.TextUtils
import android.util.Log

import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception


object RomUtil {
    //标识参考：https://blog.csdn.net/iblue007/article/details/78481533

    //MIUI标识
    private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

    //EMUI标识
    private val KEY_EMUI_VERSION_CODE = "ro.build.version.emui"
    private val KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level"
    private val KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion"

    //Flyme标识
    private val KEY_FLYME_ID_FALG_KEY = "ro.build.display.id"
    private val KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme"
    private val KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon"
    private val KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme"
    private val KEY_FLYME_PUBLISH_FALG = "ro.flyme.published"

    private val KEY_VIVO_OS_VERSION = "ro.vivo.os.version"
    private val KEY_OPPO_OS_VERSION = "ro.build.version.opporom"

    val isFlyme: Boolean
        get() = isPropertiesExist(KEY_FLYME_ICON_FALG, KEY_FLYME_SETUP_FALG, KEY_FLYME_PUBLISH_FALG)

    val isEMUI: Boolean
        get() = isPropertiesExist(KEY_EMUI_VERSION_CODE, KEY_EMUI_API_LEVEL, KEY_EMUI_CONFIG_HW_SYS_VERSION)

    val isMIUI: Boolean
        get() = isPropertiesExist(KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME, KEY_MIUI_INTERNAL_STORAGE)

    val romType: RomType
        get() {
            val romType = RomType.OTHER
            try {
                val buildProperties = BuildProperties.newInstance()

                if (buildProperties.containsKey(KEY_EMUI_VERSION_CODE) || buildProperties.containsKey(KEY_EMUI_API_LEVEL) || buildProperties.containsKey(KEY_EMUI_CONFIG_HW_SYS_VERSION)) {
                    return RomType.EMUI
                }
                if (buildProperties.containsKey(KEY_MIUI_VERSION_CODE) || buildProperties.containsKey(KEY_MIUI_VERSION_NAME) || buildProperties.containsKey(KEY_MIUI_INTERNAL_STORAGE)) {
                    return RomType.MIUI
                }
                if (buildProperties.containsKey(KEY_FLYME_ICON_FALG) || buildProperties.containsKey(KEY_FLYME_SETUP_FALG) || buildProperties.containsKey(KEY_FLYME_PUBLISH_FALG)) {
                    return RomType.FLYME
                }
                if (buildProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                    val romName = buildProperties.getProperty(KEY_FLYME_ID_FALG_KEY)
                    if (!TextUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                        return RomType.FLYME
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return romType
        }

    val romInfo: RomInfo
        get() {
            val info = RomInfo()
            info.romType = RomType.OTHER
            try {
                val buildProperties = BuildProperties.newInstance()

                if (buildProperties.containsKey(KEY_EMUI_VERSION_CODE) || buildProperties.containsKey(KEY_EMUI_API_LEVEL) || buildProperties.containsKey(KEY_EMUI_CONFIG_HW_SYS_VERSION)) {
                    info.romType = RomType.EMUI
                    info.version = getPropertyValue(buildProperties, KEY_EMUI_VERSION_CODE)
                }
                if (buildProperties.containsKey(KEY_MIUI_VERSION_CODE) || buildProperties.containsKey(KEY_MIUI_VERSION_NAME) || buildProperties.containsKey(KEY_MIUI_INTERNAL_STORAGE)) {
                    info.romType = RomType.MIUI
                    info.version = getPropertyValue(buildProperties, KEY_MIUI_VERSION_NAME)
                }
                if (buildProperties.containsKey(KEY_FLYME_ICON_FALG) || buildProperties.containsKey(KEY_FLYME_SETUP_FALG) || buildProperties.containsKey(KEY_FLYME_PUBLISH_FALG)) {
                    info.romType = RomType.FLYME
                    info.version = getPropertyValue(buildProperties, KEY_FLYME_ID_FALG_KEY)
                }
                if (buildProperties.containsKey(KEY_VIVO_OS_VERSION)) {
                    info.romType = RomType.VIVO
                    info.version = getPropertyValue(buildProperties, KEY_VIVO_OS_VERSION)
                }
                if (buildProperties.containsKey(KEY_OPPO_OS_VERSION)) {
                    info.romType = RomType.OPPO
                    info.version = getPropertyValue(buildProperties, KEY_OPPO_OS_VERSION)
                }

                if (buildProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                    val romName = buildProperties.getProperty(KEY_FLYME_ID_FALG_KEY)
                    if (!TextUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                        info.romType = RomType.FLYME
                        info.version = getPropertyValue(buildProperties, KEY_FLYME_ID_FALG_KEY)
                    }
                }

//                var versionCode = getSystemProperty(KEY_VIVO_OS_VERSION)
//                if (!versionCode.isEmpty()) {
//                    info.version = versionCode
//                    info.romType = RomType.VIVO
//                }
//
//                versionCode = getSystemProperty(KEY_OPPO_OS_VERSION)
//                if (!versionCode.isEmpty()) {
//                    info.version = versionCode
//                    info.romType = RomType.OPPO
//                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return info
        }

    fun getPhoneInfo() {
        //BOARD 主板
        var phoneInfo = "BOARD: " + android.os.Build.BOARD
        phoneInfo += ", BOOTLOADER: " + android.os.Build.BOOTLOADER
        //BRAND 运营商
        phoneInfo += ", BRAND: " + android.os.Build.BRAND
        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI
        phoneInfo += ", CPU_ABI2: " + android.os.Build.CPU_ABI2
        //DEVICE 驱动
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE
        //DISPLAY Rom的名字 例如 Flyme 1.1.2（魅族rom） &nbsp;JWR66V（Android nexus系列原生4.3rom）
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY
        //指纹
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT
        //HARDWARE 硬件
        phoneInfo += ", HARDWARE: " + android.os.Build.HARDWARE
        phoneInfo += ", HOST: " + android.os.Build.HOST
        phoneInfo += ", ID: " + android.os.Build.ID
        //MANUFACTURER 生产厂家
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER
        //MODEL 机型
        phoneInfo += ", MODEL: " + android.os.Build.MODEL
        phoneInfo += ", PRODUCT: " + android.os.Build.PRODUCT
        phoneInfo += ", RADIO: " + android.os.Build.RADIO
        phoneInfo += ", RADITAGSO: " + android.os.Build.TAGS
        phoneInfo += ", TIME: " + android.os.Build.TIME
        phoneInfo += ", TYPE: " + android.os.Build.TYPE
        phoneInfo += ", USER: " + android.os.Build.USER
        //VERSION.RELEASE 固件版本
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE
        phoneInfo += ", VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME
        //VERSION.INCREMENTAL 基带版本
        phoneInfo += ", VERSION.INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL
        //VERSION.SDK SDK版本
        phoneInfo += ", VERSION.SDK: " + android.os.Build.VERSION.SDK
        phoneInfo += ", VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT

        Log.e("phone", phoneInfo)
    }

    private fun isPropertiesExist(vararg keys: String): Boolean {
        if (keys.isEmpty()) {
            return false
        }
        try {
            val properties = BuildProperties.newInstance()
            for (key in keys) {
                val value = properties.getProperty(key)
                if (value != null)
                    return true
            }
            return false
        } catch (e: IOException) {
            return false
        }

    }

    private fun getPropertyValue(buildProperties: BuildProperties, propertyKey: String): String {
        try {
            return buildProperties.getProperty(propertyKey)
        } catch (e: IOException) {
            return ""
        }
    }
}
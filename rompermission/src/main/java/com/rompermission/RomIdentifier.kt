package com.rompermission

import com.example.rompermission.util.RomInfo
import com.example.rompermission.util.RomType

object RomIdentifier {
    /**
     * 同系列系统不同版本之前可能存在差异
     * @return Rom版本
     */
    val romInfo: RomInfo
        get() {
            val info = RomInfo()
            info.romType = RomType.OTHER
            return info
        }
}

package com.example.rompermission.factory

import com.example.rompermission.factory.impl.EMUIRequesterFactory
import com.example.rompermission.factory.impl.FlymeRequesterFactory
import com.example.rompermission.factory.impl.MIUIRequesterFactory
import com.example.rompermission.factory.impl.VIVORequesterFactory
import com.example.rompermission.requester.IRomPermissionRequester
import com.example.rompermission.requester.impl.DefaultRequester
import com.example.rompermission.requester.impl.vivo.VIVO4Requester
import com.example.rompermission.util.RomInfo
import com.example.rompermission.util.RomType

class PermissionRequesterFactory private constructor() {

    fun getPermissionRequester(romInfo: RomInfo): IRomPermissionRequester? {
        when (romInfo.romType) {
            RomType.MIUI -> return MIUIRequesterFactory().getRequester(romInfo.version)
            RomType.FLYME -> return FlymeRequesterFactory().getRequester(romInfo.version)
            RomType.EMUI -> return EMUIRequesterFactory().getRequester(romInfo.version)
            RomType.VIVO -> return VIVORequesterFactory().getRequester(romInfo.version)
            RomType.OTHER -> return DefaultRequester()
        }
    }

    companion object {

        val instance = PermissionRequesterFactory()
    }
}

package com.rompermission.factory

import com.rompermission.factory.impl.EMUIRequesterFactory
import com.rompermission.factory.impl.FlymeRequesterFactory
import com.rompermission.factory.impl.MIUIRequesterFactory
import com.rompermission.factory.impl.VIVORequesterFactory
import com.rompermission.requester.IRomPermissionRequester
import com.rompermission.requester.impl.DefaultRequester
import com.rompermission.requester.impl.vivo.VIVO4Requester
import com.rompermission.util.RomInfo
import com.rompermission.util.RomType

object PermissionRequesterFactory  {
    fun getPermissionRequester(romInfo: RomInfo): IRomPermissionRequester? {
        when (romInfo.romType) {
            RomType.MIUI -> return MIUIRequesterFactory().getRequester(romInfo.version)
            RomType.FLYME -> return FlymeRequesterFactory().getRequester(romInfo.version)
            RomType.EMUI -> return EMUIRequesterFactory().getRequester(romInfo.version)
            RomType.VIVO -> return VIVORequesterFactory().getRequester(romInfo.version)
            RomType.OPPO -> return DefaultRequester()
            RomType.OTHER -> return DefaultRequester()
        }
    }
}

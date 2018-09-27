package com.rompermission.factory.impl

import com.example.rompermission.factory.RomRequesterFactory
import com.example.rompermission.requester.impl.DefaultRequester
import com.example.rompermission.requester.impl.flyme.Flyme5Requester
import com.example.rompermission.requester.impl.flyme.Flyme6Requester

/**
 * like Flyme 6.3.0.2A
 */
class FlymeRequesterFactory : RomRequesterFactory() {
    override fun getRequester(romVersion: String): DefaultRequester {
        return if (isBetween("Flyme 6.0.0.0", "Flyme 7.0.0.0", romVersion)) {
            Flyme6Requester()
        } else if (isBetween("Flyme 5.0.0.0", "Flyme 6.0.0.0", romVersion)) {
            Flyme5Requester()
        } else {
            DefaultRequester()
        }
    }
}

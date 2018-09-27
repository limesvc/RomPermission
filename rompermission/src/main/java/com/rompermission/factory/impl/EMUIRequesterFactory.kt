package com.rompermission.factory.impl

import com.example.rompermission.factory.RomRequesterFactory
import com.example.rompermission.requester.impl.DefaultRequester
import com.example.rompermission.requester.impl.emui.EMUI3Requester
import com.example.rompermission.requester.impl.emui.EMUI3p1Requester

class EMUIRequesterFactory : RomRequesterFactory() {
    override fun getRequester(romVersion: String): DefaultRequester {
        return if (isBetween("EmotionUI_3.0", "EmotionUI_3.1", romVersion)) {
            EMUI3Requester()
        } else if (isBetween("EmotionUI_3.1", "EmotionUI_4.0", romVersion)) {
            EMUI3p1Requester()
        } else {
            DefaultRequester()
        }
    }
}

package com.arcamere.wanderingmerchants.merchant

import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.npc.NpcDriver

data class Merchant(val name: String,
                    private val npcDriver: NpcDriver) {
    var location: MerchantLocation?
        get() = npcDriver.getLocation()
        set(value) {
            if (value == null) {
                npcDriver.destroy()
            } else {
                npcDriver.teleport(value)
            }
        }
}
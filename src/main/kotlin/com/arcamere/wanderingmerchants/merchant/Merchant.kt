package com.arcamere.wanderingmerchants.merchant

import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.npc.MerchantCommand
import com.arcamere.wanderingmerchants.npc.NpcDriver

data class Merchant(val name: String,
                    val command: MerchantCommand?,
                    private val npcDriver: NpcDriver) {

    init {
        if (command != null && command.command != "Set the command for the merchant to execute here") {
            npcDriver.setMerchantCommand(command)
        }
    }

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
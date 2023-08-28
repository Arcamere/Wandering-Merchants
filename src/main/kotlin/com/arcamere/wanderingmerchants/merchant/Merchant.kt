package com.arcamere.wanderingmerchants.merchant

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.npc.NpcDriver
import dev.sergiferry.playernpc.api.NPC
import dev.sergiferry.playernpc.api.NPCLib
import org.bukkit.Bukkit
import org.bukkit.Location

data class Merchant(val name: String,
                    val items: ArrayList<TradeItem>,
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
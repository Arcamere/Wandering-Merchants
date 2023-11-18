package com.arcamere.wanderingmerchants.npc

import com.arcamere.wanderingmerchants.location.MerchantLocation
import org.bukkit.entity.Player

interface NpcDriver {
    fun create(location: MerchantLocation)
    fun teleport(location: MerchantLocation)
    fun getLocation(): MerchantLocation?
    fun destroy()
    fun setMerchantCommand(merchantCommand: MerchantCommand)
}
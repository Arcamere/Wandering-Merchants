package com.arcamere.wanderingmerchants.npc

import com.arcamere.wanderingmerchants.location.MerchantLocation

interface NpcDriver {
    fun create(location: MerchantLocation)
    fun teleport(location: MerchantLocation)
    fun getLocation(): MerchantLocation?
    fun destroy()
}
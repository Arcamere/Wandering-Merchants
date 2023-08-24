package com.arcamere.wanderingmerchants.merchant

import com.arcamere.wanderingmerchants.WanderingMerchants
import dev.sergiferry.playernpc.api.NPC
import dev.sergiferry.playernpc.api.NPCLib
import org.bukkit.Bukkit
import org.bukkit.Location

data class Merchant(val name: String, val items: ArrayList<TradeItem>, private val plugin: WanderingMerchants) {
    private var npc: NPC.Global? = null
    var location: Location?
        get() = npc?.location
        set(value) {
            if (value == null) {
                if (npc == null) {
                    return
                }
                npc?.destroy()
                NPCLib.getInstance().removeGlobalNPC(npc!!)
                npc = null
                return
            }
            if (npc == null) {
                val slug = name.lowercase().replace(" ", "_")
                npc = NPCLib.getInstance().generateGlobalNPC(plugin, "wanderingtrader:$slug", value)
                this.setupNpc()
            } else {
                npc?.teleport(value)
            }
        }

    private fun setupNpc() {
        this.npc?.setText(name)
        this.npc?.forceUpdate()
        this.npc?.forceUpdateText()
    }
}
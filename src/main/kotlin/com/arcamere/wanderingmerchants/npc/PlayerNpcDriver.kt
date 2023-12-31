package com.arcamere.wanderingmerchants.npc

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import dev.sergiferry.playernpc.api.NPC
import dev.sergiferry.playernpc.api.NPCLib
import java.util.function.BiConsumer

class PlayerNpcDriver(private val plugin: WanderingMerchants,
                      private val merchantName: String): NpcDriver {
    private var npc: NPC.Global? = null
    private var location: MerchantLocation? = null
    private var command: MerchantCommand? = null
    override fun create(location: MerchantLocation) {
        val slug = merchantName.lowercase().replace(" ", "_")
        npc = NPCLib.getInstance().generateGlobalNPC(plugin, "wanderingtrader:$slug", location.toLocation())
        this.npc?.setText(merchantName)
        this.npc?.forceUpdate()
        this.npc?.forceUpdateText()
        this.npc?.addCustomClickAction(NPC.Interact.ClickType.RIGHT_CLICK) { _, player ->
            this.command?.executeCommand(player)
        }
    }

    override fun teleport(location: MerchantLocation) {
        this.location = location
        if (this.npc == null) {
            this.create(location)
            return
        }
        this.npc?.teleport(location.toLocation())
    }

    override fun getLocation(): MerchantLocation? {
        return this.location
    }

    override fun destroy() {
        this.location = null
        if (this.npc == null) {
            return
        }
        this.npc?.destroy()
        NPCLib.getInstance().removeGlobalNPC(this.npc!!)
        this.npc = null
    }

    override fun setMerchantCommand(merchantCommand: MerchantCommand) {
        this.command = merchantCommand
    }
}
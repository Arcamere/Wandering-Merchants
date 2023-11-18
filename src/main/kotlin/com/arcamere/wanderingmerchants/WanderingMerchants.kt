package com.arcamere.wanderingmerchants

import com.arcamere.wanderingmerchants.commands.WanderingMerchantsCommand
import com.arcamere.wanderingmerchants.config.Config
import com.arcamere.wanderingmerchants.config.ConfigSerialiser
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import dev.sergiferry.playernpc.api.NPCLib
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class WanderingMerchants: JavaPlugin() {
    var merchants = ArrayList<Merchant>()
    var locations = MerchantLocationMap()
    override fun onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig()

        val config = ConfigSerialiser().deserialise(this)
        this.merchants = config.merchants
        this.locations = config.locations

        NPCLib.getInstance().registerPlugin(this)
        this.getCommand("wanderingmerchants")?.setExecutor(WanderingMerchantsCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
        NPCLib.getInstance().unregisterPlugin(this)
    }

    override fun saveConfig() {
        ConfigSerialiser().serialise(this, Config(merchants, locations)).save(File(this.dataFolder, "config.yml"))
    }
}

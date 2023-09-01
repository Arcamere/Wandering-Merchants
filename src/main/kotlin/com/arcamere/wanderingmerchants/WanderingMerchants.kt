package com.arcamere.wanderingmerchants

import com.arcamere.wanderingmerchants.commands.WanderingMerchantsCommand
import com.arcamere.wanderingmerchants.config.ConfigSerialiser
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import dev.sergiferry.playernpc.api.NPCLib
import dev.sergiferry.playernpc.integration.integrations.Vault
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin

class WanderingMerchants: JavaPlugin() {
    var merchants = ArrayList<Merchant>()
    var locations = MerchantLocationMap()
    lateinit var vault: Economy
    override fun onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig()

        // Link Vault integration
        if (!setupEconomy()) {
            logger.severe(String.format("[WanderingMerchants] - Disabled due to no Vault dependency found!"))
            server.pluginManager.disablePlugin(this)
            return;
        }

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

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return false
        vault = rsp.provider
        return true
    }
}

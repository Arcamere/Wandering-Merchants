package com.arcamere.wanderingmerchants

import com.arcamere.wanderingmerchants.commands.WanderingMerchantsCommand
import com.arcamere.wanderingmerchants.merchant.Merchant
import com.arcamere.wanderingmerchants.merchant.TradeItem
import dev.sergiferry.playernpc.api.NPCLib
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class WanderingMerchants: JavaPlugin() {
    val merchants = arrayListOf(
        Merchant("Wood Merchant", arrayListOf(
                TradeItem(ItemStack(Material.STICK), 1),
                TradeItem(ItemStack(Material.ACACIA_BOAT), 22),
                TradeItem(ItemStack(Material.ACACIA_LOG, 23), 2000)
        ), this),
        Merchant("Ender Merchant", arrayListOf(
                TradeItem(ItemStack(Material.ENDER_CHEST), 2000),
                TradeItem(ItemStack(Material.ENDER_EYE, 10), 1000),
                TradeItem(ItemStack(Material.ENDER_PEARL, 3), 500),
                TradeItem(ItemStack(Material.DRAGON_EGG), 20000)
        ), this),
        Merchant("Poor Merchant", arrayListOf(
                TradeItem(ItemStack(Material.DIRT, 30), 10)
        ), this),
        Merchant("Pretty Bad Merchant", arrayListOf(
                TradeItem(ItemStack(Material.DIAMOND, 25), 25),
                TradeItem(ItemStack(Material.DIAMOND_AXE), 10)
        ), this),
        Merchant("Test", arrayListOf(
            TradeItem(ItemStack(Material.ACACIA_SIGN), 10)
        ), this)
    )
    val locations = ArrayList<Location>()
    override fun onEnable() {
        // Plugin startup logic
        NPCLib.getInstance().registerPlugin(this)
        this.getCommand("wanderingmerchants")?.setExecutor(WanderingMerchantsCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}

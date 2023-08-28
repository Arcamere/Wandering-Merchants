package com.arcamere.wanderingmerchants.config

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import com.arcamere.wanderingmerchants.merchant.TradeItem
import com.arcamere.wanderingmerchants.npc.NpcLibDriver
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ConfigSerialiser {
    fun serialise(config: Config): Nothing = TODO()
    fun deserialise(plugin: WanderingMerchants): Config {
        val config = plugin.getConfig()
        val locations = MerchantLocationMap()
        val merchants = ArrayList<Merchant>()
        config.getList("locations")?.forEach {
            if (it is LinkedHashMap<*, *>) {
                locations[it["name"] as String] = MerchantLocation(
                    it["name"] as String,
                    Bukkit.getWorld(it["world"] as String)!!,
                    (it["x"] as Int).toDouble(),
                    (it["y"] as Int).toDouble(),
                    (it["z"] as Int).toDouble(),
                    (it["yaw"] as Int? ?: 0).toFloat(),
                    (it["pitch"] as Int? ?: 0).toFloat()
                )
            }
        }
        config.getList("merchants")?.forEach { it ->
            if (it is LinkedHashMap<*, *>) {
                val name = it["name"] as String
                val items = ArrayList<TradeItem>()
                (it["items"] as List<*>).forEach {
                    if (it is LinkedHashMap<*, *>) {
                        items.add(TradeItem(
                            ItemStack(Material.matchMaterial(it["item"] as String)!!, it["amount"] as Int),
                            it["price"] as Int
                        ))
                    }
                }
                val npcDriver = NpcLibDriver(plugin, name)
                val merchant = Merchant(name, items, npcDriver)
                merchants.add(merchant)
            }
        }

        return Config(merchants, locations)
    }
}
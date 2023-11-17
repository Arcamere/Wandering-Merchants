package com.arcamere.wanderingmerchants.config

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import com.arcamere.wanderingmerchants.npc.NpcLibDriver
import org.bukkit.Bukkit

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
                val npcDriver = NpcLibDriver(plugin, name)
                val merchant = Merchant(name, npcDriver)
                merchants.add(merchant)
            }
        }

        return Config(merchants, locations)
    }
}
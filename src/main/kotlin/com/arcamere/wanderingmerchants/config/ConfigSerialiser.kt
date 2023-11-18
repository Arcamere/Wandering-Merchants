package com.arcamere.wanderingmerchants.config

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import com.arcamere.wanderingmerchants.npc.PlayerNpcDriver
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration

class ConfigSerialiser {
    fun serialise(plugin: WanderingMerchants, config: Config): YamlConfiguration {
        val yaml = YamlConfiguration()
        val locationSectionHeader = yaml.createSection("locations")
        for (location in config.locations) {
            val locationSection = locationSectionHeader.createSection(location.value.name)
            locationSection.set("name", location.value.name)
            locationSection.set("world", location.value.world.name)
            locationSection.set("x", location.value.x)
            locationSection.set("y", location.value.y)
            locationSection.set("z", location.value.z)
            locationSection.set("yaw", location.value.yaw)
            locationSection.set("pitch", location.value.pitch)
        }

        val merchantsSectionHeader = yaml.createSection("merchants")
        for (merchants in config.merchants) {
            val merchantSection = merchantsSectionHeader.createSection(merchants.name)

        }

        return yaml
    }
    fun deserialise(plugin: WanderingMerchants): Config {
        val config = plugin.getConfig()
        val locations = MerchantLocationMap()
        val merchants = ArrayList<Merchant>()
        config.getConfigurationSection("locations")?.getKeys(false)?.forEach {
            val locationSection = config.getConfigurationSection("locations.$it")
            locations[it] = MerchantLocation(
                locationSection?.getString("name")!!,
                Bukkit.getWorld(locationSection.getString("world")!!)!!,
                locationSection.getDouble("x"),
                locationSection.getDouble("y"),
                locationSection.getDouble("z"),
                locationSection.getDouble("yaw").toFloat(),
                locationSection.getDouble("pitch").toFloat()
            )
        }
        config.getConfigurationSection("merchants")?.getKeys(false)?.forEach { it ->
            val merchantSection = config.getConfigurationSection("merchants.$it")
            val name = it as String
            val npcDriver = PlayerNpcDriver(plugin, name)
            val merchant = Merchant(name, npcDriver)
            merchants.add(merchant)
        }

        return Config(merchants, locations)
    }
}
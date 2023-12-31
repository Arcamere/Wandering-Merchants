package com.arcamere.wanderingmerchants.config

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import com.arcamere.wanderingmerchants.npc.CommandContext
import com.arcamere.wanderingmerchants.npc.MerchantCommand
import com.arcamere.wanderingmerchants.npc.PlayerNpcDriver
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration

class ConfigSerialiser {
    fun serialise(config: Config): YamlConfiguration {
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
        for (merchant in config.merchants) {
            val merchantSection = merchantsSectionHeader.createSection(merchant.name)
            if (merchant.command != null) {
                merchantSection.set("command", merchant.command.command)
                merchantSection.set("command_context", merchant.command.commandContext.name)
            }
        }

        val generalSectionHeader = yaml.createSection("general")
        generalSectionHeader.set("merchant_cap", config.merchantCap)

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
            val command = if (merchantSection?.contains("command")!!) {
                MerchantCommand(merchantSection.getString("command")!!,
                    CommandContext.valueOf(merchantSection.getString("command_context")!!))
            } else {
                null
            }
            val merchant = Merchant(name, command, npcDriver)
            merchants.add(merchant)
        }
        val merchantCap = config.getInt("general.merchant_cap", Int.MAX_VALUE)

        return Config(merchants, locations, merchantCap)
    }
}
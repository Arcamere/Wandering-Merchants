package com.arcamere.wanderingmerchants.commands

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.location.MerchantLocationMap
import com.arcamere.wanderingmerchants.merchant.Merchant
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WanderingMerchantsCommand(private val plugin: WanderingMerchants): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args.isNullOrEmpty()) {
            sender.sendMessage("Wandering Merchants v${plugin.description.version}")
            return true
        }

        return when (args[0]) {
            "shuffle" -> {
                val lastLocation = HashMap<Merchant, MerchantLocation>()
                for (merchant in plugin.merchants) {
                    if (merchant.location == null) {
                        continue
                    }
                    lastLocation[merchant] = merchant.location!!
                    merchant.location = null
                }

                val availableLocations = plugin.locations.clone()

                for (merchant in plugin.merchants.shuffled()) {
                    var location = availableLocations.random()
                    while (lastLocation.containsKey(merchant) && location == lastLocation[merchant] && availableLocations.size > 1) {
                        location = availableLocations.random()
                    }
                    merchant.location = location
                    availableLocations.remove(location)

                    if (availableLocations.size == 0) {
                        return true
                    }
                }
                sender.sendMessage("Shuffled merchants.")
                true
            }
            "addloc" -> {
                if (sender is Player) {
                    plugin.locations.add(
                            MerchantLocation(args[1], sender.location))
                    sender.sendMessage("Added location.")
                    true
                } else {
                    sender.sendMessage("You must be a player to use this command.")
                    false
                }
            }
            else -> {
                sender.sendMessage("Unknown command.")
                false
            }
        }
    }
}
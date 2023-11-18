package com.arcamere.wanderingmerchants.commands

import com.arcamere.wanderingmerchants.WanderingMerchants
import com.arcamere.wanderingmerchants.location.MerchantLocation
import com.arcamere.wanderingmerchants.merchant.Merchant
import com.arcamere.wanderingmerchants.npc.CommandContext
import com.arcamere.wanderingmerchants.npc.MerchantCommand
import com.arcamere.wanderingmerchants.npc.PlayerNpcDriver
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
                plugin.shuffleMerchants()
                sender.sendMessage("Shuffled merchants.")
                true
            }
            "locations" -> {
                if (args.size == 1) {
                    sender.sendMessage("Available commands: " +
                            "* /wm locations list\n" +
                            "* /wm locations add <name>")
                    return true
                }
                when (args[1]) {
                    "list" -> {
                        sender.sendMessage("There are ${plugin.locations.size} locations defined")
                        for (location in plugin.locations) {
                            sender.sendMessage("* ${location.value.name}")
                        }
                        true
                    }
                    "add" -> {
                        if (args.size == 2) {
                            sender.sendMessage("Incorrect usage, correct usage is /wm locations add <name>")
                            false
                        }
                        if (sender is Player) {
                            plugin.locations.add(
                                MerchantLocation(args[2], sender.location))
                            sender.sendMessage("Added location.")
                            plugin.saveConfig()
                            true
                        } else {
                            sender.sendMessage("You must be a player to use this command.")
                            false
                        }
                    }
                    else -> {
                        false
                    }
                }
            }
            "merchants" -> {
                if (args.size == 1) {
                    sender.sendMessage("Available commands: " +
                            "* /wm merchants list\n" +
                            "* /wm merchants add <name>\n" +
                            "* /wm merchants remove <name>")
                    return true
                }
                when (args[1]) {
                    "list" -> {
                        var merchantCount = 0
                        for (merchant in plugin.merchants) {
                            if (merchant.location != null) {
                                merchantCount += 1
                            }
                        }
                        sender.sendMessage("There are $merchantCount/${plugin.merchants.size} merchants deployed")
                        for(merchant in plugin.merchants) {
                            sender.sendMessage("* ${merchant.name}: ${merchant.location?.name ?: "Undeployed"}")
                        }
                        true
                    }
                    "add" -> {
                        if (args.size == 2) {
                            sender.sendMessage("Incorrect usage, correct usage is /wm merchants add <name>")
                            false
                        }
                        val name = args.sliceArray(2 until args.size).joinToString(" ")
                        plugin.merchants.add(Merchant(name, MerchantCommand(
                            "Set the command for the merchant to execute here",
                            CommandContext.AS_CONSOLE), PlayerNpcDriver(plugin, name)))
                        plugin.saveConfig()
                    }
                    "remove" -> {
                        if (args.size == 2) {
                            sender.sendMessage("Incorrect usage, correct usage is /wm merchants remove <name>")
                            false
                        }
                        val name = args.sliceArray(2 until args.size).joinToString(" ").lowercase()
                        var foundMerchant: Merchant? = null
                        for (merchant in plugin.merchants) {
                            // So you can use lowercase variants
                            if (merchant.name.lowercase() == name) {
                                foundMerchant = merchant
                            }
                        }

                        if (foundMerchant != null) {
                            plugin.merchants.remove(foundMerchant)
                            plugin.saveConfig()
                            sender.sendMessage("Removed ${foundMerchant.name}")
                        } else {
                            sender.sendMessage("Could not find a merchant by that name")
                        }
                        true
                    }
                    "tp" -> {
                        if (args.size == 2) {
                            sender.sendMessage("Incorrect usage, correct usage is /wm merchants tp <name>")
                            return false
                        }
                        if (sender !is Player) {
                            sender.sendMessage("You need to be a player to teleport mate")
                            return false
                        }
                        val name = args.sliceArray(2 until args.size).joinToString(" ").lowercase()
                        var foundMerchant: Merchant? = null
                        for (merchant in plugin.merchants) {
                            // So you can use lowercase variants
                            if (merchant.name.lowercase() == name) {
                                foundMerchant = merchant
                            }
                        }

                        if (foundMerchant != null) {
                            if (foundMerchant.location == null) {
                                sender.sendMessage("This merchant has not been deployed")
                                return false
                            }
                            sender.teleport(foundMerchant.location!!.toLocation())
                        } else {
                            sender.sendMessage("Could not find a merchant by that name")
                        }

                    }
                }
                false
            }
            else -> {
                sender.sendMessage("Unknown command.")
                false
            }
        }
    }
}
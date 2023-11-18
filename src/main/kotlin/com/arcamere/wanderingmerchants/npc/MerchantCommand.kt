package com.arcamere.wanderingmerchants.npc

import org.bukkit.Bukkit
import org.bukkit.entity.Player

data class MerchantCommand(val command: String, val commandContext: CommandContext) {
    fun executeCommand(player: Player) {
        val adaptedCommand = command.replace("\$PLAYER_NAME", player.name)
        when (commandContext) {
            CommandContext.AS_USER -> player.performCommand(adaptedCommand)
            CommandContext.AS_CONSOLE -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), adaptedCommand)
        }
    }
}

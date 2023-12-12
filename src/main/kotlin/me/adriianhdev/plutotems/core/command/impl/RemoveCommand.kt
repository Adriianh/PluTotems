package me.adriianhdev.plutotems.core.command.impl

import me.adriianhdev.plutotems.core.totem.TotemManager
import me.adriianhdev.plutotems.util.PlayerUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val removeCommand = subCommand {
    dynamic(comment = "id") {
        suggestion<Player> { _, _ ->
            TotemManager.totems.map { it.id }
        }
        dynamic(comment = "player") {
            suggestion<Player> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            execute<CommandSender> { sender, context, _ ->
                val player = Bukkit.getPlayer(context["player"])!!
                val totem = TotemManager.getTotem(context["id"])!!

                sender.sendLang("Command-Totem-Remove", totem.id, player.name)
                player.sendLang("Totem-Remove-Item", sender.name, totem.id)
                PlayerUtil.removeTotem(player, totem)
            }
        }
        execute<Player> { sender, context, _ ->
            val totem = TotemManager.getTotem(context["id"])!!

            sender.sendLang("Command-Totem-Remove", totem.id, sender.name)
            PlayerUtil.removeTotem(sender, totem)
        }
    }
}
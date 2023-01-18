package me.adriianhdev.plutotems.module.internal.command.impl

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
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
            execute<CommandSender> { sender, context, argument ->
                val player = Bukkit.getPlayer(argument)!!
                val totem = TotemManager.getTotem(context.argument(-1))!!

                sender.sendLang("Command-Totem-Remove", totem.id, player.name)
                player.sendLang("Totem-Remove-Item", sender.name, totem.id)
                PlayerUtil.removeTotem(player, totem)
            }
        }
        execute<Player> { sender, _, argument ->
            val totem = TotemManager.getTotem(argument)!!

            sender.sendLang("Command-Totem-Remove", totem.id, sender.name)
            PlayerUtil.removeTotem(sender, totem)
        }
    }
}
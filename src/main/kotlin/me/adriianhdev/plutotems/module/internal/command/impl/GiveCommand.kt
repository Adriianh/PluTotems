package me.adriianhdev.plutotems.module.internal.command.impl

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val giveCommand = subCommand {
    dynamic(comment = "player") {
        suggestion<Player> { _, _ ->
            Bukkit.getOnlinePlayers().map { it.name }
        }
        dynamic(comment = "id") {
            suggestion<Player> { _, _ ->
                TotemManager.totems.map { it.id }
            }
            execute<CommandSender> { sender, context, argument ->
                val totem = TotemManager.getTotem(argument)
                val player = Bukkit.getPlayer(context.argument(-1))

                if (player == null) {
                    sender.sendLang("Command-Unknown-Player", context.argument(-1))
                    return@execute
                }

                if (totem == null) {
                    sender.sendLang("Command-Unknown-Totem", argument)
                    return@execute
                }

                sender.sendLang("Command-Totem-Give", totem.id, player.name)
                player.sendLang("Command-Totem-Get", totem.id)
                PlayerUtil.giveTotem(player, totem)
            }
        }
    }
}
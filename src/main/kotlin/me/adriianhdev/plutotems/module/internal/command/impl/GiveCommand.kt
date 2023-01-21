package me.adriianhdev.plutotems.module.internal.command.impl

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.module.conf.totem.Totem
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

                giveInfo(sender, player, totem)
                PlayerUtil.giveTotem(player!!, totem!!)
            }
            dynamic(comment = "amount", optional = true) {
                suggestion<Player> { _, _ ->
                    (1..64).map { it.toString() }
                }
                execute<CommandSender> { sender, context, argument ->
                    val totem = TotemManager.getTotem(context.argument(-1))
                    val player = Bukkit.getPlayer(context.argument(-2))
                    val amount = argument.toInt()

                    giveInfo(sender, player, totem)
                    PlayerUtil.giveTotem(player!!, totem!!, amount)
                }
            }
        }
    }
}

fun giveInfo(sender: CommandSender, player: Player?, totem: Totem?) {
    if (player == null) {
        sender.sendLang("Command-Unknown-Player")
        return
    }

    if (totem == null) {
        sender.sendLang("Command-Unknown-Totem")
        return
    }

    sender.sendLang("Command-Totem-Give", totem.id, player.name)
    player.sendLang("Command-Totem-Get", totem.id)
}
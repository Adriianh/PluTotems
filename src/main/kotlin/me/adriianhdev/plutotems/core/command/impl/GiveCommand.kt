package me.adriianhdev.plutotems.core.command.impl

import me.adriianhdev.plutotems.core.totem.Totem
import me.adriianhdev.plutotems.core.totem.TotemManager
import me.adriianhdev.plutotems.util.PlayerUtil
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
            execute<CommandSender> { sender, context, _ ->
                val totem = TotemManager.getTotem(context["id"])
                val player = Bukkit.getPlayer(context["player"])

                giveInfo(sender, player, totem)
                PlayerUtil.giveTotem(player!!, totem!!)
            }
            dynamic(comment = "amount", optional = true) {
                suggestion<Player> { _, _ ->
                    (1..64).map { it.toString() }
                }
                execute<CommandSender> { sender, context, _ ->
                    val totem = TotemManager.getTotem(context["id"])
                    val player = Bukkit.getPlayer(context["player"])
                    val amount = context["amount"].toIntOrNull() ?: 1

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
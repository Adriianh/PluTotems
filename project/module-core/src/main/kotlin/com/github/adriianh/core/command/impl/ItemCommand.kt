package com.github.adriianh.core.command.impl

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.TotemFactory
import com.github.adriianh.common.totem.TotemRegistry
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val getCommand = subCommand {
    dynamic(comment = "id") {
        suggestion<Player> { _, _ ->
            TotemRegistry.getTotems().map { it.id }
        }
        execute<Player> { sender, context, _ ->
            val totem = TotemRegistry.getTotem(context["id"])

            if (totem == null) {
                sender.sendLang("Command-Totem-Not-Found", context["id"])
                return@execute
            }

            sender.sendLang("Command-Totem-Get", totem.id)
            TotemFactory.giveTotem(sender, totem)
        }
        dynamic(comment = "amount", optional = true) {
            suggestion<Player> { _, _ ->
                (1..64).map { it.toString() }
            }
            execute<Player> { sender, context, _ ->
                val totem = TotemRegistry.getTotem(context["id"])

                if (totem == null) {
                    sender.sendLang("Command-Totem-Not-Found", context["id"])
                    return@execute
                }

                sender.sendLang("Command-Totem-Get", totem.id)
                TotemFactory.giveTotem(sender, totem, context["amount"].toInt())
            }
        }
    }
}

val giveCommand = subCommand {
    dynamic(comment = "player") {
        suggestion<Player> { _, _ ->
            Bukkit.getOnlinePlayers().map { it.name }
        }
        dynamic(comment = "id") {
            suggestion<Player> { _, _ ->
                TotemRegistry.getTotems().map { it.id }
            }
            execute<Player> { sender, context, _ ->
                val (totem, player) = getTotemAndPlayer(sender, context["id"], context["player"]) ?: return@execute

                sender.sendLang("Command-Totem-Give", player!!.name, totem!!.id)
                TotemFactory.giveTotem(player, totem)
            }
            dynamic(comment = "amount", optional = true) {
                suggestion<Player> { _, _ ->
                    (1..64).map { it.toString() }
                }
                execute<Player> { sender, context, _ ->
                    val (totem, player) = getTotemAndPlayer(sender, context["id"], context["player"]) ?: return@execute

                    sender.sendLang("Command-Totem-Give", player!!.name, totem!!.id)
                    TotemFactory.giveTotem(player, totem, context["amount"].toInt())
                }
            }
        }
    }
}

private fun getTotemAndPlayer(sender: Player, totemId: String, playerName: String): Pair<Totem?, Player?>? {
    val totem = TotemRegistry.getTotem(totemId)
    val player = Bukkit.getPlayer(playerName)

    if (totem == null) {
        sender.sendLang("Command-Totem-Not-Found", totemId)
        return null
    }

    if (player == null) {
        sender.sendLang("Command-Player-Not-Found", playerName)
        return null
    }

    return Pair(totem, player)
}
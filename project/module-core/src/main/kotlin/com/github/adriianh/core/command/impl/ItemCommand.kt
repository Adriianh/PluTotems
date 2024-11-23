package com.github.adriianh.core.command.impl

import com.github.adriianh.common.totem.TotemFactory
import com.github.adriianh.common.totem.TotemRegistry
import com.github.adriianh.common.util.getTotemAndPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

@CommandHeader(
    name = "item",
    permission = "plutotems.command.item"
)
object ItemCommand {
    @CommandBody
    val get = subCommand {
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

    @CommandBody
    val give = subCommand {
        dynamic(comment = "player") {
            suggestion<Player> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            dynamic(comment = "id") {
                suggestion<Player> { _, _ ->
                    TotemRegistry.getTotems().map { it.id }
                }
                execute<Player> { sender, context, _ ->
                    val (player, totem) = getTotemAndPlayer(sender, context["player"], context["id"]) ?: return@execute

                    sender.sendLang("Command-Totem-Give", player!!.name, totem!!.id)
                    TotemFactory.giveTotem(player, totem)
                }
                dynamic(comment = "amount", optional = true) {
                    suggestion<Player> { _, _ ->
                        (1..64).map { it.toString() }
                    }
                    execute<Player> { sender, context, _ ->
                        val (player, totem) = getTotemAndPlayer(sender, context["player"], context["id"]) ?: return@execute

                        sender.sendLang("Command-Totem-Give", player!!.name, totem!!.id)
                        TotemFactory.giveTotem(player, totem, context["amount"].toInt())
                    }
                }
            }
        }
    }
}
package me.adriianhdev.plutotems.core.command.impl

import me.adriianhdev.plutotems.core.totem.TotemManager
import me.adriianhdev.plutotems.util.PlayerUtil
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val getCommand = subCommand {
    dynamic(comment = "id") {
        suggestion<Player> { _, _ ->
            TotemManager.totems.map { it.id }
        }
        execute<Player> { sender, context, _ ->
            val totem = TotemManager.getTotem(context["id"])

            if (totem == null) {
                sender.sendLang("Command-Unknown-Totem", context["id"])
                return@execute
            }

            sender.sendLang("Command-Totem-Get", totem.id)
            PlayerUtil.giveTotem(sender, totem)
        }
        dynamic(comment = "amount", optional = true) {
            suggestion<Player> { _, _ ->
                (1..64).map { it.toString() }
            }
            execute<Player> { sender, context, _ ->
                val totem = TotemManager.getTotem(context["id"])
                val amount = context["amount"].toIntOrNull() ?: 1

                if (totem == null) {
                    sender.sendLang("Command-Unknown-Totem", context["id"])
                    return@execute
                }

                sender.sendLang("Command-Totem-Get", totem.id)
                PlayerUtil.giveTotem(sender, totem, amount)
            }
        }
    }
}
package me.adriianhdev.plutotems.module.internal.command.impl

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val getCommand = subCommand {
    dynamic(comment = "id") {
        suggestion<Player> { _, _ ->
            TotemManager.totems.map { it.id }
        }
        execute<Player> { sender, _, argument ->
            val totem = TotemManager.getTotem(argument)

            if (totem == null) {
                sender.sendLang("Command-Unknown-Totem", argument)
                return@execute
            }

            sender.sendLang("Command-Totem-Get", totem.id)
            PlayerUtil.giveTotem(sender, totem)
        }
        dynamic(comment = "amount", optional = true) {
            suggestion<Player> { _, _ ->
                (1..64).map { it.toString() }
            }
            execute<Player> { sender, context, argument ->
                val totem = TotemManager.getTotem(context.argument(-1))
                val amount = argument.toInt()

                if (totem == null) {
                    sender.sendLang("Command-Unknown-Totem", context.argument(-1))
                    return@execute
                }

                sender.sendLang("Command-Totem-Get", totem.id)
                PlayerUtil.giveTotem(sender, totem, amount)
            }
        }
    }
}
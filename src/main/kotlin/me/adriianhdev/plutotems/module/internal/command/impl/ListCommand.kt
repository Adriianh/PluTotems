package me.adriianhdev.plutotems.module.internal.command.impl

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.common.util.StringUtil.replaceVar
import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.chat.TellrawJson

val listCommand = subCommand {
    execute<CommandSender> { sender, _, _ ->
        val proxySender = adaptCommandSender(sender)

        proxySender.sendMessage("")
        TellrawJson()
            .append("  ").append("§6PluTotems")
            .append(" ").append("§f${PluTotems.plugin.description.version}")
            .hoverText(
                """
                §7Plugin version: §2${PluTotems.plugin.description.version}
            """.trimIndent()
            ).sendTo(proxySender)
        proxySender.sendMessage("")

        TotemManager.totems.forEach { totem ->
            TellrawJson()
                .append("    §8- ").append("§f${totem.name}".colorify())
                .hoverText(
                    """
                    §8» &7Totem ID: §f{id}
                    §8» &7Totem description: §f{description}
                    &7
                    &8• &7Click to get the totem.
                """.trimIndent().colorify().replaceVar(totem)
                )
                .runCommand("/plutotems get ${totem.id} ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7{description}".colorify().replaceVar(totem))
        }
    }
}
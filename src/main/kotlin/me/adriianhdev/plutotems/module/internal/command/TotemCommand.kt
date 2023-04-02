package me.adriianhdev.plutotems.module.internal.command

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.module.internal.command.impl.*
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.chat.TellrawJson
import taboolib.platform.util.asLangText
import java.util.*

@CommandHeader(
    name = "plutotems",
    aliases = ["pt", "plutotem", "totem", "totems"],
    description = "PluTotems main command",
    permission = "plutotems.command.access"
)
object TotemCommand {
    @TotemHelper
    @CommandBody(permission = "plutotems.command.get")
    val get = getCommand

    @TotemHelper
    @CommandBody(permission = "plutotems.command.give")
    val give = giveCommand

    @TotemHelper
    @CommandBody(permission = "plutotems.command.remove")
    val remove = removeCommand

    @TotemHelper
    @CommandBody(permission = "plutotems.command.list")
    val list = listCommand

    @TotemHelper
    @CommandBody(permission = "plutotems.command.reload")
    val reload = reloadCommand

    @TotemHelper
    @CommandBody(permission = "plutotems.command.schematic")
    val schematic = SchematicCommand

    @CommandBody(permission = "plutotems.command.help")
    val help = subCommand {
        execute<CommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    @CommandBody
    val main = mainCommand {
        execute<CommandSender> { sender, _, argument ->
            if (argument.isEmpty()) {
                generateMainHelper(sender)
                return@execute
            }
            sender.sendMessage("§8[§6PluTotems§8] §cERROR §3| Args §6$argument §3not found.")
            TellrawJson()
                .append("§8[§6PluTotems§8] §aINFO §f| Type ").append("§6/plutotems help")
                .hoverText("§f/plutotems help §8- §7more help...")
                .suggestCommand("/plutotems help")
                .append("§f for help.")
                .sendTo(adaptCommandSender(sender))
        }
    }

    /*
        @author Arasple
     */
    private fun generateMainHelper(sender: CommandSender) {
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
        TellrawJson()
            .append("  §7${sender.asLangText("Command-Help-Type")}: ").append("§f/plutotems §8[...]")
            .hoverText("§f/plutotems §8[...]")
            .suggestCommand("/plutotems ")
            .sendTo(proxySender)
        proxySender.sendMessage("  §7${sender.asLangText("Command-Help-Args")}:")

        javaClass.declaredFields.forEach { field ->
            if (!field.isAnnotationPresent(TotemHelper::class.java)) return@forEach
            val name = field.name
            val regularName =
                name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val desc = sender.asLangText("Command-$regularName-Description")

            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/plutotems $name §8- §7$desc")
                .suggestCommand("/plutotems $name ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7$desc")
        }
        proxySender.sendMessage("")
    }
}
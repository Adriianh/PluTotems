package com.github.adriianh.core.command

import com.github.adriianh.core.PluTotems
import com.github.adriianh.core.command.impl.*
import com.github.adriianh.core.command.impl.SchematicCommand
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.chat.Components
import taboolib.platform.util.asLangText
import java.util.*

@CommandHeader(
    name = "totem",
    aliases = ["totems", "plutotems", "pt"],
    permission = "totem.command.access",
    description = "Main command for the totem plugin."
)
object TotemCommand {
    @TotemHelper
    @CommandBody
    val item = ItemCommand

    @TotemHelper
    @CommandBody
    val info = InfoCommand

    @TotemHelper
    @CommandBody
    val config = ConfigCommand

    @TotemHelper
    @CommandBody
    val schematic = SchematicCommand

    @TotemHelper
    @CommandBody
    val menu = MenuCommand

    @TotemHelper
    @CommandBody
    val admin = AdminCommand

    @CommandBody
    val main = mainCommand {
        execute<CommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    /*
        @author Arasple
    */
    private fun generateMainHelper(sender: CommandSender) {
        val proxySender = adaptCommandSender(sender)
        proxySender.sendMessage("")
        Components.empty()
            .append("  ").append("§6PluTotems")
            .append(" ").append("§f${PluTotems.plugin.description.version}")
            .hoverText(
                """
                §7Plugin version: §2${PluTotems.plugin.description.version}
            """.trimIndent()
            ).sendTo(proxySender)
        proxySender.sendMessage("")
        Components.empty()
            .append("  §7${sender.asLangText("Command-Help-Type")}: ").append("§f/plutotems §8[...]")
            .hoverText("§f/plutotems §8[...]")
            .clickSuggestCommand("/plutotems ")
            .sendTo(proxySender)
        proxySender.sendMessage("  §7${sender.asLangText("Command-Help-Args")}:")

        javaClass.declaredFields.forEach { field ->
            if (!field.isAnnotationPresent(TotemHelper::class.java)) return@forEach
            val name = field.name
            val regularName =
                name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val desc = sender.asLangText("Command-$regularName-Description")

            Components.empty()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/plutotems $name §8- §7$desc")
                .clickSuggestCommand("/plutotems $name ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7$desc")
        }
        proxySender.sendMessage("")
    }
}
package com.github.adriianh.core.command.impl

import com.github.adriianh.common.compat.schematic.saveSchematic
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.getDataFolder
import taboolib.platform.util.sendLang
import java.io.File

@CommandHeader(
    name = "schem"
)
object SchemCommand {
    @CommandBody
    val save = subCommand {
        dynamic(comment = "name") {
            execute<Player> { sender, context, _ ->
                saveSchematic(sender, context["name"])
            }
        }
    }

    @CommandBody
    val delete = subCommand {
        dynamic(comment = "name") {
            suggestion<Player> { _, _ ->
                File(getDataFolder(), "schematics/")
                    .listFiles()
                    ?.map { it.nameWithoutExtension }
                    ?.toMutableList()
            }
            execute<Player> { sender, context, _ ->
                val name = context["name"]
                val file = File(getDataFolder(), "schematics/$name.schem")

                if (!file.exists()) {
                    sender.sendLang("Command-Schematic-Not-Found", name)
                    return@execute
                }

                file.delete()
                sender.sendLang("Command-Schematic-Delete", name)
            }
        }
    }
}
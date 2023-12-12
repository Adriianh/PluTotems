package me.adriianhdev.plutotems.core.command.impl

import me.adriianhdev.plutotems.util.SchematicUtil
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.getDataFolder
import taboolib.platform.util.sendLang
import java.io.File
import java.io.FileNotFoundException

@CommandHeader(
    name = "totem schematic",
    permission = "plutotems.command.schematic")
object SchematicCommand {
    @CommandBody(permission = "plutotems.command.schematic.save")
    val save = subCommand {
        dynamic {
            execute<Player> { sender, _, argument ->
                SchematicUtil.save(sender, argument)
            }
        }
    }

    @CommandBody(permission = "plutotems.command.schematic.delete")
    val delete = subCommand {
        dynamic {
            suggestion<Player> { _, _ ->
                File(getDataFolder(), "schematics")
                    .listFiles()?.map { it.nameWithoutExtension } ?: emptyList()
            }
            execute<Player> { sender, _, argument ->
                val file = File(getDataFolder(), "schematics/$argument.schem")

                try {
                    sender.sendLang("Command-Schematic-Deleted", argument)
                    file.delete()
                } catch (e: FileNotFoundException) {
                    sender.sendLang("Command-Schematic-Not-Found", argument)
                }
            }
        }
    }
}
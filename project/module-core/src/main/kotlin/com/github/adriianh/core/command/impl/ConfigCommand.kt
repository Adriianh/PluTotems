package com.github.adriianh.core.command.impl

import com.github.adriianh.common.config.ConfigLoader
import com.github.adriianh.common.config.ConfigManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

@CommandHeader(
    name = "config",
    permission = "totem.command.config",
)
object ConfigCommand {
    private val manager = ConfigManager
    private val loader = ConfigLoader

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            try {
                sender.sendLang("Command-Plugin-Reload")

                manager.getUpdatedConfigurations()
                loader.loadFiles()
            } catch (e: Exception) {
                sender.sendLang("Command-Plugin-Reload-Failed")
                e.printStackTrace()
            }
        }
    }
}
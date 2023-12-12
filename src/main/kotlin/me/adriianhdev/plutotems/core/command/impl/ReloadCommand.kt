package me.adriianhdev.plutotems.core.command.impl

import me.adriianhdev.plutotems.plugin.PluTotemsLoader
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val reloadCommand = subCommand {
    execute<CommandSender> { sender, _, _ ->
        try {
            PluTotemsLoader.reload()
            sender.sendLang("Command-Plugin-Reload")
        } catch (e: Exception) {
            sender.sendMessage("An error occurred while reloading the plugin!")
            e.printStackTrace()
        }
    }
}
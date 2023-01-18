package me.adriianhdev.plutotems.module.internal.command.impl

import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

val listCommand = subCommand {
    execute<CommandSender> { sender, _, _ ->
        sender.sendLang("Command-Totem-List")
        TotemManager.totems.forEach { totem ->
            sender.sendMessage("§7- §a${totem.id}")
        }
    }
}
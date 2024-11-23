package com.github.adriianh.core.command.impl

import com.github.adriianh.core.ui.MenuHelper
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand

@CommandHeader(
    name = "menu",
    permission = "plutotems.command.menu"
)
object MenuCommand {
    @CommandBody
    val list = subCommand {
        execute<Player> { sender, _, _ ->
            MenuHelper.openListMenu(sender)
        }
    }
}
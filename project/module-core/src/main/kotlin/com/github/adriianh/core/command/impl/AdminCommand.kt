package com.github.adriianh.core.command.impl

import com.github.adriianh.core.command.impl.admin.CooldownCommand
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader

@CommandHeader(
    name = "admin",
    aliases = ["a"],
    permission = "plutotems.command.admin"
)
object AdminCommand {
    @CommandBody
    val cooldown = CooldownCommand
}
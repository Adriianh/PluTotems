package com.github.adriianh.core.command.impl.admin

import com.github.adriianh.common.totem.TotemRegistry
import com.github.adriianh.common.util.CooldownUtil
import com.github.adriianh.common.util.TimeUtil
import com.github.adriianh.common.util.getTotemAndPlayer
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.platform.util.sendLang

@CommandHeader(
    name = "cooldown",
    aliases = ["cd"],
    permission = "plutotems.command.admin.cooldown"
)
object CooldownCommand {
    @CommandBody
    val reset = subCommand {
        dynamic(comment = "player") {
            suggestPlayers()
            dynamic(comment = "id") {
                suggestion<Player> { _, _ ->
                    TotemRegistry.getTotems().map { it.id }
                }
                execute<Player> { sender, context, _ ->
                    val (player, totem) = getTotemAndPlayer(sender, context["player"], context["id"]) ?: return@execute

                    CooldownUtil.resetCooldown(player!!, totem!!.id)
                    sender.sendLang("Command-Cooldown-Reset", player.name, totem.id)
                }
            }
        }
    }

    @CommandBody
    val set = subCommand {
        dynamic(comment = "player", optional = false) {
            suggestPlayers()
            dynamic(comment = "id", optional = false) {
                suggestion<Player> { _, _ ->
                    TotemRegistry.getTotems().map { it.id }
                }
                dynamic(comment = "cooldown", optional = false) {
                    execute<Player> { sender, context, _ ->
                        val cooldownString = context["cooldown"]
                        val cooldownMillis = TimeUtil.parseTimeToMillis(cooldownString)
                        val (player, totem) = getTotemAndPlayer(sender, context["player"], context["id"]) ?: return@execute

                        CooldownUtil.setCooldown(player!!, totem!!.id, cooldownMillis / 1000)
                        sender.sendLang("Command-Cooldown-Set", cooldownString, player.name, totem.id)
                    }
                }
            }
        }
    }
}
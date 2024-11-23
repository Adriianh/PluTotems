package com.github.adriianh.common.util

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.TotemRegistry
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.platform.util.sendLang

fun getTotemAndPlayer(sender: Player, name: String, id: String): Pair<Player?, Totem?>? {
    val player = Bukkit.getPlayer(name)
    val totem = TotemRegistry.getTotem(id)

    if (player == null) {
        sender.sendLang("Command-Player-Not-Found", name)
        return null
    }

    if (totem == null) {
        sender.sendLang("Command-Totem-Not-Found", id)
        return null
    }


    return Pair(player, totem)
}
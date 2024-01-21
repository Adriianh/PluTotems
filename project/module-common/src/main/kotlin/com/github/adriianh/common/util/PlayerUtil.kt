package com.github.adriianh.common.util

import org.bukkit.entity.Player

object PlayerUtil {
    fun getNearbyPlayers(player: Player, radius: Double): List<Player> {
        return player.getNearbyEntities(radius, radius, radius).mapNotNull { it as? Player }
    }
}
package com.github.adriianh.common.util

import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

object CooldownUtil {
    private val cooldown = HashMap<UUID, Long>()

    fun setCooldown(player: Player, time: Int) {
        val delay = System.currentTimeMillis() + (time * 1000)
        cooldown[player.uniqueId] = delay
    }

    fun isInCooldown(player: Player): Boolean {
        val isInCooldown = cooldown.containsKey(player.uniqueId) && cooldown[player.uniqueId]!! > System.currentTimeMillis()
        if (!isInCooldown) {
            cooldown.remove(player.uniqueId)
        }
        return isInCooldown
    }

    fun getRestTime(player: Player): Int {
        return ((cooldown[player.uniqueId]!! - System.currentTimeMillis()) / 1000).toInt()
    }
}
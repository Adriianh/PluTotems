package com.github.adriianh.common.util

import org.bukkit.entity.Player
import java.util.*

object CooldownUtil {
    private val cooldowns = HashMap<UUID, HashMap<String, Long>>()

    fun setCooldown(player: Player, totemId: String, time: Long) {
        val playerCooldowns = cooldowns.getOrPut(player.uniqueId) { HashMap() }
        val delay = System.currentTimeMillis() + (time * 1000)
        playerCooldowns[totemId] = delay
    }

    fun hasCooldown(player: Player, totemId: String): Boolean {
        val playerCooldowns = cooldowns[player.uniqueId] ?: return false
        val hasCooldown =
            playerCooldowns.containsKey(totemId) && playerCooldowns[totemId]!! > System.currentTimeMillis()

        if (!hasCooldown) {
            playerCooldowns.remove(totemId)
            if (playerCooldowns.isEmpty()) {
                cooldowns.remove(player.uniqueId)
            }
        }

        return hasCooldown
    }

    fun getCooldown(player: Player, totemId: String): String {
        val playerCooldowns = cooldowns[player.uniqueId] ?: return "00:00:00"
        val remainingTime = playerCooldowns[totemId]!! - System.currentTimeMillis()
        return TimeUtil.formatTime(remainingTime)
    }

    fun resetCooldown(player: Player, totemId: String) {
        val playerCooldowns = cooldowns[player.uniqueId] ?: return
        playerCooldowns.remove(totemId)
        if (playerCooldowns.isEmpty()) {
            cooldowns.remove(player.uniqueId)
        }
    }
}
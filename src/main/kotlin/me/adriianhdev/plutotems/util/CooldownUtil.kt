package me.adriianhdev.plutotems.util

import org.bukkit.entity.Player
import java.util.*


class CooldownUtil {
    private val cooldown = HashMap<UUID, Long>()

    fun setCooldown(player: Player, time: Int) {
        val delay = System.currentTimeMillis() + (time * 1000)
        cooldown[player.uniqueId] = delay
    }

    fun isCooldown(player: Player): Boolean {
        return cooldown.containsKey(player.uniqueId) && cooldown[player.uniqueId]!! > System.currentTimeMillis()
    }

    fun getRestTime(player: Player): Int {
        return ((cooldown[player.uniqueId]!! - System.currentTimeMillis()) / 1000).toInt()
    }

    companion object {
        const val DEFAULT_COOLDOWN: Int = 10
    }
}
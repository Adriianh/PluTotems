package com.github.adriianh.core.event

import com.github.adriianh.common.config.ConfigManager
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

object PlayerResurrectEvent {
    private val settings = ConfigManager.settings

    @SubscribeEvent(priority = EventPriority.MONITOR)
    fun onPlayerResurrect(event: EntityResurrectEvent) {
        val player = event.entity as? Player ?: return

        if (event.isCancelled) return

        val chance = settings.getInt("Settings.Chance")
        val random = (0..100).random()

        if (chance != 100 && random > chance) {
            event.isCancelled = true
            player.sendLang("Totem-Resurrect-Failure")
        } else {
            player.sendLang("Totem-Resurrect-Success")
        }
    }
}
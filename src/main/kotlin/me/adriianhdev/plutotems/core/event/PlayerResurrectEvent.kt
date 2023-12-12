package me.adriianhdev.plutotems.core.event

import me.adriianhdev.plutotems.configuration.ConfigManager
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

object PlayerResurrectEvent {
    private val config = ConfigManager.config

    @SubscribeEvent(priority = EventPriority.MONITOR)
    fun onPlayerResurrect(event: EntityResurrectEvent) {
        val chance = config.getInt("Totem.chance")

        val player = event.entity
        if (player !is Player) return
        if (event.isCancelled) return

        if (chance != 100 && (0..100).random() > chance) {
            player.sendLang("Totem-Resurrect-Failure")
            event.isCancelled = true
        } else {
            player.sendLang("Totem-Resurrect-Success")
        }
    }
}
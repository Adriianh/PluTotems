package com.github.adriianh.common.adventure

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import taboolib.platform.BukkitPlugin

object Adventure {
    private lateinit var audience: BukkitAudiences

    fun addAudience(plugin: BukkitPlugin) {
        if (!::audience.isInitialized) {
            audience = BukkitAudiences.create(plugin)
        }
    }

    fun getAudience(): BukkitAudiences {
        check(::audience.isInitialized) {
            "Tried to get audience before it was initialized"
        }

        return audience
    }
}
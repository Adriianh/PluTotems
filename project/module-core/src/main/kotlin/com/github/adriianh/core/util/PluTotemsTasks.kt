package com.github.adriianh.core.util

import com.github.adriianh.common.totem.TotemTask
import com.github.adriianh.core.PluTotems
import org.bukkit.Bukkit

object PluTotemsTasks {
    fun registerTasks() {
        Bukkit.getServer().scheduler.runTaskTimer(PluTotems.plugin, TotemTask, 0L, 20L)
    }
}
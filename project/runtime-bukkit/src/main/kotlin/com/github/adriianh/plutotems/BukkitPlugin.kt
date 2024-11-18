package com.github.adriianh.plutotems

import com.github.adriianh.core.util.PluTotemsLoader.onPluginDisable
import com.github.adriianh.core.util.PluTotemsLoader.onPluginEnable
import com.github.adriianh.core.util.PluTotemsLoader.onPluginLoad
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformSide
import taboolib.common.platform.Plugin

@PlatformSide(Platform.BUKKIT)
object BukkitPlugin : Plugin() {
    override fun onLoad() = onPluginLoad()

    override fun onEnable() = onPluginEnable()

    override fun onDisable() = onPluginDisable()
}
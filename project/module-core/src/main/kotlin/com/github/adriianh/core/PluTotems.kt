package com.github.adriianh.core

import com.github.adriianh.core.util.PluTotemsLoader.onPluginDisable
import com.github.adriianh.core.util.PluTotemsLoader.onPluginEnable
import com.github.adriianh.core.util.PluTotemsLoader.onPluginLoad
import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

object PluTotems : Plugin() {
    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    override fun onLoad() {
        onPluginLoad()
    }

    override fun onEnable() {
        onPluginEnable()
    }

    override fun onDisable() {
        onPluginDisable()
    }
}
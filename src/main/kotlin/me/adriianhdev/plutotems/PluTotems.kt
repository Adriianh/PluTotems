package me.adriianhdev.plutotems

import me.adriianhdev.plutotems.plugin.PluTotemsLoader
import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

object PluTotems : Plugin() {
    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    override fun onLoad() {
        PluTotemsLoader.load()
    }

    override fun onEnable() {
        PluTotemsLoader.init()
    }

    override fun onDisable() {
        PluTotemsLoader.unload()
    }
}
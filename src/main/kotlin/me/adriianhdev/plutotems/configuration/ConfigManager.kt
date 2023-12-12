package me.adriianhdev.plutotems.configuration

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object ConfigManager {
    @Config("config.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    @Config("totem/ExampleTotems.yml", autoReload = true)
    lateinit var totems: Configuration
        private set

    fun reload() {
        config.reload()
        totems.reload()
    }
}
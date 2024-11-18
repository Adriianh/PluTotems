package com.github.adriianh.common.config

import com.github.adriianh.common.config.ConfigLoader.getConfigurationList
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object ConfigManager {
    @Config("Settings.yml", autoReload = true, migrate = true)
    lateinit var settings: Configuration

    @Config("UI.yml", autoReload = true, migrate = true)
    lateinit var ui: Configuration

    fun getConfigurations() = getConfigurationList()

    fun getUpdatedConfigurations() = getConfigurationList().map { it.reload() }
}
package com.github.adriianh.common.config

import com.github.adriianh.common.totem.TotemRegistry
import com.github.adriianh.common.util.ConfigUtil.getTotemData
import org.bukkit.configuration.file.YamlConfiguration
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.module.configuration.Configuration
import java.io.File

object ConfigLoader {
    private val files = ArrayList<File>()
    private val configurations = mutableMapOf<Configuration, YamlConfiguration>()

    @Awake(LifeCycle.ACTIVE)
    fun loadFiles() {
        clearFiles()
        getDefaults()
        getFiles(File(getDataFolder(), "totem/"))
        getConfigurations()
        getTotems()
    }

    fun getConfigurationList(): List<Configuration> = configurations.keys.toList()

    fun getYamlList(): List<YamlConfiguration> = configurations.values.toList()

    private fun clearFiles() {
        files.clear()
        configurations.clear()
        TotemRegistry.totems.clear()
    }

    private fun getFiles(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { getFiles(it) }
        } else {
            files.add(file)
        }
    }

    private fun getConfigurations() {
        files.forEach { file ->
            val config = Configuration.loadFromFile(file)
            val yamlConfig = YamlConfiguration.loadConfiguration(file)
            configurations[config] = yamlConfig
        }
    }

    private fun getTotems() {
        configurations.forEach { (config, yamlConfig) ->
            config.getKeys(false).forEach { key ->
                TotemRegistry.registerTotem(key) { getTotemData(config, yamlConfig, key) }
            }
        }
    }

    private fun getDefaults() {
        val dir = File(getDataFolder(), "totem/")
        val examples = arrayListOf(
            "totem/Default.yml",
        )

        if (!dir.exists()) {
            dir.mkdirs()

            examples.forEach { example ->
                val file = File(dir, example.substringAfterLast("/"))
                val inputStream = javaClass.classLoader.getResourceAsStream(example)
                inputStream?.let {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                    file.writeBytes(it.readBytes())
                }
            }
        }
    }
}
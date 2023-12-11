package me.adriianhdev.plutotems.module.conf

import me.adriianhdev.plutotems.common.util.getConditions
import me.adriianhdev.plutotems.common.util.getOptions
import me.adriianhdev.plutotems.common.util.getTotemItem
import me.adriianhdev.plutotems.common.util.getTypes
import me.adriianhdev.plutotems.module.conf.totem.Totem
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import me.adriianhdev.plutotems.module.conf.totem.TotemScript
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

object ConfigLoader {
    private val files = ArrayList<File>()
    private val configs = ArrayList<Configuration>()

    @Awake(LifeCycle.ACTIVE)
    fun loader() {
        reset()
        loadFile(File(getDataFolder(), "totem/"))
        loadConfigs()
        registerTotems()
    }

    private fun reset() {
        files.clear()
        configs.clear()
        TotemManager.totems.clear()
    }

    private fun loadConfigs() {
        configs.addAll(files.map { Configuration.loadFromFile(it, Type.YAML) })
    }

    private fun loadFile(file: File) {
        if (file.isFile) files.add(file)
        else file.listFiles()?.forEach(::loadFile)
    }

    private fun registerTotems() {
        configs.forEach { config ->
            config.getKeys(false).forEach { key ->
                TotemManager.addTotem(createTotem(config, key))
            }
        }
    }

    private fun createTotem(config: Configuration, key: String): Totem {
        val item = getTotemItem(config, key)
        val conditions = getConditions(config, key)
        val options = getOptions(config, key)
        val types = getTypes(config, key)
        val actions = config.getStringList("$key.actions")
        val scripts = config.getStringList("$key.scripts")
        val effects = config.getStringList("$key.effects")
        val heldEffect = config.getStringList("$key.heldEffects")

        val totemScript = TotemScript(item, conditions, options, types, actions, scripts, effects, heldEffect)
        return Totem(
            id = key,
            item = item,
            name = config.getString("$key.name"),
            description = config.getString("$key.description"),
            rarity = config.getString("$key.rarity"),
            data = totemScript
        )
    }
}
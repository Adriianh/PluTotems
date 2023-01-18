package me.adriianhdev.plutotems.module.conf

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.*
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.library.xseries.XItemStack
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

object ConfigLoader {
    private val files = ArrayList<File>()
    private val configs = ArrayList<Configuration>()

    @Awake(LifeCycle.ACTIVE)
    fun loader() {
        files.clear()
        configs.clear()
        TotemManager.totems.clear()
        loadFile(File(getDataFolder(), "totem/"))
        loadConfig()
        create()
    }

    private fun create() {
        configs.forEach { config ->
            register(config)
        }
    }

    private fun loadConfig() {
        files.forEach {
            configs.add(Configuration.loadFromFile(it, Type.YAML))
        }
    }

    private fun loadFile(file: File) {
        if (file.isFile) {
            files.add(file)
        } else {
            file.listFiles()?.forEach {
                loadFile(it)
            }
        }
    }

    private fun register(config: Configuration) {
        config.getKeys(false).forEach { key ->
            val name = config.getString("$key.name")
            val description = config.getString("$key.description")
            val type = config.getString("$key.type")
            val rarity = config.getString("$key.rarity")
            val actions = config.getStringList("$key.actions")
            val scripts = config.getStringList("$key.scripts")
            val effects = config.getStringList("$key.effects")

            val item = config.getConfigurationSection("$key.item")!!.let {
                XItemStack.deserialize(it) { it.colorify() }
            }
            val conditions = Condition(
                config.getInt("$key.conditions.chance.value"),
                config.getString("$key.conditions.permission.value"),
                config.getStringList("$key.conditions.kether")
            )
            val options = Options(
                config.getDouble("$key.options.health"),
                config.getBoolean("$key.options.playAnimation"),
                config.getBoolean("$key.options.isClickable"),
                config.getBoolean("$key.options.isConsumable"),
                config.getBoolean("$key.options.isThrowable"),
                config.getBoolean("$key.options.isPickupable"),
                config.getBoolean("$key.options.isPlaceable")
            )

            val totemScript = TotemScript(item, conditions, options, actions, scripts, effects)
            val totem = Totem(
                id = key,
                item = item,
                name = name,
                description = description,
                type = type,
                rarity = rarity,
                data = totemScript
            ).let {
                TotemManager.addTotem(it)
            }
        }
    }
}
package com.github.adriianh.common.util

import com.github.adriianh.common.totem.Property
import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.action.ActionRegistry
import com.github.adriianh.common.totem.condition.ConditionRegistry
import com.github.adriianh.common.totem.effect.Effect
import com.github.adriianh.common.totem.getTypeByName
import com.github.adriianh.common.totem.option.OptionRegistry
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XItemStack
import taboolib.module.configuration.Configuration

object ConfigUtil {
    fun getItemStack(config: ConfigurationSection, path: String): ItemStack? {
        return config.getConfigurationSection(path)?.let { item ->
            XItemStack.deserialize(item.toMap()) { it.colorify() }
        }
    }

    fun getTotemData(config: Configuration, yamlConfiguration: YamlConfiguration, key: String) = Totem(
        id = key,
        name = config.getString("$key.name")!!,
        item = getItemStack(config, "$key.item")!!,
        type = getTypeByName(config.getString("$key.type")!!),
        options = getElements(config, key, "options", OptionRegistry::getOption),
        actions = getElements(config, key, "actions", ActionRegistry::getAction),
        effects = getEffects(config, key),
        conditions = getElements(config, key, "conditions", ConditionRegistry::getCondition),
        settings = config,
    )

    private fun <T : Property> getElements(
        config: Configuration,
        key: String,
        path: String,
        registryGetter: (String) -> T?
    ): List<T> {
        val elements = mutableListOf<T>()

        val sections = mutableListOf(
            key,
            "$key.$path",
            "$key.$path.entity",
            "$key.$path.structure",
        )

        sections.forEach { section ->
            config.getConfigurationSection(section)?.getValues(false)?.forEach { (element, value) ->
                registryGetter(element)?.let { property ->
                    property.setConvertedValue(value)
                    elements.add(property)
                }
            }
        }

        return elements
    }

    private fun getEffects(config: Configuration, key: String): List<Effect> {
        val effects = mutableListOf<Effect>()

        config.getConfigurationSection("$key.effects")?.getKeys(false)?.forEach { effectName ->
            val effectType = PotionEffectType.getByName(effectName)
            val duration = config.getInt("$key.effects.$effectName.duration")
            val amplifier = config.getInt("$key.effects.$effectName.level")

            val description = config.getStringList("$key.effects.$effectName.description")

            if (effectType != null) {
                effects.add(Effect(effectType, duration, amplifier, description))
            }
        }

        return effects
    }
}
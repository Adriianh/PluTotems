package com.github.adriianh.common.totem

import com.github.adriianh.common.compat.OptionParameters
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.totem.condition.Condition
import com.github.adriianh.common.totem.effect.Effect
import com.github.adriianh.common.totem.option.Option
import org.bukkit.inventory.ItemStack
import taboolib.module.configuration.Configuration
import java.util.*

class Totem(
    val id: String,
    val name: String,
    val item: ItemStack,
    val type: TotemType,
    options: List<Option<*>>? = null,
    actions: List<Action<*>>? = null,
    effects: List<Effect>? = null,
    conditions: List<Condition<*>>? = null,
    val settings: Configuration? = null,
) : OptionParameters {
    private val optionsMap: Map<String, Option<*>> = options?.associateBy { it.identifier } ?: emptyMap()
    private val actionsMap: Map<String, Action<*>> = actions?.associateBy { it.identifier } ?: emptyMap()
    private val effectsMap: Map<String, Effect> = effects?.associateBy { it.type.name } ?: emptyMap()
    private val conditionsMap: Map<String, Condition<*>> = conditions?.associateBy { it.identifier } ?: emptyMap()

    fun getOptions(): List<Option<*>> = optionsMap.values.toList()
    fun getActions(): List<Action<*>> = actionsMap.values.toList()
    fun getEffects(): List<Effect> = effectsMap.values.toList()
    fun getConditions(): List<Condition<*>> = conditionsMap.values.toList()

    fun getOption(identifier: String): Option<*>? {
        return optionsMap[identifier.uppercase(Locale.getDefault())]
    }

    fun getAction(identifier: String): Action<*>? {
        return actionsMap[identifier.uppercase(Locale.getDefault())]
    }

    fun getEffect(identifier: String): Effect? {
        return effectsMap[identifier]
    }

    fun getCondition(identifier: String): Condition<*>? {
        return conditionsMap[identifier.uppercase(Locale.getDefault())]
    }

    override fun getParameters(name: String): Any? {
        return getOption(name)?.getOptionValue()
    }
}
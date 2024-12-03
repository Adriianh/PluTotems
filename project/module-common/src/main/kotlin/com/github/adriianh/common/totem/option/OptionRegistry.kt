package com.github.adriianh.common.totem.option

import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.totem.option.type.impl.base.*
import com.github.adriianh.common.totem.option.type.impl.entity.*
import com.github.adriianh.common.totem.option.type.impl.schematic.OptionSchem
import com.github.adriianh.common.totem.option.type.impl.schematic.OptionSchemAir
import com.github.adriianh.common.totem.option.type.impl.schematic.OptionSchemEntities
import com.github.adriianh.common.totem.option.type.impl.schematic.OptionSchemRotation
import com.github.adriianh.common.totem.option.type.impl.trigger.*
import java.util.*

object OptionRegistry {
    private val options = mutableMapOf<Pair<String, OptionTypes>, Option<*>>()

    init {
        registerOptions(
            OptionActions(), OptionAnimation(), OptionCooldown(), OptionConditions(), OptionDuration(),
            OptionExecute(), OptionEffects(), OptionHealth(), OptionItem(), OptionName(), OptionRadius(),
            OptionRarity(), OptionType(), OptionEntityAI(), OptionEntityArms(),
            OptionEntityBase(), OptionEntityCollidable(), OptionEntityCustom(), OptionEntityEquipment(),
            OptionEntityGlowing(), OptionEntityGravity(), OptionEntityInvulnerable(), OptionEntityKill(),
            OptionEntityMarker(), OptionEntityName(), OptionEntityNameVisible(), OptionEntityPose(),
            OptionEntitySmall(), OptionEntityType(), OptionEntityVisible(), OptionEntityRotation(),
            OptionSchem(), OptionSchemEntities(), OptionSchemAir(), OptionSchemRotation(),
            OptionClickable(), OptionConsumable(), OptionPickupable(), OptionPlaceable(), OptionThrowable()
        )
    }

    private fun registerOptions(vararg options: Option<*>) {
        options.forEach { registerOption(it) }
    }

    private fun registerOption(option: Option<*>) {
        options[option.identifier to option.type] = option
    }

    fun getOption(identifier: String, type: OptionTypes): Option<*>? {
        val option = options[identifier.uppercase(Locale.getDefault()) to type]?.clone()

        return option
    }

    fun getOption(identifier: String): Option<*>? {
        return options.keys.find { it.first.equals(identifier, ignoreCase = true) }?.let { options[it]?.clone() }
    }

    fun getOptions(): List<Option<*>> {
        return options.values.toList()
    }
}
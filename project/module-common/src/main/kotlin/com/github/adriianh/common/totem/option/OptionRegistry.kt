package com.github.adriianh.common.totem.option

import com.github.adriianh.common.totem.option.type.impl.base.*
import com.github.adriianh.common.totem.option.type.impl.entity.*
import com.github.adriianh.common.totem.option.type.impl.schematic.*
import com.github.adriianh.common.totem.option.type.impl.trigger.*
import java.util.*

object OptionRegistry {
    private val options = mutableMapOf<String, Option<*>>()

    init {
        registerOptions(
            OptionActions(), OptionAnimation(), OptionCooldown(), OptionDuration(),
            OptionEffects(), OptionHealth(), OptionItem(), OptionName(), OptionRadius(),
            OptionRarity(), OptionScripts(), OptionType(), OptionEntityAI(), OptionEntityArms(),
            OptionEntityBase(), OptionEntityCollidable(), OptionEntityCustom(), OptionEntityEquipment(),
            OptionEntityGlowing(), OptionEntityGravity(), OptionEntityInvulnerable(), OptionEntityKill(),
            OptionEntityMarker(), OptionEntityName(), OptionEntityNameVisible(), OptionEntityPose(),
            OptionEntitySmall(), OptionEntityType(), OptionEntityVisible(), OptionEntityRotation(),
            OptionSchem(), OptionSchemEntities(), OptionSchemAir(), OptionSchemRotation(),
            OptionClickable(), OptionConsumable(), OptionPickupable(), OptionPlaceable(), OptionThrowable()
        )
    }

    private fun registerOption(option: Option<*>) {
        options[option.identifier] = option
    }

    private fun registerOptions(vararg options: Option<*>) {
        options.forEach { registerOption(it) }
    }

    fun getOption(identifier: String): Option<*>? {
        val option = options[identifier.uppercase(Locale.getDefault())]?.clone()

        return option
    }

    fun getOptions(): List<Option<*>> {
        return options.values.toList()
    }
}
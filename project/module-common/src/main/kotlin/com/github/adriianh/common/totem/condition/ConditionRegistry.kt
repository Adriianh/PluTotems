package com.github.adriianh.common.totem.condition

import com.github.adriianh.common.totem.condition.impl.ConditionChance
import com.github.adriianh.common.totem.condition.impl.ConditionPermission
import com.github.adriianh.common.totem.condition.impl.ConditionScript
import java.util.*

object ConditionRegistry {
    private val conditions = mutableMapOf<String, Condition<*>>()

    init {
        register(
            ConditionChance(), ConditionPermission(), ConditionScript()
        )
    }

    private fun register(condition: Condition<*>) {
        conditions[condition.id] = condition
    }

    private fun register(vararg conditions: Condition<*>) {
        conditions.forEach { register(it) }
    }


    fun getCondition(identifier: String): Condition<*>? {
        val condition = conditions[identifier.uppercase(Locale.getDefault())]

        return condition
    }

    fun getConditions(): List<Condition<*>> {
        return conditions.values.toList()
    }
}
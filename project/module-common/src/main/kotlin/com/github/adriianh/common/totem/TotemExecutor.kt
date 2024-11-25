package com.github.adriianh.common.totem

import com.github.adriianh.common.totem.TotemEntityFactory.spawnEntity
import com.github.adriianh.common.totem.TotemStructureFactory.spawnSchematic
import com.github.adriianh.common.totem.effect.EffectRegistry.addPotionEffect
import com.github.adriianh.common.totem.option.type.impl.base.OptionAnimation
import com.github.adriianh.common.totem.option.type.impl.base.OptionHealth
import com.github.adriianh.common.totem.option.type.impl.base.OptionInventory
import com.github.adriianh.common.util.CooldownUtil
import com.github.adriianh.common.util.ItemUtil.isAirOrNull
import com.github.adriianh.common.util.PlayerUtil.getNearbyPlayers
import org.bukkit.EntityEffect
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import taboolib.common5.cbool
import taboolib.common5.cdouble
import taboolib.common5.clong
import taboolib.platform.util.sendLang
import java.util.*

object TotemExecutor {
    private val cooldown = CooldownUtil

    fun execute(
        player: Player,
        item: ItemStack,
        totem: Totem,
        isEvent: Boolean = false,
        reduceAmount: Boolean? = true
    ) {
        if (cooldown.hasCooldown(player, totem.id)) {
            player.sendLang("Totem-Cooldown", cooldown.getCooldown(player, totem.id))
            return
        }

        if (reduceAmount == true) {
            item.amount--
        }

        val type = totem.type
        when (type) {
            TotemType.ITEM, TotemType.ARMOR -> {
                execute(player, totem)
            }

            TotemType.ENTITY, TotemType.STRUCTURE -> {
                executeType(player, totem)
            }
        }

        val cooldownTime = totem.getOption("cooldown")?.getOptionValue().clong
        if (cooldownTime > 0) {
            cooldown.setCooldown(player, totem.id, cooldownTime)
        }

        val optionHealth = totem.getOption("health") as? OptionHealth
        val optionHealthAmount = optionHealth?.getOptionValue() ?: 10.0
        if (isEvent) {
            player.health = optionHealthAmount
        } else {
            player.health = (player.health + optionHealthAmount).coerceAtMost(
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
            )
        }
    }

    fun execute(player: Player, event: EntityDamageEvent) {
        val hand = player.inventory.itemInMainHand
        val offhand = player.inventory.itemInOffHand
        val armor = player.inventory.armorContents
        val inventory = player.inventory.contents

        val items = listOf(hand, offhand) + armor + inventory
        for (item in items) {
            if (isAirOrNull(item)) continue
            if (!TotemFactory.isTotem(item)) continue

            val totem = TotemFactory.getTotem(item)

            if (inventory.contains(item) && !checkInventoryOption(totem)) continue
            if (!checkConditions(player, totem)) {
                event.isCancelled = false
                item.amount--
                return
            }

            execute(player, item, totem, true)
            event.isCancelled = true
            break
        }
    }

    fun handleEvent(
        player: Player,
        item: ItemStack,
        optionName: String,
        event: Cancellable? = null,
        cancelEvent: Boolean = false,
        reduceAmount: Boolean? = true
    ) {
        if (!TotemFactory.isTotem(item)) return

        val totem = TotemFactory.getTotem(item)
        val option = totem.getOption(optionName) ?: return

        if (!option.getOptionValue().cbool) {
            val regularName =
                (option.id).replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                    else it.toString()
                }

            player.sendLang("Totem-Non-$regularName")
            return
        }

        if (cancelEvent) {
            event?.isCancelled = true
        }

        execute(player = player, item = item, totem = totem, reduceAmount = reduceAmount)
    }

    fun execute(
        player: Player,
        totem: Totem
    ) {
        val actions = totem.getActions()
        val effects = totem.getEffects()
        val conditions = totem.getConditions()

        conditions.forEach { condition ->
            if (!condition.checkResult(player)) {
                return
            }
        }

        val optionAnimation = totem.getOption("animation") as? OptionAnimation
        if (optionAnimation == null || optionAnimation.getOptionValue()) {
            playAnimation(player)
        }

        val optionHealth = totem.getOption("health") as? OptionHealth
        val optionHealthAmount = optionHealth?.getOptionValue() ?: 10.0
        if (optionHealth != null) {
            player.health += optionHealthAmount
        }

        actions.forEach { action ->
            action.execute(player)
        }

        effects.forEach { effect ->
            addPotionEffect(player, effect)
        }
    }

    private fun checkInventoryOption(totem: Totem): Boolean {
        val optionInventory = totem.getOption("inventory") as? OptionInventory ?: return true
        return !optionInventory.getOptionValue()
    }

    private fun checkConditions(
        player: Player,
        totem: Totem
    ): Boolean {
        val conditions = totem.getConditions()

        conditions.forEach { condition ->
            if (!condition.checkResult(player)) {
                return false
            }
        }

        return true
    }

    private fun executeType(
        player: Player,
        totem: Totem
    ) {
        val actions = totem.getActions()
        val effects = totem.getEffects()
        val conditions = totem.getConditions()

        val executeActions = totem.getOption("actions")?.getOptionValue()
        val executeEffects = totem.getOption("effects")?.getOptionValue()
        val radius = totem.getOption("radius")?.getOptionValue()

        conditions.forEach { condition ->
            if (!condition.checkResult(player)) {
                return
            }
        }

        val nearbyPlayers = getNearbyPlayers(player, radius.cdouble)

        if (totem.type == TotemType.ENTITY) {
            spawnEntity(player, totem)
        }
        if (totem.type == TotemType.STRUCTURE) {
            spawnSchematic(player, totem)
        }

        when (executeActions) {
            "self" -> {
                actions.forEach { action ->
                    action.execute(player)
                }
                if (executeEffects == "self") {
                    effects.forEach { effect ->
                        addPotionEffect(player, effect)
                    }
                }
            }

            "all" -> {
                nearbyPlayers.forEach { nearbyPlayer ->
                    actions.forEach { action ->
                        action.execute(nearbyPlayer)
                    }
                    if (executeEffects == "all") {
                        effects.forEach { effect ->
                            addPotionEffect(nearbyPlayer, effect)
                        }
                    }
                }
            }

            else -> {
                actions.forEach { action ->
                    action.execute(player)
                }
                effects.forEach { effect ->
                    addPotionEffect(player, effect)
                }
            }
        }
    }

    private fun playAnimation(player: Player) {
        player.playEffect(EntityEffect.TOTEM_RESURRECT)
        player.playHurtAnimation(0f)
    }
}
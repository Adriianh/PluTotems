package me.adriianhdev.plutotems.common.util

import me.adriianhdev.plutotems.common.util.KetherUtil.eval
import me.adriianhdev.plutotems.common.util.TotemUtil.isAirOrNull
import me.adriianhdev.plutotems.module.conf.totem.Totem
import me.adriianhdev.plutotems.module.conf.totem.action.ActionManager
import me.adriianhdev.plutotems.module.conf.totem.effect.Effect
import org.bukkit.EntityEffect
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack

object ExecutorUtils {
    fun run(player: Player, totem: Totem) {
        val actions = totem.data.actions ?: return
        val scripts = totem.data.scripts ?: return
        val effects = totem.data.effects ?: return

        if (actions.isNotEmpty()) {
            actions.forEach { action ->
                ActionManager.execute(action, player)
            }
        }

        if (scripts.isNotEmpty()) {
            scripts.eval(player)
        }

        if (effects.isNotEmpty()) {
            effects.forEach { effect ->
                Effect.addEffect(effect, player)
            }
        }

        handlePlayer(player, totem)
    }

    fun handleAction(player: Player, event: EntityDamageEvent) {
        val inventory = player.inventory
        val armor = player.inventory.armorContents
        val hand = player.inventory.itemInMainHand
        val offHand = player.inventory.itemInOffHand

        when {
            TotemUtil.isTotem(offHand) -> {
                check(offHand, player, event)
            }

            TotemUtil.isTotem(hand) -> {
                check(hand, player, event)
            }

            armor.isNotEmpty() -> {
                for (item in armor) {
                    if (isAirOrNull(item)) continue

                    if (TotemUtil.isTotem(item)) {
                        val totem = TotemUtil.getTotem(item)!!
                        if (!totem.type.equals("armor", true)) return

                        check(item, player, event)
                        break
                    }
                }
            }
            inventory.any { TotemUtil.isTotem(it) } -> {
                for (i in 0 until inventory.size) {
                    val item = inventory.getItem(i) ?: continue
                    if (isAirOrNull(item)) return

                    if (!TotemUtil.isTotem(item)) return
                    val totem = TotemUtil.getTotem(item)!!
                    val option = totem.data.options.autoTotem

                    if (option == true) {
                        check(item, player, event)
                        break
                    }
                }
            }
        }
    }

    fun checkEffects(player: Player, item: ItemStack) {
        if (!TotemUtil.isTotem(item)) return

        val totem = TotemUtil.getTotem(item)!!
        val effects = totem.data.heldEffect ?: return

        if (!totem.type.equals("armor", true)) return

        effects.forEach { effect ->
            Effect.removeEffect(effect, player)
        }
    }

    fun checkCondition(player: Player, totem: Totem): Boolean {
        val conditions = totem.data.conditions
        if (conditions.check(player)) {
            return true
        }
        return false
    }

    private fun handlePlayer(player: Player, totem: Totem) {
        val options = totem.data.options
        val healthAmount = options.healthAmount
        val playAnimation = options.playAnimation

        player.health = (player.health + healthAmount)
            .coerceAtMost(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.defaultValue)

        if (playAnimation == true) {
            player.playEffect(EntityEffect.HURT)
            player.playEffect(EntityEffect.TOTEM_RESURRECT)
        }
    }

    private fun check(item: ItemStack, player: Player, event: EntityDamageEvent) {
        if (!TotemUtil.isTotem(item)) return

        val totem = TotemUtil.getTotem(item)!!
        if (!checkCondition(player, totem)) {
            item.amount--
            event.isCancelled = false

            return
        }
        event.isCancelled = true

        checkEffects(player, item)
        run(player, totem)
        item.amount--
    }
}
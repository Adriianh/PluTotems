package me.adriianhdev.plutotems.common.util

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.common.util.KetherUtil.eval
import me.adriianhdev.plutotems.module.conf.totem.Totem
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import me.adriianhdev.plutotems.module.conf.totem.effect.Effect
import org.bukkit.EntityEffect
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType


object TotemUtil {
    private val key = NamespacedKey(PluTotems.plugin, "totem")

    fun setKey(item: ItemStack, totem: Totem) {
        val meta = item.itemMeta!!
        val container = meta.persistentDataContainer

        container.set(key, PersistentDataType.STRING, totem.id)
        item.itemMeta = meta
    }

    fun getKey(item: ItemStack): String? {
        val container: PersistentDataContainer = item.itemMeta!!.persistentDataContainer
        return container.get(key, PersistentDataType.STRING)
    }

    fun hasKey(item: ItemStack): Boolean {
        val container = item.itemMeta!!.persistentDataContainer
        return container.has(key, PersistentDataType.STRING)
    }

    fun isTotem(item: ItemStack): Boolean {
        return (item.hasItemMeta() && hasKey(item))
    }

    fun getTotem(item: ItemStack): Totem? {
        if (!hasKey(item)) return null
        val id = getKey(item)!!

        return TotemManager.getTotem(id)
    }

    fun run(player: Player, totem: Totem, item: ItemStack) {
        val actions = totem.data.actions ?: return
        val scripts = totem.data.scripts ?: return
        val effects = totem.data.effects ?: return

        if (actions.isNotEmpty()) {
            actions.forEach { action ->
                Action.execute(action, player)
            }
        }

        if (scripts.isNotEmpty()) {
            scripts.eval(player)
        }

        if (effects.isNotEmpty()) {
            effects.forEach { effect ->
                Effect.addDeathEffect(effect, player)
            }
        }

        handlePlayer(player, totem)
        item.amount--
    }

    fun run(player: Player, totem: Totem) {
        val actions = totem.data.actions ?: return
        val scripts = totem.data.scripts ?: return
        val effects = totem.data.effects ?: return

        if (actions.isNotEmpty()) {
            actions.forEach { action ->
                Action.execute(action, player)
            }
        }

        if (scripts.isNotEmpty()) {
            scripts.eval(player)
        }

        if (effects.isNotEmpty()) {
            effects.forEach { effect ->
                Effect.addDeathEffect(effect, player)
            }
        }

        handlePlayer(player, totem)
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

    fun handleAction(player: Player, event: Cancellable) {
        val offhand = player.inventory.itemInOffHand
        val hand = player.inventory.itemInMainHand

        if (isTotem(offhand)) {
            check(offhand, player, event)
        } else {
            if (isTotem(hand)) {
                check(hand, player, event)
            }
        }
    }

    private fun check(item: ItemStack, player: Player, event: Cancellable) {
        val totem = getTotem(item)!!

        if (!checkCondition(player, totem)) {
            event.isCancelled = false
            item.amount--

            return
        }

        event.isCancelled = true
        run(player, totem, item)
    }
}
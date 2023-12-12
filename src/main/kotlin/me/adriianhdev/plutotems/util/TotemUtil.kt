package me.adriianhdev.plutotems.util

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.core.totem.Totem
import me.adriianhdev.plutotems.core.totem.TotemManager
import me.adriianhdev.plutotems.util.CooldownUtil.Companion.DEFAULT_COOLDOWN
import me.adriianhdev.plutotems.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.util.ExecutorUtils.runType
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.util.*

object TotemUtil {
    private val key = NamespacedKey(PluTotems.plugin, "totem")
    private val keyOwner = NamespacedKey(PluTotems.plugin, "totem_owner")

    fun setOwner(entity: Entity, owner: UUID) {
        val container = entity.persistentDataContainer
        container.set(keyOwner, PersistentDataType.STRING, owner.toString())
    }

    fun setKey(item: ItemStack, totem: Totem) {
        val meta = item.itemMeta!!
        val container = meta.persistentDataContainer

        container.set(key, PersistentDataType.STRING, totem.id)
        item.itemMeta = meta
    }

    fun setKey(entity: Entity, totem: Totem) {
        val container = entity.persistentDataContainer
        container.set(key, PersistentDataType.STRING, totem.id)
    }

    private fun getOwner(entity: Entity): UUID? {
        val container = entity.persistentDataContainer
        val owner = container.get(keyOwner, PersistentDataType.STRING) ?: return null

        return UUID.fromString(owner)
    }

    fun getKey(item: ItemStack): String? {
        val container: PersistentDataContainer = item.itemMeta!!.persistentDataContainer
        return container.get(key, PersistentDataType.STRING)
    }

    private fun getKey(entity: Entity): String? {
        val container = entity.persistentDataContainer
        return container.get(key, PersistentDataType.STRING)
    }

    private fun hasOwner(entity: Entity): Boolean {
        val container = entity.persistentDataContainer
        return container.has(keyOwner, PersistentDataType.STRING)
    }

    private fun hasKey(item: ItemStack): Boolean {
        val container = item.itemMeta!!.persistentDataContainer
        return container.has(key, PersistentDataType.STRING)
    }

    private fun hasKey(entity: Entity): Boolean {
        val container = entity.persistentDataContainer
        return container.has(key, PersistentDataType.STRING)
    }

    fun isOwner(entity: Entity, owner: UUID): Boolean {
        return hasOwner(entity) && getOwner(entity) == owner
    }

    fun isTotem(item: ItemStack): Boolean {
        return (item.hasItemMeta() && hasKey(item))
    }

    fun isTotem(entity: Entity): Boolean {
        return hasKey(entity)
    }

    fun getTotem(item: ItemStack): Totem? {
        if (!hasKey(item)) return null
        val id = getKey(item)!!

        return TotemManager.getTotem(id)
    }

    fun getTotem(entity: Entity): Totem? {
        if (!hasKey(entity)) return null
        val id = getKey(entity)!!

        return TotemManager.getTotem(id)
    }

    fun isAirOrNull(item: ItemStack?): Boolean {
        return item == null || item.type == Material.AIR
    }

    fun hasCooldown(totem: Totem): Boolean {
        val cooldown = totem.data.options.cooldown

        return cooldown != null && cooldown > 0
    }

    fun getCooldown(totem: Totem): Int {
        return totem.data.options.cooldown ?: DEFAULT_COOLDOWN
    }

    fun checkType(type: String, player: Player, totem: Totem, item: ItemStack) {
        when (type.lowercase(Locale.getDefault())) {
            "structure" -> {
                checkEffects(player, item)
                runType(player, totem)
                item.amount--
            }
            "entity" -> {
                checkEffects(player, item)
                runType(player, totem)
                item.amount--
            }
        }
    }
}
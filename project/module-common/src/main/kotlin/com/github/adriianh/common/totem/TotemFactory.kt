package com.github.adriianh.common.totem

import de.tr7zw.changeme.nbtapi.NBT
import de.tr7zw.changeme.nbtapi.NBTEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XItemStack

object TotemFactory {
    private fun setTotemData(item: ItemStack, totem: Totem) {
        NBT.modify(item) { nbt ->
            nbt.setString("Totem", totem.id)
            nbt.setBoolean("isTotem", true)
        }
    }

    fun setTotemData(entity: Entity, totem: Totem) {
        NBT.modify(entity) { nbt ->
            nbt.setString("Totem", totem.id)
            nbt.setBoolean("isTotem", true)
        }
    }

    fun setOwner(player: Player, entity: Entity) {
        NBT.modify(entity) { nbt ->
            nbt.setString("Owner", player.uniqueId.toString())
            nbt.setBoolean("isTotem", true)
        }
    }

    fun isTotem(item: ItemStack): Boolean {
        return NBT.readNbt(item).getBoolean("isTotem")
    }

    fun isTotem(entity: Entity): Boolean {
        return NBTEntity(entity).getBoolean("isTotem")
    }

    fun getTotem(item: ItemStack): Totem {
        val id = NBT.readNbt(item).getString("Totem")
        return TotemRegistry.getTotem(id)!!
    }

    fun getTotem(entity: Entity): Totem {
        val id = NBTEntity(entity).getString("Totem")
        return TotemRegistry.getTotem(id)!!
    }

    fun getOwner(entity: Entity): Player {
        val uuid = NBTEntity(entity).getString("Owner")
        return entity.world.players.find { it.uniqueId.toString() == uuid }!!
    }

    fun isOwner(player: Player, entity: Entity): Boolean {
        return NBTEntity(entity).getString("Owner") == player.uniqueId.toString()
    }

    fun giveTotem(player: Player, totem: Totem, amount: Int = 1) {
        val item = totem.item.clone()

        item.amount = amount
        setTotemData(item, totem)
        XItemStack.giveOrDrop(player, item)
    }
}
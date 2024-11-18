package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemFactory
import com.github.adriianh.common.totem.TotemType
import com.github.adriianh.common.totem.effect.EffectRegistry.removePotionsEffect
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent

object PlayerArmorEvent {
    @SubscribeEvent
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        removeEffects(player, item)
    }

    @SubscribeEvent
    fun onPlayerHoldItem(event: PlayerItemHeldEvent) {
        val player = event.player
        val item = player.inventory.getItem(event.previousSlot) ?: return

        removeEffects(player, item)
    }

    @SubscribeEvent
    fun onPlayerMoveItem(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        if (event.slot != player.inventory.heldItemSlot || event.cursor == null) return
        if (event.slotType != InventoryType.SlotType.ARMOR) return

        val item = event.currentItem ?: return

        removeEffects(player, item)
    }

    private fun removeEffects(player: Player, item: ItemStack) {
        if (!TotemFactory.isTotem(item)) return

        val totem = TotemFactory.getTotem(item)
        if (totem.type != TotemType.ARMOR) return

        totem.removePotionsEffect(player)
    }
}
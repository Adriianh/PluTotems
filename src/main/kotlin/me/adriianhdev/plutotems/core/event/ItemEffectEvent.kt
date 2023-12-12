package me.adriianhdev.plutotems.core.event

import me.adriianhdev.plutotems.util.ExecutorUtils.checkEffects
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent

object ItemEffectEvent {
    @SubscribeEvent
    fun onItemHoldEvent(event: PlayerItemHeldEvent) {
        val player = event.player
        val item: ItemStack = player.inventory.getItem(event.previousSlot) ?: return

        checkEffects(player, item)
    }

    @SubscribeEvent
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        checkEffects(player, item)
    }

    @SubscribeEvent
    fun onPlayerMoveItemInHand(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        if (event.slot != player.inventory.heldItemSlot && event.slotType != InventoryType.SlotType.ARMOR) return
        val item = event.currentItem ?: return

        checkEffects(player, item)
    }
}
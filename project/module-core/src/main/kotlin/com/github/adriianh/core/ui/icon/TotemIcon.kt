package com.github.adriianh.core.ui.icon

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.TotemFactory
import com.github.adriianh.core.ui.MenuHelper
import nl.odalitadevelopments.menus.items.ClickableItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import taboolib.platform.util.ItemBuilder

fun getClickableTotem(totem: Totem, list: List<String>) = ClickableItem.of(
    ItemBuilder(totem.item).apply {
        lore.addAll(list)
    }.build()
) { event ->
    val player = event.whoClicked as Player

    when (event.click) {
        ClickType.SHIFT_LEFT -> {

        }

        ClickType.RIGHT -> {
            MenuHelper.openEditorMenu(player, totem)

        }

        ClickType.LEFT -> {
            TotemFactory.giveTotem(player, totem)
        }

        else -> {
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BIT, 1f, 1f)
        }
    }
}
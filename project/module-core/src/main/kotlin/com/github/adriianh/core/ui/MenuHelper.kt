package com.github.adriianh.core.ui

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.core.util.PluTotemsLoader.getOdalitaMenus
import nl.odalitadevelopments.menus.items.DisplayItem
import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.platform.util.ItemBuilder

object MenuHelper {
    val FILL_ITEM = DisplayItem.of(
        ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).apply {
            name = ""
            hideAll()
        }.build()
    )

    fun openListMenu(player: Player) {
        getOdalitaMenus()?.openMenu(ListMenu(), player)
    }

    fun openEditorMenu(player: Player, totem: Totem) {
        getOdalitaMenus()?.openMenu(EditorMenu(totem), player)
    }
}
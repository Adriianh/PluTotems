package com.github.adriianh.core.ui

import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.DisplayItem
import nl.odalitadevelopments.menus.menu.providers.GlobalMenuProvider
import nl.odalitadevelopments.menus.menu.type.MenuType
import org.bukkit.Material
import taboolib.platform.util.ItemBuilder

@Menu(
    title = "Totem List | Menu",
    type = MenuType.CHEST_6_ROW
)
class ListMenu : GlobalMenuProvider {
    override fun onLoad(menuContents: MenuContents) {
        menuContents.fillBorders(
            DisplayItem.of(
                ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).apply {
                    name = ""
                    hideAll()
                }.build()
            )
        )
    }
}
package com.github.adriianh.core.ui

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.TotemRegistry
import com.github.adriianh.core.sorter.TotemSorter
import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.ClickableItem
import nl.odalitadevelopments.menus.items.DisplayItem
import nl.odalitadevelopments.menus.items.buttons.CloseItem
import nl.odalitadevelopments.menus.items.buttons.PageItem
import nl.odalitadevelopments.menus.iterators.MenuIteratorType
import nl.odalitadevelopments.menus.iterators.MenuObjectIterator
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider
import nl.odalitadevelopments.menus.menu.type.MenuType
import nl.odalitadevelopments.menus.pagination.ObjectPagination
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import taboolib.platform.util.ItemBuilder

@Menu(
    title = "Totem List | Menu",
    type = MenuType.CHEST_6_ROW
)
class ListMenu : PlayerMenuProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        menuContents.fillBorders(
            DisplayItem.of(
                ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).apply {
                    name = ""
                    hideAll()
                }.build()
            )
        )

        val pagination: ObjectPagination<Totem> = menuContents.pagination("player_example_pagination", 36)
            .objectIterator(this.createObjectIterator(menuContents))
            .create()

        pagination.let {
            it.createBatch()

            TotemRegistry.getTotems().forEach { totem ->
                it.addItem(totem)
            }

            it.endBatch()
        }

        menuContents[45] = PageItem.previous(pagination)
        menuContents[53] = PageItem.next(pagination)
        menuContents[49] = CloseItem.get()

        menuContents.setRefreshable(51) {
            val cachedSorter: TotemSorter = menuContents.cache("sorter", TotemSorter.NAME_ASCENDING)
            ClickableItem.of(ItemBuilder(Material.HOPPER).apply {
                name = "Sorter"

                lore.add("")
                for (sorter in TotemSorter.entries) {
                    lore.add(
                        if (cachedSorter == sorter) "§a${sorter.name}"
                        else "§7${sorter.name}"
                    )
                }

                val next: TotemSorter = cachedSorter.next()
                val previous: TotemSorter = cachedSorter.previous()

                lore.add("")
                lore.add("§7Left Click: §a${next.name}")
                lore.add("§7Right Click: §a${previous.name}")
            }.build()) { event: InventoryClickEvent ->
                val next: TotemSorter = if (event.isRightClick) cachedSorter.next() else cachedSorter.previous()
                pagination.sorter(0, next.getComparator())
                pagination.apply()

                menuContents.setCache("sorter", next)
                menuContents.refreshItem(51)
            }
        }
    }

    private fun createObjectIterator(contents: MenuContents): MenuObjectIterator<Totem> {
        return contents.createObjectIterator(MenuIteratorType.HORIZONTAL, 0, 0, Totem::class.java) { totem: Totem ->
            DisplayItem.of(totem.item)
        }.sorter(0, TotemSorter.NAME_ASCENDING.getComparator())
    }
}
package com.github.adriianh.core.ui

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.TotemRegistry
import com.github.adriianh.common.util.colorify
import com.github.adriianh.core.sorter.TotemSorter
import com.github.adriianh.core.ui.MenuHelper.FILL_ITEM
import com.github.adriianh.core.ui.icon.getClickableTotem
import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.ClickableItem
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
        menuContents.fillBorders(FILL_ITEM)

        val pagination: ObjectPagination<Totem> = menuContents.pagination("paginated_list_menu", 36)
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
                name = "&6Sorter"

                lore.addAll(listOf(
                    "",
                    "&7• &aSorted by ${cachedSorter.name}",
                    ""
                ).colorify())

                val next: TotemSorter = cachedSorter.next()
                val previous: TotemSorter = cachedSorter.previous()

                lore.addAll(listOf(
                        "",
                        "&7• &6Left Click &7to sort by ${next.name}",
                        "&7• &6Right Click &7to sort by ${previous.name}",
                        ""
                ).colorify())
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
        val lore = listOf(
            "&7",
            "&7• &6Left Click &7to get",
            "&7• &6Right Click &7to edit",
            "&7• &6Shift + Right Click &7to delete",
            "&7"
        ).colorify()

        return contents.createObjectIterator(MenuIteratorType.HORIZONTAL, 0, 0, Totem::class.java) { totem: Totem ->
            getClickableTotem(totem, lore)
        }.sorter(0, TotemSorter.NAME_ASCENDING.getComparator())
    }
}
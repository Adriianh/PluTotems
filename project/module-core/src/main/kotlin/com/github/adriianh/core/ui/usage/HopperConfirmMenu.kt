package com.github.adriianh.core.ui.usage

import com.github.adriianh.common.util.colorify
import com.github.adriianh.core.util.PluTotemsLoader.getOdalitaMenus
import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.DisplayItem
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider
import nl.odalitadevelopments.menus.menu.type.MenuType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import taboolib.platform.util.ItemBuilder
import java.util.function.Consumer

@Menu(
    title = "Confirm | Menu",
    type = MenuType.CHEST_3_ROW
)
class HopperConfirmMenu(
    private var input: Consumer<Boolean>,
    private val originMenu: PlayerMenuProvider
) : PlayerMenuProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        menuContents[11] = DisplayItem.of(ItemBuilder(Material.PAPER).apply {
            name = "&aConfirm".colorify()
            lore.addAll(listOf(
                "",
                " &7• &aClick to confirm",
                ""
            ).colorify())
        }.build())

        menuContents[15] = DisplayItem.of(ItemBuilder(Material.PAPER).apply {
            name = "&cCancel".colorify()
            lore.addAll(listOf(
                "",
                " &7• &cClick to cancel",
                ""
            ).colorify())
        }.build())

        menuContents.events().onInventoryEvent(InventoryClickEvent::class.java) { event ->
            when (event.slot) {
                11 -> {
                    input.accept(true)
                    event.whoClicked.closeInventory()
                }
                15 -> {
                    input.accept(false)
                    event.whoClicked.closeInventory()
                }
                else -> return@onInventoryEvent
            }
        }

        menuContents.events().onClose(Runnable {
            input.accept(false)
            getOdalitaMenus()?.openMenu(originMenu, player)
        })
    }
}
package com.github.adriianh.core.ui.usage

import com.github.adriianh.core.util.PluTotemsLoader.getOdalitaMenus
import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.contents.action.MenuProperty
import nl.odalitadevelopments.menus.items.DisplayItem
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider
import nl.odalitadevelopments.menus.menu.type.MenuType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

@Menu(
    title = "Input | Menu",
    type = MenuType.ANVIL
)
class AnvilInputMenu(
    private var inputConsumer: Consumer<Any?>,
    private val originMenu: PlayerMenuProvider
) : PlayerMenuProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        menuContents[0] = DisplayItem.of(ItemStack(Material.PAPER))

        menuContents.events().onInventoryEvent(PrepareAnvilEvent::class.java) {
            menuContents.actions().setProperty(MenuProperty.REPAIR_COST, 0)
        }

        menuContents.events().onInventoryEvent(
            InventoryClickEvent::class.java
        ) { event: InventoryClickEvent ->
            if (event.slot != 2) return@onInventoryEvent

            val currentItem: ItemStack? = event.currentItem
            if (currentItem == null || currentItem.type.isAir) return@onInventoryEvent

            val inventory = event.inventory as AnvilInventory
            val text = inventory.renameText

            inputConsumer.accept(text)

            event.whoClicked.closeInventory()
            getOdalitaMenus()?.openMenu(originMenu, player)
        }
    }
}
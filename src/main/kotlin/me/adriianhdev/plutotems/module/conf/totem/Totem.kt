package me.adriianhdev.plutotems.module.conf.totem

import me.adriianhdev.plutotems.module.conf.ConfigManager
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.adaptPlayer
import taboolib.common5.Coerce
import taboolib.module.kether.KetherShell
import taboolib.module.kether.printKetherErrorMessage
import taboolib.platform.util.sendLang

private val config = ConfigManager.config

data class Totem(
    val id: String,
    val item: ItemStack,
    val name: String? = null,
    val description: String? = null,
    val type: String? = null,
    val rarity: String? = null,
    val data: TotemScript
)

data class Options(
    val healthAmount: Double = config.getDouble("Totem.health"),
    val playAnimation: Boolean? = config.getBoolean("Totem.playAnimation"),
    val autoTotem: Boolean? = null,
    val isClickable: Boolean? = null,
    val isConsumable: Boolean? = null,
    val isThrowable: Boolean? = null,
    val isPickupable: Boolean? = null,
    val isPlaceable: Boolean? = null,
)

data class Condition(
    val chance: Int = 100,
    val permission: String? = null,
    val script: List<String>? = null
) {
    fun check(player: Player): Boolean {
        if (chance != 100 && (0..100).random() > chance) {
            player.sendLang("Totem-Chance-Failed")
            return false
        }
        if (permission != null && !player.hasPermission(permission) && !player.isOp) {
            player.sendLang("Totem-No-Permission")
            return false
        }
        if (script!!.isNotEmpty()) {
            val result = KetherShell.eval(script, sender = adaptPlayer(player)).thenApply {
                Coerce.toBoolean(it)
            }.exceptionally {
                it.printKetherErrorMessage()
                false
            }.get()

            if (!result) {
                player.sendLang("Totem-Script-Failed")
                return false
            }
        }
        return true
    }
}
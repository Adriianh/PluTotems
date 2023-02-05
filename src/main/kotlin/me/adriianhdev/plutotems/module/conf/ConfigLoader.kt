package me.adriianhdev.plutotems.module.conf

import me.adriianhdev.plutotems.common.util.*
import me.adriianhdev.plutotems.common.util.StringUtil.toVector
import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.*
import org.bukkit.util.Vector
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.library.xseries.XItemStack
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

object ConfigLoader {
    private val files = ArrayList<File>()
    private val configs = ArrayList<Configuration>()

    @Awake(LifeCycle.ACTIVE)
    fun loader() {
        files.clear()
        configs.clear()
        TotemManager.totems.clear()
        loadFile(File(getDataFolder(), "totem/"))
        loadConfig()
        create()
    }

    private fun create() {
        configs.forEach { config ->
            register(config)
        }
    }

    private fun loadConfig() {
        files.forEach {
            configs.add(Configuration.loadFromFile(it, Type.YAML))
        }
    }

    private fun loadFile(file: File) {
        if (file.isFile) {
            files.add(file)
        } else {
            file.listFiles()?.forEach {
                loadFile(it)
            }
        }
    }

    private fun register(config: Configuration) {
        config.getKeys(false).forEach { key ->
            val name = config.getString("$key.name")
            val description = config.getString("$key.description")
            val rarity = config.getString("$key.rarity")
            val actions = config.getStringList("$key.actions")
            val scripts = config.getStringList("$key.scripts")
            val effects = config.getStringList("$key.effects")
            val heldEffect = config.getStringList("$key.heldEffects")

            val item = config.getConfigurationSection("$key.item")!!.let {
                XItemStack.deserialize(it) { it.colorify() }
            }
            val conditions = Condition(
                config.getInt("$key.conditions.chance.value"),
                config.getString("$key.conditions.permission.value"),
                config.getStringList("$key.conditions.kether")
            )
            val options = Options(
                config.getDouble("$key.options.health"),
                config.getBoolean("$key.options.playAnimation"),
                config.getBoolean("$key.options.autoTotem"),
                config.getBoolean("$key.options.isClickable"),
                config.getBoolean("$key.options.isConsumable"),
                config.getBoolean("$key.options.isThrowable"),
                config.getBoolean("$key.options.isPickupable"),
                config.getBoolean("$key.options.isPlaceable")
            )
            val typeOptions = TypesOptions(
                config.getString("$key.types.options.giveEffect"),
                config.getString("$key.types.options.executeActions"),
                config.getString("$key.types.options.executeScripts"),
                config.getBoolean("$key.types.options.entity.runOnKill")
            )
            val typeSchematic = TotemSchematic(
                config.getString("$key.types.options.schematic.name"),
                config.getDouble("$key.types.options.schematic.rotation.x"),
                config.getDouble("$key.types.options.schematic.rotation.y"),
                config.getDouble("$key.types.options.schematic.rotation.z"),
                config.getBoolean("$key.types.options.schematic.entities"),
                config.getBoolean("$key.types.options.schematic.air"),
            )
            val types = Types(
                config.getString("$key.types.type"),
                typeOptions,
                getEntityConfig(config, key),
                typeSchematic,
                config.getBoolean("$key.types.options.entity.animation.play"),
                config.getDouble("$key.types.options.entity.animation.amplitude"),
                config.getDouble("$key.types.options.entity.animation.frequency"),
                config.getInt("$key.types.options.duration"),
                config.getDouble("$key.types.options.radius"),
            )

            val totemScript = TotemScript(item, conditions, options, types, actions, scripts, effects, heldEffect)
            val totem = Totem(
                id = key,
                item = item,
                name = name,
                description = description,
                rarity = rarity,
                data = totemScript
            ).let {
                TotemManager.addTotem(it)
            }
        }
    }

    private fun getEntityConfig(config: Configuration, key: String): TotemEntity {
        val options = "$key.types.options.entity"
        
        val direction = Vector(
            config.getDouble("$options.rotation.direction.x"),
            config.getDouble("$options.rotation.direction.y"),
            config.getDouble("$options.rotation.direction.z"),
        )
        val entityRotation = EntityRotation(
            config.getDouble("$options.rotation.yaw").toFloat(),
            config.getDouble("$options.rotation.pitch").toFloat(),
            direction
        )
        val entityPose = EntityPose(
            config.getString("$options.pose.head")?.toVector(),
            config.getString("$options.pose.body")?.toVector(),
            config.getString("$options.pose.leftArm")?.toVector(),
            config.getString("$options.pose.rightArm")?.toVector(),
            config.getString("$options.pose.leftLeg")?.toVector(),
            config.getString("$options.pose.rightLeg")?.toVector(),
        )
        val entityEquipment = EntityEquipment(
            config.getConfigurationSection("$options.equipment.helmet")?.let {
                XItemStack.deserialize(it) { it.colorify()} },
            config.getConfigurationSection("$options.equipment.chestplate")?.let {
                XItemStack.deserialize(it) { it.colorify()} },
            config.getConfigurationSection("$options.equipment.leggings")?.let {
                XItemStack.deserialize(it) { it.colorify()} },
            config.getConfigurationSection("$options.equipment.boots")?.let {
                XItemStack.deserialize(it) { it.colorify()} },
            config.getConfigurationSection("$options.equipment.mainHand")?.let {
                XItemStack.deserialize(it) { it.colorify()} },
            config.getConfigurationSection("$options.equipment.offHand")?.let {
                XItemStack.deserialize(it) { it.colorify()} },
        )

        return TotemEntity(
            type = config.getString("$options.type"),
            name = config.getString("$options.name"),
            isNameVisible = config.getBoolean("$options.nameVisible"),
            hasAI = config.getBoolean("$options.ai"),
            isCollidable = config.getBoolean("$options.collidable"),
            hasGravity = config.getBoolean("$options.gravity"),
            isInvulnerable = config.getBoolean("$options.invulnerable"),
            isVisible = config.getBoolean("$options.visible"),
            rotation = entityRotation,
            pose = entityPose,
            equipment = entityEquipment,
            hasArms = config.getBoolean("$options.arms"),
            hasBasePlate = config.getBoolean("$options.basePlate"),
            isSmall = config.getBoolean("$options.small"),
            isMarker = config.getBoolean("$options.marker"),
            isGlowing = config.getBoolean("$options.glow")
        )
    }
}
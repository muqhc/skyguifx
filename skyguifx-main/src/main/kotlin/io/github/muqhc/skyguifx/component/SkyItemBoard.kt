package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.util.div
import io.github.muqhc.skyguifx.util.plus
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemDisplay
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.AxisAngle4f
import org.joml.Vector3f

/**
 * A gui component based on item_display
 */
open class SkyItemBoard(var itemDisplay: ItemDisplay): SkyFXComponent {
    override var parent: SkyFXComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var localFloatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()

    constructor(itemStack: ItemStack?, display: SkyDisplay): this(
        (display.location.world.spawnEntity(display.location, EntityType.ITEM_DISPLAY) as ItemDisplay).apply {
            this.itemStack = itemStack
        }
    )

    private var point1_cache: Point? = null
    private var point2_cache: Point? = null
    private var display_loc_cache: Vector? = null
    private var display_dir_cache: Vector? = null

    override fun renderFx(display: SkyDisplay) {
        if (
            point1_cache != point1 ||
            point2_cache != point2 ||
            display_loc_cache != display.location.toVector() ||
            display_dir_cache != display.normalVector
        ) {
            point1_cache = point1
            point2_cache = point2
            display_loc_cache = display.location.toVector()
            display_dir_cache = display.normalVector

            itemDisplay.transformation = Transformation(
                Vector3f(0f, 0f, 0f), AxisAngle4f(0f, 0f, 0f, 0f),
                Vector3f(width.toFloat(), height.toFloat(), 0.0f), AxisAngle4f(0f, 0f, 0f, 0f),
            )
            val loc = display.getLocationOnSpace((point1+point2)/2.0)
                .toLocation(display.location.world).add(display.normalVector.clone().multiply(floatingLevel))
            loc.direction = display.normalVector
            itemDisplay.teleport(loc)
        }
    }

    override fun onRemoved() {
        itemDisplay.remove()
    }
}
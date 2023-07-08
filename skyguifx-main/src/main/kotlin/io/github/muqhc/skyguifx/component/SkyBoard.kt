package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import org.bukkit.block.data.BlockData
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.EntityType
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.AxisAngle4f
import org.joml.Vector3f

/**
 * A gui component based on block_display
 */
open class SkyBoard(var blockDisplay: BlockDisplay): SkyFXComponent {
    override var parent: SkyFXComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var localFloatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()
    override var onAfterDisabled: MutableList<()->Unit> = mutableListOf()
    override var onAfterEnabled: MutableList<()->Unit> = mutableListOf()
    override var onAfterRemoved: MutableList<() -> Unit> = mutableListOf()
    override var isDisabled: Boolean = false

    constructor(blockData: BlockData, display: SkyDisplay): this(
        (display.location.world.spawnEntity(display.location, EntityType.BLOCK_DISPLAY) as BlockDisplay).apply {
            block = blockData
        }
    )

    private var point1_cache: Point? = null
    private var point2_cache: Point? = null
    private var display_loc_cache: Vector? = null
    private var display_dir_cache: Vector? = null

    var isCaching = true

    override fun renderFx(display: SkyDisplay) {
        if (isCaching) {
            if (point1_cache == point1 &&
                point2_cache == point2 &&
                display_loc_cache == display.location.toVector() &&
                display_dir_cache == display.normalVector
            ) return
            point1_cache = point1
            point2_cache = point2
            display_loc_cache = display.location.toVector()
            display_dir_cache = display.normalVector
        }

        blockDisplay.transformation = Transformation(
            Vector3f(0f, 0f, 0f), AxisAngle4f(0f, 0f, 0f, 0f),
            Vector3f(width.toFloat(), height.toFloat(), 0.0f), AxisAngle4f(0f, 0f, 0f, 0f),
        )
        val loc = display.getLocationOnSpace(Point(point1.x, point1.y + height))
            .toLocation(display.location.world).add(display.normalVector.clone().multiply(floatingLevel))
        loc.direction = display.normalVector
        blockDisplay.teleport(loc)
    }

    override fun onRemoved() {
        blockDisplay.remove()
    }
}
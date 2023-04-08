package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import org.bukkit.block.data.BlockData
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.EntityType
import org.bukkit.util.Transformation
import org.joml.AxisAngle4f
import org.joml.Vector3f

open class SkyBoard(var blockDisplay: BlockDisplay): SkyFXComponent {
    override var parent: SkyComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var floatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()

    constructor(blockData: BlockData, display: SkyDisplay): this(
        (display.location.world.spawnEntity(display.location, EntityType.BLOCK_DISPLAY) as BlockDisplay).apply {
            block = blockData
        }
    )

    override fun renderFx(display: SkyDisplay) {
        blockDisplay.transformation = Transformation(
            Vector3f(0f,0f,0f), AxisAngle4f(0f,0f,0f,0f),
            Vector3f(width.toFloat(),height.toFloat(),0.0f), AxisAngle4f(0f,0f,0f,0f),
        )
        val loc = display.getLocationOnSpace(Point(point1.x,point1.y + height))
            .toLocation(display.location.world)
        loc.direction = display.normalVector
        blockDisplay.teleport(loc)
//        blockDisplay.location.set(loc.x,loc.y,loc.z)
//        blockDisplay.location.direction = display.normalVector
    }

    override fun onRemoved() {
        blockDisplay.remove()
    }
}
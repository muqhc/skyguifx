package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.util.LazyMutable
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
open class SkyBoard: SkyEntityComponent<BlockDisplay,SkyBoard.BoardOption> {

    class BoardOption(val blockData: BlockData): EntityOption

    constructor(blockData: BlockData, display: SkyDisplay): super() {
        prepare(BoardOption(blockData),display)
    }

    final override fun onPrepare(option: BoardOption, display: SkyDisplay) {
        entityLazy = LazyMutable {
            (display.location.world.spawnEntity(display.location, EntityType.BLOCK_DISPLAY) as BlockDisplay).apply {
                block = option.blockData
            }
        }
    }

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

        entity.transformation = Transformation(
            Vector3f(0f, 0f, 0f), AxisAngle4f(0f, 0f, 0f, 0f),
            Vector3f(width.toFloat(), height.toFloat(), 0.0f), AxisAngle4f(0f, 0f, 0f, 0f),
        )
        val loc = display.getLocationOnSpace(Point(point1.x, point1.y + height))
            .toLocation(display.location.world).add(display.normalVector.clone().multiply(floatingLevel))
        loc.direction = display.normalVector
        entity.teleport(loc)
    }

    override fun onRemoved() {
        entity.remove()
    }
}
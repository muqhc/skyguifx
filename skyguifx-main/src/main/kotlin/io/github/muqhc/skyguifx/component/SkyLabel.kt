package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.util.LazyMutable
import net.kyori.adventure.text.Component
import org.bukkit.entity.EntityType
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.AxisAngle4f
import org.joml.Vector3f

/**
 * A gui component based on text_display
 */
open class SkyLabel: SkyEntityComponent<TextDisplay,SkyLabel.LabelOption> {

    data class LabelOption(val text: Component): EntityOption

    constructor(text: Component, display: SkyDisplay): super() {
        prepare(LabelOption(text),display)
    }

    constructor(text: String, display: SkyDisplay): this(Component.text(text),display)


    final override fun onPrepare(option: LabelOption, display: SkyDisplay) {
        entityLazy = LazyMutable {
            (display.location.world.spawnEntity(display.location, EntityType.TEXT_DISPLAY) as TextDisplay).apply {
                text(option.text)
            }
        }
    }

    var scale: Point = Point(1,1)

    private var scale_cache: Point? = null
    private var point1_cache: Point? = null
    private var point2_cache: Point? = null
    private var display_loc_cache: Vector? = null
    private var display_dir_cache: Vector? = null

    var isCaching = true

    override fun renderFx(display: SkyDisplay) {
        if (isCaching){
            if (scale_cache == scale &&
                point1_cache == point1 &&
                point2_cache == point2 &&
                display_loc_cache == display.location.toVector() &&
                display_dir_cache == display.normalVector
            ) return
            scale_cache = scale
            point1_cache = point1
            point2_cache = point2
            display_loc_cache = display.location.toVector()
            display_dir_cache = display.normalVector
        }

        entity.transformation = Transformation(
            Vector3f(0f, 0f, 0f), AxisAngle4f(0f, 0f, 0f, 0f),
            Vector3f(scale.x.toFloat(), scale.y.toFloat(), 0.0f), AxisAngle4f(0f, 0f, 0f, 0f),
        )
        val loc = display.getLocationOnSpace(
            Point((point1.x + point2.x) / 2, point2.y)
        ).toLocation(display.location.world).add(display.normalVector.clone().multiply(floatingLevel))
        loc.direction = display.normalVector
        entity.teleport(loc)
    }

    override fun onRemoved() {
        entity.remove()
    }
}
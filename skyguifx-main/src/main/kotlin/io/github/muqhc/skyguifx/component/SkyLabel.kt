package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import net.kyori.adventure.text.Component
import org.bukkit.entity.EntityType
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.AxisAngle4f
import org.joml.Vector3f

open class SkyLabel(var textDisplay: TextDisplay): SkyFXComponent {
    override var parent: SkyFXComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var localFloatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()

    constructor(text: Component, display: SkyDisplay): this(
        (display.location.world.spawnEntity(display.location, EntityType.TEXT_DISPLAY) as TextDisplay).apply {
            text(text)
        }
    )

    constructor(text: String, display: SkyDisplay): this(Component.text(text),display)

    var scale: Point = Point(1,1)

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

            textDisplay.transformation = Transformation(
                Vector3f(0f, 0f, 0f), AxisAngle4f(0f, 0f, 0f, 0f),
                Vector3f(scale.x.toFloat(), scale.y.toFloat(), 0.0f), AxisAngle4f(0f, 0f, 0f, 0f),
            )
            val loc = display.getLocationOnSpace(
                Point((point1.x + point2.x) / 2, point2.y)
            ).toLocation(display.location.world).add(display.normalVector.clone().multiply(floatingLevel))
            loc.direction = display.normalVector
            textDisplay.teleport(loc)
        }
    }

    override fun onRemoved() {
        textDisplay.remove()
    }
}
package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import net.kyori.adventure.text.Component
import org.bukkit.entity.EntityType
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation
import org.joml.AxisAngle4f
import org.joml.Vector3f

open class SkyLabel(var textDisplay: TextDisplay): SkyFXComponent {
    override var parent: SkyComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var floatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()

    constructor(text: Component, display: SkyDisplay): this(
        (display.location.world.spawnEntity(display.location, EntityType.TEXT_DISPLAY) as TextDisplay).apply {
            text(text)
        }
    )

    constructor(text: String, display: SkyDisplay): this(Component.text(text),display)

    var scale: Point = Point(1,1)

    override fun renderFx(display: SkyDisplay) {
        textDisplay.transformation = Transformation(
            Vector3f(0f,0f,0f), AxisAngle4f(0f,0f,0f,0f),
            Vector3f(scale.x.toFloat(),scale.y.toFloat(),0.0f), AxisAngle4f(0f,0f,0f,0f),
        )
        val loc = display.getLocationOnSpace(
            Point((point1.x+point2.x)/2, point2.y)
        ).toLocation(display.location.world)
        loc.direction = display.normalVector
        textDisplay.teleport(loc)
    }

    override fun onRemoved() {
        textDisplay.remove()
    }
}
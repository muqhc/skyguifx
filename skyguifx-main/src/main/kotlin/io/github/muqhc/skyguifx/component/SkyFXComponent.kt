package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.util.isPointInReversed
import io.github.muqhc.skyguifx.util.plus

interface SkyFXComponent: SkyComponent {
    override val point1: Point get() = parent?.let { it.point1 + localPoint1 } ?: localPoint1
    override val point2: Point get() = parent?.let { it.point1 + localPoint2 } ?: localPoint2

    var parent: SkyComponent?
    var localPoint1: Point
    var localPoint2: Point
    var floatingLevel: Double

    val width: Double get() = localPoint2.x - localPoint1.x
    val height: Double get() = localPoint2.y - localPoint1.y

    override fun render(display: SkyDisplay) {
        display.location.add(display.normalVector.clone().multiply(floatingLevel))
        renderFx(display)
        display.location.subtract(display.normalVector.clone().multiply(floatingLevel))
    }

    fun renderFx(display: SkyDisplay)

    fun remove() {
        onRemoved()
    }

    fun onRemoved() {}

    override fun isPointIn(target: Point): Boolean = isPointInReversed(target)
}
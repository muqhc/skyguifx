package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.util.SkyEvent0
import io.github.muqhc.skyguifx.util.SkyEvent1
import io.github.muqhc.skyguifx.util.isPointInReversed
import io.github.muqhc.skyguifx.util.plus

interface SkyFXComponent: SkyComponent {
    override val point1: Point get() = parent?.let { it.point1 + localPoint1 } ?: localPoint1
    override val point2: Point get() = parent?.let { it.point1 + localPoint2 } ?: localPoint2

    var parent: SkyFXComponent?
    var localPoint1: Point
    var localPoint2: Point
    var localFloatingLevel: Double

    val floatingLevel: Double
        get() = parent?.let { it.floatingLevel + localFloatingLevel } ?: localFloatingLevel

    val width: Double get() = localPoint2.x - localPoint1.x
    val height: Double get() = localPoint2.y - localPoint1.y

    var onAfterRender: SkyEvent1<SkyDisplay,Unit>
    var onBeforeRender: SkyEvent1<SkyDisplay,Unit>
    var onAfterClicked: SkyEvent1<SkyDisplayInteractEvent,Unit>
    var onBeforeClicked: SkyEvent1<SkyDisplayInteractEvent,Unit>
    var onAfterDisabled: SkyEvent0<Unit>
    var onBeforeDisabled: SkyEvent0<Unit>
    var onAfterEnabled: SkyEvent0<Unit>
    var onBeforeEnabled: SkyEvent0<Unit>
    var onAfterRemoved: SkyEvent0<Unit>
    var onBeforeRemoved: SkyEvent0<Unit>
    var isDisabled: Boolean

    override fun render(display: SkyDisplay) {
        onBeforeRender.handle(display)
        renderFx(display)
        onAfterRender.handle(display)
    }

    fun renderFx(display: SkyDisplay) {}

    override fun click(event: SkyDisplayInteractEvent) {
        if (isDisabled) return
        onBeforeClicked.handle(event)
        super.click(event)
        onAfterClicked.handle(event)
    }

    fun clickDirect(event: SkyDisplayInteractEvent) =
        super.click(event)

    fun remove() {
        onBeforeRemoved.handle()
        onRemoved()
        onAfterRemoved.handle()
    }

    fun onRemoved() {}

    override fun isPointIn(target: Point): Boolean = isPointInReversed(target)

    fun enable() {
        onBeforeEnabled.handle()
        isDisabled = false
        onAfterEnabled.handle()
    }
    fun disable() {
        onBeforeDisabled.handle()
        isDisabled = true
        onAfterDisabled.handle()
    }
}
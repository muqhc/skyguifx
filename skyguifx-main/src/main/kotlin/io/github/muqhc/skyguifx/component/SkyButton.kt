package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point

open class SkyButton(var onClicked: (SkyDisplayInteractEvent) -> Unit): SkyFXComponent {
    override var parent: SkyFXComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var localFloatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()

    override fun renderFx(display: SkyDisplay) {}

    override fun onClicked(event: SkyDisplayInteractEvent) {
        onClicked.invoke(event)
    }
}
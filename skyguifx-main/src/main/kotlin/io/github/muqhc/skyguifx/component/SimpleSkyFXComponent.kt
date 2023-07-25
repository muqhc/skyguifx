package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.util.SkyEvent0
import io.github.muqhc.skyguifx.util.SkyEvent1

abstract class SimpleSkyFXComponent: SkyFXComponent {
    override var parent: SkyFXComponent? = null
    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var localFloatingLevel: Double = 0.0

    override var onAfterRender: SkyEvent1<SkyDisplay, Unit> = SkyEvent1()
    override var onBeforeRender: SkyEvent1<SkyDisplay, Unit> = SkyEvent1()
    override var onAfterClicked: SkyEvent1<SkyDisplayInteractEvent, Unit> = SkyEvent1()
    override var onBeforeClicked: SkyEvent1<SkyDisplayInteractEvent, Unit> = SkyEvent1()
    override var onAfterDisabled: SkyEvent0<Unit> = SkyEvent0()
    override var onBeforeDisabled: SkyEvent0<Unit> = SkyEvent0()
    override var onAfterEnabled: SkyEvent0<Unit> = SkyEvent0()
    override var onBeforeEnabled: SkyEvent0<Unit> = SkyEvent0()
    override var onAfterRemoved: SkyEvent0<Unit> = SkyEvent0()
    override var onBeforeRemoved: SkyEvent0<Unit> = SkyEvent0()
    override var isDisabled: Boolean = false
}
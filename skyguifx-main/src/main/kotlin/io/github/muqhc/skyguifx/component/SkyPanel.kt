package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import org.bukkit.block.data.BlockData

open class SkyPanel<O:SkyLayoutOption,L:SkyLayoutManager<O,L>>(override var layoutManager: L) : SkyContainer<O,L> {
    override var components: MutableList<SkyContained<O>> = mutableListOf()
    override var childrenFloatingLevel: Double = 0.01
    override var parent: SkyComponent? = null

    override var localPoint1: Point = Point(0,0)
    override var localPoint2: Point = Point(0,0)
    override var floatingLevel: Double = 0.0

    override var onAfterRender: MutableList<(SkyDisplay)->Unit> = mutableListOf()
    override var onAfterClicked: MutableList<(SkyDisplayInteractEvent)->Unit> = mutableListOf()

    override fun renderFx(display: SkyDisplay) {

    }
}
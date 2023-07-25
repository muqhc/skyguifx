package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import org.bukkit.block.data.BlockData

/**
 * A component container to group components
 */
open class SkyPanel<O:SkyLayoutOption,L:SkyLayoutManager<O,L>>(override var layoutManager: L) : SimpleSkyFXComponent(), SkyContainer<O,L> {
    override var components: MutableList<SkyContained<O>> = mutableListOf()

    override var localFloatingLevel: Double = 0.0
}
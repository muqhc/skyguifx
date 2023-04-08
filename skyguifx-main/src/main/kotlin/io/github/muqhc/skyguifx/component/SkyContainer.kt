package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import io.github.muqhc.skyguifx.util.isPointInReversed


interface SkyContainer<O:SkyLayoutOption,L:SkyLayoutManager<O,L>>: SkyFXComponent {
    var layoutManager: L
    var components: MutableList<SkyContained<O>>

    var childrenFloatingLevel: Double

    fun add(component: SkyFXComponent, option: O = layoutManager.defaultLayoutOption): O {
        val contained = SkyContained(component, option)
        layoutManager.onPreAdd(this, contained)
        component.parent = this
        components.add(contained)
        layoutManager.onAdd(this, contained)

        return option
    }

    override fun render(display: SkyDisplay) {
        layoutManager.manage(this)
        super.render(display)
        display.location.add(display.normalVector.clone().multiply(childrenFloatingLevel))
        components.forEach { it.component.render(display) }
        display.location.subtract(display.normalVector.clone().multiply(childrenFloatingLevel))
    }

    override fun remove() {
        components.forEach { it.component.remove() }
        components.clear()
        super.remove()
    }

    override fun click(event: SkyDisplayInteractEvent) {
        super.click(event)
        components.forEach {  (compo,_) ->
            if (compo.isPointInReversed(event.traceResult.hitLocationOnDisplay)) {
                compo.click(event)
            }
        }
    }
}

data class SkyContained<O:SkyLayoutOption>(val component: SkyFXComponent, var option: O)
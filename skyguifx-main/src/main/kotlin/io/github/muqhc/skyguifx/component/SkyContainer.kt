package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import io.github.muqhc.skyguifx.util.isPointInReversed


interface SkyContainer<O:SkyLayoutOption,L:SkyLayoutManager<O,L>>: SkyFXComponent {
    var layoutManager: L
    var components: MutableList<SkyContained<O>>

    fun add(component: SkyFXComponent, option: O = layoutManager.defaultLayoutOption.clone() as O): O {
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
        components.forEach {
            it.component.render(display)
        }
    }

    override fun remove() {
        components.forEach { it.component.remove() }
        components.clear()
        super.remove()
    }

    fun remove(component: SkyFXComponent) {
        component.remove()
        components.removeIf { it == component }
    }

    override fun click(event: SkyDisplayInteractEvent) {
        if (isDisabled) return
        super.click(event)
        components.forEach {  (compo,_) ->
            if (compo.isPointInReversed(event.traceResult.hitLocationOnDisplay)) {
                compo.click(event)
            }
        }
    }
}

data class SkyContained<O:SkyLayoutOption>(val component: SkyFXComponent, var option: O)
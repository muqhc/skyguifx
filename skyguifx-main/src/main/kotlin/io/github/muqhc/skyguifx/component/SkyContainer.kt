package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
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
        onBeforeRender.handle(display)
        layoutManager.manage(this)
        renderFx(display)
        components.forEach {
            it.component.render(display)
        }
        onAfterRender.handle(display)
    }

    override fun remove() {
        onBeforeRemoved.handle()
        components.forEach { it.component.remove() }
        components.clear()
        onRemoved()
        onAfterRemoved.handle()
    }

    fun remove(component: SkyFXComponent) {
        component.remove()
        components.removeIf { it == component }
    }

    override fun click(event: SkyDisplayInteractEvent) {
        if (isDisabled) return
        onBeforeClicked.handle(event)
        clickDirect(event)
        components
            .filter { (compo,_) -> compo.isPointInReversed(event.traceResult.hitLocationOnDisplay) }
            .map { (compo,_) -> compo.click(event) }
        onAfterClicked.handle(event)
    }
}

data class SkyContained<O:SkyLayoutOption>(val component: SkyFXComponent, var option: O)
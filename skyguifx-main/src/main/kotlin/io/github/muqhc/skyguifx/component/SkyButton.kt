package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point

/**
 * A button that can listen player interaction(click)
 */
open class SkyButton(var onClicked: (SkyDisplayInteractEvent) -> Unit): SimpleSkyFXComponent() {

    override fun renderFx(display: SkyDisplay) {}

    override fun onClicked(event: SkyDisplayInteractEvent) {
        onClicked.invoke(event)
    }
}
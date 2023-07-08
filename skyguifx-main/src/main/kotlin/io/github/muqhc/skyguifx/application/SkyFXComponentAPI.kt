package io.github.muqhc.skyguifx.application

import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent

class SkyFXComponentAPI(
    val application: SkyApplication, val ownerComponent: SkyContainer<*,*>, val component: SkyFXComponent
) {
    fun close() {
        ownerComponent.remove(component)
    }
}
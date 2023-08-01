package io.github.muqhc.skyguifx.application

import io.github.muqhc.skyguifx.component.SkyContained
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent

class SkyApplicationCloser(
    val application: SkyApplication<*>, val ownerComponent: SkyContainer<*,*>, val containedList: List<SkyContained<*>>
) {
    fun close() {
        containedList.forEach {
            ownerComponent.remove(it.component)
        }
    }
}
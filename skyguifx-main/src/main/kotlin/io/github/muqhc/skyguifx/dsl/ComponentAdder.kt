package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skyguifx.component.SkyContained
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

interface ComponentAdder<O : SkyLayoutOption, L : SkyLayoutManager<O, L>> {
    fun add(ownerContainer: SkyContainer<O,L>, component: SkyFXComponent, option: O) {
        ownerContainer.add(component,option)
    }

    class Default<O : SkyLayoutOption, L : SkyLayoutManager<O, L>> : ComponentAdder<O,L>
}
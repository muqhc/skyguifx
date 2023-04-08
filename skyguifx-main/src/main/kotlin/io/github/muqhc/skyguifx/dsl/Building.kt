package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skyguifx.SkyFXDisplay
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import io.github.muqhc.skyguifx.layout.SkyPlainFieldOption

class SkyGuiBuildingInstance<C:SkyContainer<O,L>,O:SkyLayoutOption,L:SkyLayoutManager<O,L>>(
    val rootContainer: C,
    val init: ContainerConfigureScope<C,SkyLayoutOption,O,L>.() -> Unit
) {
    fun build(display: SkyDisplay): C {
        val scope = ContainerConfigureScope<C,SkyLayoutOption,O,L>(rootContainer,SkyPlainFieldOption(),display)
        scope.init()
        return rootContainer
    }
}

fun <C:SkyContainer<O,L>,O:SkyLayoutOption,L:SkyLayoutManager<O,L>> C.skygui(
    init: ContainerConfigureScope<C,SkyLayoutOption,O,L>.() -> Unit
): SkyGuiBuildingInstance<C,O,L> {
    return SkyGuiBuildingInstance(this,init)
}

fun <C:SkyContainer<O,L>,O:SkyLayoutOption,L:SkyLayoutManager<O,L>> C.skyguiBuild(
    display: SkyDisplay,
    init: ContainerConfigureScope<C,SkyLayoutOption,O,L>.() -> Unit
): C {
    return SkyGuiBuildingInstance(this,init).build(display)
}
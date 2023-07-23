package io.github.muqhc.skyguifx.application

import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

abstract class SkyApplication {

    internal val dataContainer: MutableMap<String,MutableState<Any?>> = mutableMapOf()

    fun <T> remember(data: T) =
        RememberByApp(this,data)

    open fun <O:SkyLayoutOption> show(ownerComponent: SkyContainer<O,*>, option: O): SkyFXComponentAPI {
        val compo = entry(this)
        ownerComponent.add(compo,option)
        return SkyFXComponentAPI(this, ownerComponent,compo)
    }

    abstract val entry: (SkyApplication) -> SkyFXComponent

}







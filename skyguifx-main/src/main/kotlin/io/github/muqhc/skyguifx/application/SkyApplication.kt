package io.github.muqhc.skyguifx.application

import io.github.muqhc.skyguifx.SkyFXDisplay
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.dsl.SkyGuiBuildingInstance
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

abstract class SkyApplication {

    internal val dataContainer: MutableMap<String,MutableState<Any?>> = mutableMapOf()
    protected var compoAPI: SkyFXComponentAPI? = null

    fun <T> remember(data: T) =
        Remember(this,data)

    open fun <O:SkyLayoutOption> show(ownerComponent: SkyContainer<O,*>, option: O): SkyFXComponentAPI {
        val compo = entry(this)
        ownerComponent.add(compo,option)
        compoAPI = SkyFXComponentAPI(this, ownerComponent,compo)
        return compoAPI!!
    }

    fun close(): Boolean {
        if (compoAPI == null) return false
        compoAPI!!.close()
        compoAPI = null
        return true
    }

    abstract val entry: (SkyApplication) -> SkyFXComponent

}







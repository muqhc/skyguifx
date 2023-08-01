package io.github.muqhc.skyguifx.application

import io.github.muqhc.skyguifx.component.SkyContained
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

abstract class SkyApplication<O:SkyLayoutOption> {

    internal val dataContainer: MutableMap<String,MutableState<Any?>> = mutableMapOf()

    fun <T> remember(data: T) =
        RememberByApp(this,data)

    open fun show(ownerComponent: SkyContainer<O,*>): SkyApplicationCloser {
        val containedList = entry(this)
        containedList.forEach {
            ownerComponent.add(it.component,it.option)
        }
        return SkyApplicationCloser(this, ownerComponent, containedList)
    }

    abstract val entry: (SkyApplication<O>) -> List<SkyContained<O>>

}







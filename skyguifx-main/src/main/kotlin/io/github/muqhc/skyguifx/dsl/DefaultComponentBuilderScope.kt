package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

open class ComponentConfigureScope<C:SkyFXComponent,O:SkyLayoutOption>(val compo: C, var option: O, val display: SkyDisplay) {
    fun option(configure: O.() -> Unit) = option.configure()
    fun init(configure: C.() -> Unit) = compo.configure()
}

open class ContainerConfigureScope<
    C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    >(compo: C, option: O, display: SkyDisplay): ComponentConfigureScope<C,O>(compo, option, display) {
    var defaultOption: OO
        get() = compo.layoutManager.defaultLayoutOption
        set(value) {
            compo.layoutManager.defaultLayoutOption = value
        }

    fun <CC:SkyFXComponent> add(component: CC, option: OO = compo.layoutManager.defaultLayoutOption.clone() as OO, configure: ComponentConfigureScope<CC,OO>.() -> Unit): CC {
        ComponentConfigureScope(component,option,display).configure()
        compo.add(component, option)
        return component
    }
    fun <CC:SkyContainer<OOO,LL>,OOO:SkyLayoutOption,LL:SkyLayoutManager<OOO,LL>> addContainer(component: CC, option: OO = compo.layoutManager.defaultLayoutOption.clone() as OO, configure: ContainerConfigureScope<CC,OO,OOO,LL>.() -> Unit): CC {
        ContainerConfigureScope(component,option,display).configure()
        compo.add(component, option)
        return component
    }

}
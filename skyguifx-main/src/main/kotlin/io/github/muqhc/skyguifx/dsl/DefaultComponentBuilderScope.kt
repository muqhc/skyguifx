package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skyguifx.component.EntityOption
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyEntityComponent
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import io.github.muqhc.skyguifx.util.promise
import io.github.muqhc.skyguifx.util.volatile1
import org.bukkit.entity.Entity

open class ComponentConfigureScope<C:SkyFXComponent,O:SkyLayoutOption>(
    val compo: C, var option: O, val display: SkyDisplay, val parentScope: ComponentConfigureScope<*,*>?
) {
    val namespace: String? get() = parentScope?.let { it.namespace + "." + localNamespace } ?: localNamespace
    var localNamespace: String? = compo::class.simpleName

    fun option(configure: O.() -> Unit) = option.configure()
    fun init(configure: C.() -> Unit) = compo.configure()
}

open class EntityComponentConfigureScope<C:SkyEntityComponent<E,EO>,O:SkyLayoutOption,E:Entity,EO:EntityOption>(
    compo: C, option: O, display: SkyDisplay, parentScope: ComponentConfigureScope<*,*>?
) : ComponentConfigureScope<C,O>(compo, option, display, parentScope) {
    fun entity(configure: E.() -> Unit) {
        compo.onBeforeRender promise {
            compo.entity.configure()
        }
    }
}

open class ContainerConfigureScope<C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>>(
    compo: C, option: O, display: SkyDisplay, parentScope: ComponentConfigureScope<*,*>?,
    val adder: ComponentAdder<OO,L> = ComponentAdder.Default()
): ComponentConfigureScope<C,O>(compo, option, display, parentScope) {
    var defaultOption: OO
        get() = compo.layoutManager.defaultLayoutOption
        set(value) {
            compo.layoutManager.defaultLayoutOption = value
        }

    var additionalFloatingLevel: Double = 0.03

    fun <CC:SkyFXComponent> add(component: CC, option: OO = compo.layoutManager.defaultLayoutOption.clone() as OO, configure: ComponentConfigureScope<CC,OO>.() -> Unit): CC {
        ComponentConfigureScope(component,option,display,this).configure()
        adder.add(compo, component, option)
        component.localFloatingLevel += additionalFloatingLevel
        return component
    }
    fun <CC:SkyContainer<OOO,LL>,OOO:SkyLayoutOption,LL:SkyLayoutManager<OOO,LL>> addContainer(component: CC, option: OO = compo.layoutManager.defaultLayoutOption.clone() as OO, configure: ContainerConfigureScope<CC,OO,OOO,LL>.() -> Unit): CC {
        ContainerConfigureScope(component,option,display,this)
            .also { it.additionalFloatingLevel = additionalFloatingLevel }
            .configure()
        adder.add(compo, component, option)
        component.localFloatingLevel += additionalFloatingLevel
        return component
    }
}
package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skyguifx.application.SkyApplication
import io.github.muqhc.skyguifx.application.SkyApplicationCloser
import io.github.muqhc.skyguifx.component.SkyContained
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

abstract class SkyApplicationInDsl<C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>>(
    val scope: ContainerConfigureScope<C,O,OO,L>
): SkyApplication<OO>() {
    fun show(): SkyApplicationCloser {
        return show(scope.compo)
    }
}

open class SkyApplicationScopeInDsl<C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>>(
    val scope: ContainerConfigureScope<C,O,OO,L>,
    val ownerApplication: SkyApplication<OO>,
    val adderByAppScope: Adder<C,O,OO,L> = Adder(scope)
): ContainerConfigureScope<C,O,OO,L>(scope.compo,scope.option,scope.display,adderByAppScope) {
    fun <T> remember(data: T) = ownerApplication.remember(data)

    class Adder<C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>>(
        scope: ContainerConfigureScope<C,O,OO,L>
    ): ComponentAdder<OO,L> {
        val controllingCompoList: MutableList<SkyContained<OO>> = mutableListOf()

        override fun add(ownerContainer: SkyContainer<OO, L>, component: SkyFXComponent, option: OO) {
            controllingCompoList.add(SkyContained(component, option))
            super.add(ownerContainer, component, option)
        }
    }
}

fun <O:SkyLayoutOption> skyguiAppInstance(
    entry: SkyApplication<O>.() -> List<SkyContained<O>>
) =
    object : SkyApplication<O>() {
        override val entry: (SkyApplication<O>) -> List<SkyContained<O>> = entry
    }

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.application(
    entry: SkyApplicationScopeInDsl<C,O,OO,L>.() -> Unit
) =
    object : SkyApplicationInDsl<C,O,OO,L>(this) {
        override val entry: (SkyApplication<OO>) -> List<SkyContained<OO>> = {
            val appDsl = SkyApplicationScopeInDsl(scope,this)
            appDsl.entry()
            appDsl.adderByAppScope.controllingCompoList
        }
    }



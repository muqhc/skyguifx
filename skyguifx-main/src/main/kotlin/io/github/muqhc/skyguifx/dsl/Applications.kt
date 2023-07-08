package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skyguifx.SkyFXDisplay
import io.github.muqhc.skyguifx.application.SkyApplication
import io.github.muqhc.skyguifx.application.SkyFXComponentAPI
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption

abstract class SkyApplicationInDsl<C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>>(
    val scope: ContainerConfigureScope<C,O,OO,L>
): SkyApplication() {
    @Deprecated(
        "You shouldn't use SkyApplication#show in skygui dsl",
        ReplaceWith(
            "show()"
        ),
        DeprecationLevel.ERROR
    )
    override fun <O : SkyLayoutOption> show(ownerComponent: SkyContainer<O, *>, option: O): SkyFXComponentAPI {
        return super.show(ownerComponent, option)
    }

    fun show(): SkyFXComponentAPI {
        val compo = entry(this)
        compoAPI = SkyFXComponentAPI(this, scope.compo, compo)
        return compoAPI!!
    }
}

fun skyguiAppInstance(
    entry: SkyApplication.() -> SkyFXComponent
) =
    object : SkyApplication() {
        override val entry: (SkyApplication) -> SkyFXComponent = entry
    }

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.application(
    entry: SkyApplication.() -> SkyFXComponent
) =
    object : SkyApplicationInDsl<C,O,OO,L>(this) {
        override val entry: (SkyApplication) -> SkyFXComponent = entry
    }



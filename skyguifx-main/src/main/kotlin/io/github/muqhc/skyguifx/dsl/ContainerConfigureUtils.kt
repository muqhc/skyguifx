package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.layout.*

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,OOO:SkyLayoutOption,L:SkyLayoutManager<OO,L>,LL:SkyLayoutManager<OOO,LL>
    > ContainerConfigureScope<C,O,OO,L>.panel(
    layoutManager: LL,
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<OOO,LL>,OO,OOO,LL>.() -> Unit = {}
): SkyPanel<OOO,LL> {
    return addContainer(SkyPanel(layoutManager),option,configure)
}

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.plainField(
    layoutManager: SkyPlainFieldLayout = SkyPlainFieldLayout(),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<SkyPlainFieldOption,SkyPlainFieldLayout>,OO,SkyPlainFieldOption,SkyPlainFieldLayout>.() -> Unit = {}
): SkyPanel<SkyPlainFieldOption,SkyPlainFieldLayout> {
    return panel(layoutManager,option,configure)
}

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.paddingBox(
    layoutManager: SkyPaddingBoxLayout = SkyPaddingBoxLayout(),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout>,OO,SkyPaddingBoxOption,SkyPaddingBoxLayout>.() -> Unit = {}
): SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout> {
    return panel(layoutManager,option,configure)
}

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.aligningBox(
    layoutManager: SkyAligningBoxLayout = SkyAligningBoxLayout(),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<SkyAligningBoxOption,SkyAligningBoxLayout>,OO,SkyAligningBoxOption,SkyAligningBoxLayout>.() -> Unit = {}
): SkyPanel<SkyAligningBoxOption,SkyAligningBoxLayout> {
    return panel(layoutManager,option,configure)
}

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.ratioField(
    layoutManager: SkyRatioFieldLayout = SkyRatioFieldLayout(),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<SkyRatioFieldOption,SkyRatioFieldLayout>,OO,SkyRatioFieldOption,SkyRatioFieldLayout>.() -> Unit = {}
): SkyPanel<SkyRatioFieldOption,SkyRatioFieldLayout> {
    return panel(layoutManager,option,configure)
}

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.gridField(
    layoutManager: SkyGridFieldLayout = SkyGridFieldLayout(),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<SkyGridFieldOption,SkyGridFieldLayout>,OO,SkyGridFieldOption,SkyGridFieldLayout>.() -> Unit = {}
): SkyPanel<SkyGridFieldOption,SkyGridFieldLayout> {
    return panel(layoutManager,option,configure)
}

fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>
    > ContainerConfigureScope<C,O,OO,L>.simpleGridField(
    rows: Int = 1,
    columns: Int = 1,
    layoutManager: SkyGridFieldLayout = SkySimpleGridLayout(rows,columns),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ContainerConfigureScope<SkyPanel<SkyGridFieldOption,SkyGridFieldLayout>,OO,SkyGridFieldOption,SkyGridFieldLayout>.() -> Unit = {}
): SkyPanel<SkyGridFieldOption,SkyGridFieldLayout> {
    return panel(layoutManager,option,configure)
}


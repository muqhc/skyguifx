package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skyguifx.component.*
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import net.kyori.adventure.text.Component
import org.bukkit.block.data.BlockData
import org.bukkit.inventory.ItemStack

/** add component */
fun <C:SkyContainer<OO,L>,CC:SkyFXComponent,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.component(
    component: CC,
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ComponentConfigureScope<CC,OO>.() -> Unit = {}
): CC {
    return add(component,option,configure)
}

/** add SkyLabel */
fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.label(
    text: Component,
    component: SkyLabel = SkyLabel(text,this.display),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ComponentConfigureScope<SkyLabel,OO>.() -> Unit = {}
): SkyLabel {
    return component(component,option,configure)
}

/** add SkyBoard */
fun <C: SkyContainer<OO,L>,O: SkyLayoutOption,OO: SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.board(
    blockData: BlockData,
    component: SkyBoard = SkyBoard(blockData,this.display),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ComponentConfigureScope<SkyBoard,OO>.() -> Unit = {}
): SkyBoard {
    return component(component,option,configure)
}

/** add SkyButton */
fun <C: SkyContainer<OO,L>,O: SkyLayoutOption,OO: SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.button(
    onClicked: (SkyDisplayInteractEvent) -> Unit = {},
    component: SkyButton = SkyButton(onClicked),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ComponentConfigureScope<SkyButton,OO>.() -> Unit = {}
): SkyButton {
    return component(component,option,configure)
}

/** add SkyItemBoard */
fun <C: SkyContainer<OO,L>,O: SkyLayoutOption,OO: SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.itemBoard(
    itemStack: ItemStack? = null,
    component: SkyItemBoard = SkyItemBoard(itemStack,this.display),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ComponentConfigureScope<SkyItemBoard,OO>.() -> Unit = {}
): SkyItemBoard {
    return component(component,option,configure)
}





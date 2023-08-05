package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skyguifx.component.*
import io.github.muqhc.skyguifx.layout.SkyLayoutManager
import io.github.muqhc.skyguifx.layout.SkyLayoutOption
import net.kyori.adventure.text.Component
import org.bukkit.block.data.BlockData
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.TextDisplay
import org.bukkit.inventory.ItemStack

/** add component */
fun <S:ComponentConfigureScope<CC,OO>,C:SkyContainer<OO,L>,CC:SkyFXComponent,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.component(
    scope: S,
    configure: S.() -> Unit = {}
): CC {
    return add(scope,configure)
}

/** add SkyLabel */
fun <C:SkyContainer<OO,L>,O:SkyLayoutOption,OO:SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.label(
    text: Component,
    component: SkyLabel = SkyLabel(text,this.display),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: EntityComponentConfigureScope<SkyLabel,OO,TextDisplay,SkyLabel.LabelOption>.() -> Unit = {}
): SkyLabel {
    return component(EntityComponentConfigureScope(component,option,display,this),configure)
}

/** add SkyBoard */
fun <C: SkyContainer<OO,L>,O: SkyLayoutOption,OO: SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.board(
    blockData: BlockData,
    component: SkyBoard = SkyBoard(blockData,this.display),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: EntityComponentConfigureScope<SkyBoard,OO,BlockDisplay,SkyBoard.BoardOption>.() -> Unit = {}
): SkyBoard {
    return component(EntityComponentConfigureScope(component,option,display,this),configure)
}

/** add SkyButton */
fun <C: SkyContainer<OO,L>,O: SkyLayoutOption,OO: SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.button(
    onClicked: (SkyDisplayInteractEvent) -> Unit = {},
    component: SkyButton = SkyButton(onClicked),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: ComponentConfigureScope<SkyButton,OO>.() -> Unit = {}
): SkyButton {
    return component(ComponentConfigureScope(component,option,display,this),configure)
}

/** add SkyItemBoard */
fun <C: SkyContainer<OO,L>,O: SkyLayoutOption,OO: SkyLayoutOption,L:SkyLayoutManager<OO,L>> ContainerConfigureScope<C,O,OO,L>.itemBoard(
    itemStack: ItemStack? = null,
    component: SkyItemBoard = SkyItemBoard(itemStack,this.display),
    option: OO = this.compo.layoutManager.defaultLayoutOption.clone() as OO,
    configure: EntityComponentConfigureScope<SkyItemBoard,OO,ItemDisplay,SkyItemBoard.ItemBoardOption>.() -> Unit = {}
): SkyItemBoard {
    return component(EntityComponentConfigureScope(component,option,display,this),configure)
}





package io.github.muqhc.skyguifx.dsl

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.layout.*

class SkyGuiBuildingInstance<C:SkyContainer<O,L>,O:SkyLayoutOption,L:SkyLayoutManager<O,L>>(
    val rootContainer: C,
    val init: ContainerConfigureScope<C,SkyLayoutOption,O,L>.() -> Unit
): (SkyDisplay) -> C {
    fun build(display: SkyDisplay): C {
        val scope = ContainerConfigureScope<C,SkyLayoutOption,O,L>(rootContainer,SkyPlainFieldOption(),display,null)
        scope.init()
        return rootContainer
    }

    override fun invoke(display: SkyDisplay): C = build(display)
}

/** make building instance to configure SkyContainer such as SkyPanel. */
fun <C:SkyContainer<O,L>,O:SkyLayoutOption,L:SkyLayoutManager<O,L>> C.skygui(
    init: ContainerConfigureScope<C,SkyLayoutOption,O,L>.() -> Unit
): SkyGuiBuildingInstance<C,O,L> {
    return SkyGuiBuildingInstance(this,init)
}

/** straightly configure SkyContainer such as SkyPanel. */
fun <C:SkyContainer<O,L>,O:SkyLayoutOption,L:SkyLayoutManager<O,L>> C.skyguiBuild(
    display: SkyDisplay,
    init: ContainerConfigureScope<C,SkyLayoutOption,O,L>.() -> Unit
): C {
    return SkyGuiBuildingInstance(this,init).build(display)
}




fun skyPanelBuilding(
    size: Point = Point(0,0),
    init: ContainerConfigureScope<
        SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout>,SkyLayoutOption,
        SkyPaddingBoxOption,SkyPaddingBoxLayout
        >.() -> Unit = {}
): SkyGuiBuildingInstance<
    SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout>, SkyPaddingBoxOption,SkyPaddingBoxLayout
    > {
    return SkyGuiBuildingInstance(SkyPanel(SkyPaddingBoxLayout())) {
        compo.localPoint2 = size
        init()
    }
}

fun skyPanelBuilding(
    x: Double, y: Double,
    init: ContainerConfigureScope<
        SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout>,SkyLayoutOption,
        SkyPaddingBoxOption,SkyPaddingBoxLayout
        >.() -> Unit = {}
) = skyPanelBuilding(Point(x,y),init)

fun SkyDisplay.buildSkyPanel(
    size: Point = Point(0,0),
    init: ContainerConfigureScope<
        SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout>,SkyLayoutOption,
        SkyPaddingBoxOption,SkyPaddingBoxLayout
        >.() -> Unit = {}
) = skyPanelBuilding(size,init).build(this)

fun SkyDisplay.buildSkyPanel(
    x: Double, y: Double,
    init: ContainerConfigureScope<
        SkyPanel<SkyPaddingBoxOption,SkyPaddingBoxLayout>,SkyLayoutOption,
        SkyPaddingBoxOption,SkyPaddingBoxLayout
        >.() -> Unit = {}
) = buildSkyPanel(Point(x,y),init)


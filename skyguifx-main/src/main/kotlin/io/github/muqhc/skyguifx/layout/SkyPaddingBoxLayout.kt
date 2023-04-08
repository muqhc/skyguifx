package io.github.muqhc.skyguifx.layout

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.component.SkyContainer
import kotlin.math.max

open class SkyPaddingBoxLayout: SkyLayoutManager<SkyPaddingBoxOption,SkyPaddingBoxLayout> {
    override var defaultLayoutOption: SkyPaddingBoxOption = SkyPaddingBoxOption()

    override fun manage(container: SkyContainer<SkyPaddingBoxOption,SkyPaddingBoxLayout>) {
        container.components.forEach { (compo,option) ->
            compo.localPoint1 = Point(option.paddingLeft, option.paddingTop)
            compo.localPoint2 =
                Point(container.width - option.paddingRight, container.height - option.paddingBottom)
        }
    }
}

open class SkyPaddingBoxOption: SkyLayoutOption {
    var paddingTop: Double = 0.0
    var paddingBottom: Double = 0.0
    var paddingLeft: Double = 0.0
    var paddingRight: Double = 0.0

    var padding: Double
        get() = listOf(paddingTop,paddingBottom,paddingLeft,paddingRight).max()
        set(value) {
            paddingTop = value
            paddingBottom = value
            paddingLeft = value
            paddingRight = value
        }

    override fun clone(): SkyLayoutOption =
        SkyPaddingBoxOption().also {
            it.paddingTop = paddingTop
            it.paddingBottom = paddingBottom
            it.paddingLeft = paddingLeft
            it.paddingRight = paddingRight
        }
}
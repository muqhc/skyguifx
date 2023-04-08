package io.github.muqhc.skyguifx.layout

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.util.times

open class SkyRatioFieldLayout: SkyLayoutManager<SkyRatioFieldOption,SkyRatioFieldLayout> {
    override var defaultLayoutOption: SkyRatioFieldOption = SkyRatioFieldOption()

    override fun manage(container: SkyContainer<SkyRatioFieldOption,SkyRatioFieldLayout>) {
        val fullRectangle = Point(container.width,container.height)
        container.components.forEach { (compo,option) ->
            compo.localPoint1 = fullRectangle * option.ratioPoint1
            compo.localPoint2 = fullRectangle * option.ratioPoint2
        }
    }
}

open class SkyRatioFieldOption: SkyLayoutOption {
    var ratioPoint1: Point = Point(0,0)
    var ratioPoint2: Point = Point(1,1)

    override fun clone(): SkyLayoutOption =
        SkyRatioFieldOption().also {
            it.ratioPoint1 = ratioPoint1
            it.ratioPoint2 = ratioPoint2
        }
}
package io.github.muqhc.skyguifx.layout

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.util.IntPoint
import io.github.muqhc.skyguifx.util.times

open class SkyGridFieldLayout: SkyLayoutManager<SkyGridFieldOption,SkyGridFieldLayout> {
    override var defaultLayoutOption: SkyGridFieldOption = SkyGridFieldOption()

    var horizontalGridPoints: MutableList<Double> = mutableListOf()
    var verticalGridPoints: MutableList<Double> = mutableListOf()

    override fun manage(container: SkyContainer<SkyGridFieldOption,SkyGridFieldLayout>) {
        val fullRectangle = Point(container.width,container.height)
        container.components.forEach { (compo,option) ->
            compo.localPoint1 = fullRectangle * Point(
                horizontalGridPoints[option.gridPoint1.x],
                verticalGridPoints[option.gridPoint1.y]
            )
            compo.localPoint2 = fullRectangle * Point(
                horizontalGridPoints[option.gridPoint2.x],
                verticalGridPoints[option.gridPoint2.y]
            )
        }
    }
}

open class SkyGridFieldOption: SkyLayoutOption {
    var gridPoint1: IntPoint = IntPoint(0,0)
    var gridPoint2: IntPoint = IntPoint(0,0)

    override fun clone(): SkyLayoutOption =
        SkyGridFieldOption().also {
            it.gridPoint1 = gridPoint1
            it.gridPoint2 = gridPoint2
        }
}


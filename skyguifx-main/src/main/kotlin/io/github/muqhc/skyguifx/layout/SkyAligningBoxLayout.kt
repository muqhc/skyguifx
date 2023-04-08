package io.github.muqhc.skyguifx.layout

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.component.SkyContainer
import io.github.muqhc.skyguifx.util.Alignment
import io.github.muqhc.skyguifx.util.AlignmentLinear


open class SkyAligningBoxLayout: SkyLayoutManager<SkyAligningBoxOption,SkyAligningBoxLayout> {
    override var defaultLayoutOption: SkyAligningBoxOption = SkyAligningBoxOption()

    override fun manage(container: SkyContainer<SkyAligningBoxOption,SkyAligningBoxLayout>) {
        container.components.forEach { (compo,option) ->
            val (horizontal1, horizontal2) =
                calcLinearPosition(option.alignment.horizontal, container.width, option.width)
            val (vertical1, vertical2) =
                calcLinearPosition(option.alignment.vertical, container.height, option.height)

            compo.localPoint1 = Point(horizontal1,vertical1)
            compo.localPoint2 = Point(horizontal2,vertical2)
        }
    }

    private fun calcLinearPosition(alignmentLinear: AlignmentLinear, containerLength: Double, optionLength: Double) =
        when(alignmentLinear) {
            AlignmentLinear.Start ->
                0.0 to (optionLength)
            AlignmentLinear.Middle ->
                (containerLength/2 - optionLength/2) to (containerLength/2 + optionLength/2)
            AlignmentLinear.End ->
                (containerLength - optionLength) to containerLength
        }

}

open class SkyAligningBoxOption: SkyLayoutOption {
    var alignment = Alignment.MiddleCenter
    var width: Double = 0.0
    var height: Double = 0.0

    override fun clone(): SkyLayoutOption =
        SkyAligningBoxOption().also {
            it.alignment = alignment
            it.width = width
            it.height = height
        }
}


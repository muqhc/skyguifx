package io.github.muqhc.skyguifx

import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.util.plus
import org.bukkit.Location
import org.bukkit.util.Vector

open class SkyFXSimpleDisplay(override val location: Location, override val normalVector: Vector) : SkyFXDisplay {
    override val components: MutableList<SkyComponent> = mutableListOf()

    fun add(compo: SkyFXComponent, point1: Point, point2: Point) {
        compo.localPoint1 = point1
        compo.localPoint2 = point2
        add(compo)
    }
    fun add(compo: SkyFXComponent, point: Point, width: Double, height: Double) {
        add(compo, point, point + Point(width,height))
    }

}
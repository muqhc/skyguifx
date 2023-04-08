package io.github.muqhc.skyguifx

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skygui.util.Vector2D
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.util.isPointInReversed
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

interface SkyFXDisplay: SkyDisplay {
    override val displayXAxis: Vector
        get() = super.displayXAxis.multiply(-1)

    fun clear() {
        components.forEach { if (it is SkyFXComponent) it.remove() }
        components.clear()
    }

    fun remove(component: SkyComponent) {
        if (component is SkyFXComponent) component.remove()
        components.remove(component)
    }

    fun add(component: SkyComponent) {
        onPreAdd(component)
        components.add(component)
        onAdd(component)
    }
    fun onPreAdd(component: SkyComponent) {}
    fun onAdd(component: SkyComponent) {}

    override fun getLocationOnDisplay(locationOnSpace: Vector): Point {
        val local_vec = locationOnSpace.clone().subtract(this.location.toVector())
        val origin_len = local_vec.length()
        val angle =
            if (displayYAxis.angle(local_vec) < (PI/2)) displayXAxis.angle(local_vec)
            else PI - displayXAxis.angle(local_vec) + PI
        val normal_point = Vector2D(cos(angle.toDouble()), sin(angle.toDouble()))
        val result = normal_point * origin_len

        return result.toPoint()
    }

    override fun click(event: SkyDisplayInteractEvent) {
        if (event.traceResult.isFaceToFace) components.forEach {
            if (it.isPointInReversed(event.traceResult.hitLocationOnDisplay)) {
                it.click(event)
            }
        }
    }


}
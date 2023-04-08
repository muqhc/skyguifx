package io.github.muqhc.skyguifx

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.event.SkyDisplayInteractEvent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skygui.util.SkyRayTraceResult
import io.github.muqhc.skygui.util.Vector2D
import io.github.muqhc.skyguifx.component.SkyFXComponent
import io.github.muqhc.skyguifx.util.isPointInReversed
import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

interface SkyFXDisplay: SkyDisplay {
    override val displayXAxis: Vector
        get() = super.displayXAxis.multiply(-1)

    var hitDistanceLimit: Double?
    var interactDistanceLimit: Double?

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
        if (interactDistanceLimit != null)
            if (event.player.location.distance(location) > interactDistanceLimit!!)
                return
        if (!event.traceResult.isFaceToFace) return
        if (hitDistanceLimit != null)
            if (event.traceResult.hitLocation.distance(location) > hitDistanceLimit!!)
                return
        if (event.player.location.toVector()
            .subtract(event.traceResult.hitLocation.toVector()).angle(normalVector) > (PI/2))
            return
        components.forEach {
            if (it.isPointInReversed(event.traceResult.hitLocationOnDisplay)) {
                it.click(event)
            }
        }
    }

    override fun rayTrace(posX: Double, posY: Double, posZ: Double, dirX: Double, dirY: Double, dirZ: Double): SkyRayTraceResult {
        return object : SkyRayTraceResult {
            override val hitLocation: Location by lazy {
                Location(this@SkyFXDisplay.location.world, 0.0, 0.0, 0.0).apply {
                    val incl =
                        normalVector.clone().dot(location.toVector().subtract(Vector(posX, posY, posZ))) /
                            normalVector.clone().dot(Vector(dirX, dirY, dirZ))

                    val result = Vector(posX, posY, posZ).add(Vector(dirX, dirY, dirZ).multiply(incl))

                    x = result.x
                    y = result.y
                    z = result.z
                }
            }
            override val hitLocationOnDisplay: Point by lazy { getLocationOnDisplay(hitLocation.toVector()) }
            override val isFaceToFace: Boolean = normalVector.angle(Vector(dirX, dirY, dirZ)) > (PI/2)
        }
    }
}
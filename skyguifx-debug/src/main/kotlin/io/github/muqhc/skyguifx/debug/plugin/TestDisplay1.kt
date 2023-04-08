package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.component.SquareParticleRenderComponent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyBoard
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.util.Vector

class TestDisplay1(location: Location, normalVector: Vector) : SkyFXSimpleDisplay(location, normalVector) {
    override val components: MutableList<SkyComponent> = mutableListOf()

    init {
        add(object : SquareParticleRenderComponent {
            override val point1: Point = Point(0,0)
            override val point2: Point = Point(3,2)
            override val renderMaterial: Particle = Particle.WAX_ON
        })

        add(SkyBoard(Material.WHITE_CONCRETE.createBlockData(),this).apply {
            localPoint1 = Point(1,1)
            localPoint2 = Point(4,3)
        })
    }

    override fun render() {
        super.render()

        location.world.spawnParticle(Particle.WAX_OFF,location.clone().add(displayXAxis),1,0.0,0.0,0.0,0.0)
        location.world.spawnParticle(Particle.WAX_OFF,location.clone().add(displayYAxis),1,0.0,0.0,0.0,0.0)
    }
}
package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.component.SquareParticleRenderComponent
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyBoard
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.aligningBox
import io.github.muqhc.skyguifx.dsl.board
import io.github.muqhc.skyguifx.dsl.label
import io.github.muqhc.skyguifx.dsl.skyguiBuild
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.util.Alignment
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.util.Vector

class TestDisplay1(location: Location, normalVector: Vector) : SkyFXSimpleDisplay(location, normalVector) {
    override val components: MutableList<SkyComponent> = mutableListOf()

    init {
        add(app())
    }

    fun app() =
        SkyPanel(SkyPaddingBoxLayout()).skyguiBuild(this) {

            board(Material.DARK_OAK_PLANKS.createBlockData())

            aligningBox {
                label(Component.text("hello world!")) {
                    option.alignment = Alignment.BottomCenter

                    compo.textDisplay.backgroundColor = Color.fromARGB(0)
                }
            }

        }
}
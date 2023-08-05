package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.util.Alignment
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

class TestDisplay1(location: Location, normalVector: Vector) : SkyFXSimpleDisplay(location, normalVector) {
    init {
        add(app())
    }

    fun app() =
        SkyPanel(SkyPaddingBoxLayout()).skyguiBuild(this) {
            compo.localPoint2 = Point(2.7,0.7)

            board(Material.BIRCH_PLANKS.createBlockData())
            aligningBox {
                label(Component.text("hello world!").color { 0x603B2A }) {
                    option.alignment = Alignment.BottomCenter

                    entity { backgroundColor = Color.fromARGB(0) }
                    compo.scale = Point(2.2,2.3)
                }
            }
            button(onClicked = { event ->
                event.player.sendMessage("hello world")
            })
        }
}
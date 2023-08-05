package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyLabel
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

/**
 * <h2>Simple Counter</h2>
 * A counter that can increase number by touch itself.
 */
class TestDisplay4(location: Location, normalVector: Vector): SkyFXSimpleDisplay(location, normalVector) {
    init {
        add(app())
    }

    fun app() =
        SkyPanel(SkyPaddingBoxLayout()).skyguiBuild(this) {
            compo.localPoint2 = Point(2,2)

            var count: Int = 0
            lateinit var label: SkyLabel

            board(Material.WHITE_CONCRETE.createBlockData())
            button(onClicked = {
                count += 1
                label.entity { text(Component.text(count).color { 0x009900 }) }
            })
            aligningBox {
                label = label(Component.text(0).color { 0x009900 }) {
                    option.height = 1.0

                    compo.localFloatingLevel = 0.1

                    compo.scale = Point(3,3)
                    entity { backgroundColor = Color.fromARGB(0x00000000) }
                }
            }

        }
}
package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyLabel
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.util.IntPoint
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

class TestDisplay3(location: Location, normalVector: Vector) : SkyFXSimpleDisplay(location,normalVector) {

    init {
        add(app())
    }

    private fun app() =
        SkyPanel(SkyPaddingBoxLayout()).skyguiBuild(this) {

            compo.localPoint2 = Point(5,4)

            simpleGridField(2,2) {

                lateinit var label: SkyLabel
                var count = 0

                paddingBox {
                    option.gridPoint1 = IntPoint(0,0)
                    option.gridPoint1 = IntPoint(2,1)

                    board(Material.WHITE_CONCRETE.createBlockData())
                    aligningBox {
                        label = label(
                            Component.text("0").color { 0x00FF00 }
                        ) {
                            option.height = 0.3

                            compo.floatingLevel = 0.1

                            compo.scale = Point(3,3)
                            compo.textDisplay.backgroundColor = Color.fromARGB(0,0,0,0)
                        }
                    }
                }

                paddingBox {
                    defaultOption.paddingTop = 0.1
                    defaultOption.paddingRight = 0.05

                    option.gridPoint1 = IntPoint(0,1)
                    option.gridPoint2 = IntPoint(1,2)

                    board(Material.RED_CONCRETE.createBlockData())
                    button(onClicked = {
                        count += -1
                        label.textDisplay.text(Component.text(count).color { 0x00FF00 })
                    })
                }

                paddingBox {
                    defaultOption.paddingTop = 0.1
                    defaultOption.paddingLeft = 0.05

                    option.gridPoint1 = IntPoint(1,1)
                    option.gridPoint2 = IntPoint(2,2)

                    board(Material.GREEN_CONCRETE.createBlockData())
                    button(onClicked = {
                        count += 1
                        label.textDisplay.text(Component.text(count).color { 0x00FF00 })
                    })
                }

        }
    }
}
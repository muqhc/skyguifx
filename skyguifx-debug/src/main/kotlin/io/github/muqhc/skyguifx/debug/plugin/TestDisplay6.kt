package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyLabel
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.util.IntPoint
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

class TestDisplay6(location: Location, normalVector: Vector) : SkyFXSimpleDisplay(location, normalVector) {
    init {
        add(app())
    }

    fun app() =
        SkyPanel(SkyPaddingBoxLayout()).skyguiBuild(this) {
            compo.localPoint2 = Point(2,2)
            additionalFloatingLevel = 0.0
            simpleGridField(10,2) {
                val counterApp = application {
                    var counter by remember(0)
                    paddingBox {
                        option {
                            gridPoint1 = IntPoint(0,1)
                            gridPoint2 = IntPoint(2,10)
                        }
                        board(Material.BIRCH_PLANKS.createBlockData())
                        lateinit var numLabel: SkyLabel
                        aligningBox {
                            numLabel = label(Component.text(counter).color { 0x603B2A }) {
                                compo.localFloatingLevel = 0.1
                                compo.textDisplay.backgroundColor = Color.fromARGB(0)
                                compo.scale = Point(2, 2)
                            }
                        }
                        button(onClicked = {
                            counter += 1
                            numLabel.textDisplay.text(Component.text(counter).color { 0x603B2A })
                        })
                    }
                }
                paddingBox {
                    option {
                        gridPoint1 = IntPoint(0,0)
                        gridPoint2 = IntPoint(1,1)
                    }
                    board(Material.BIRCH_PLANKS.createBlockData())
                    lateinit var label: SkyLabel
                    var isOpened = false
                    aligningBox {
                        label = label(Component.text("open").color { 0x00FF00 })
                    }

                    button(onClicked = {
                        if (!isOpened) counterApp.show() else counterApp.close()
                        if (!isOpened) {
                            label.textDisplay.text(Component.text("close").color { 0xFF0000 })
                        }
                        else {
                            label.textDisplay.text(Component.text("open").color { 0x00FF00 })
                        }
                        isOpened = !isOpened
                    })
                }
            }
        }
}
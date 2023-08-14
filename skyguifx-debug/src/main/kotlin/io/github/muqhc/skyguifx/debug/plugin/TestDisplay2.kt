package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyBoard
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.util.IntPoint
import io.github.muqhc.skyguifx.util.plus
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

/**
 * <h2>3x3 Lights Out</h2>
 * A 3x3 Lights Out game pad.
 */
class TestDisplay2(
    location: Location, normalVector: Vector, windowSize: Point, windowPos: Point = Point(0,0),
    val gameSize: IntPoint = IntPoint(3,3)
) : SkyFXSimpleDisplay(location, normalVector) {

    var booleanField: MutableList<MutableList<MutableState<Boolean>>> =
        MutableList(gameSize.x) { MutableList(gameSize.y) { MutableState(false) } }

    var boardList: MutableList<MutableList<NullableState<SkyBoard>>> =
        booleanField.map { it.map { NullableState<SkyBoard>(null) }.toMutableList() }.toMutableList()

    class MutableState<T>(var value: T)
    class NullableState<T>(var value: T?)

    val fourWayPoints = listOf(
        IntPoint(0,1),
        IntPoint(0,-1),
        IntPoint(1,0),
        IntPoint(-1,0),
        IntPoint(0,0)
    )

    init {

        add(gameComponent(windowSize,windowPos))

    }


    fun gameComponent(
        windowSize: Point,
        windowPos: Point = Point(0,0),
    ) =
        buildSkyPanel {
            compo.localPoint1 = windowPos
            compo.localPoint2 = windowPos + windowSize

            simpleGridField(gameSize.y, gameSize.x) {
                booleanField.mapIndexed { x, l ->
                    l.mapIndexed { y, _ ->

                        paddingBox {
                            defaultOption.padding = 0.1

                            option.gridPoint1 = IntPoint(x, y)
                            option.gridPoint2 = IntPoint(x + 1, y + 1)


                            boardList[x][y].value = board(Material.WHITE_CONCRETE.createBlockData())

                            button(onClicked = {
                                it.player.sendMessage("You clicked! ${IntPoint(x, y)}")
                                gameButtonClick(IntPoint(x, y))
                            })
                        }

                    }
                }
            }
        }


    fun gameButtonClick(pos: IntPoint) {
        fourWayPoints.mapNotNull {
            val (x,y) = pos + it
            booleanField.getOrNull(x)?.getOrNull(y)
        }.forEach {
            it.value = !it.value
        }

        update()
    }

    fun update() {
        boardList.forEachIndexed { x,l -> l.forEachIndexed { y,it ->
            it.value!!.entity {
                block =
                    if (booleanField[x][y].value) Material.RED_CONCRETE.createBlockData()
                    else Material.WHITE_CONCRETE.createBlockData()
            }
        } }
    }
}
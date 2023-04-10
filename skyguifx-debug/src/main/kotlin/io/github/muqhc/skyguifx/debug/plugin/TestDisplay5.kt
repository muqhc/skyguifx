package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyItemBoard
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.util.IntPoint
import io.github.muqhc.skyguifx.util.times
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.ItemDisplay
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

/**
 * <h2>My Little Inventory</h2>
 * A small inventory that can store items.
 */
class TestDisplay5(location: Location, normalVector: Vector, val size: IntPoint = IntPoint(9,4)) : SkyFXSimpleDisplay(location, normalVector) {
    init {
        add(app())
    }

    fun app() =
        SkyPanel(SkyPaddingBoxLayout()).skyguiBuild(this) {
            compo.localPoint2 = Point(size.x,size.y)*0.4

            board(Material.LIGHT_GRAY_CONCRETE.createBlockData())
            simpleGridField(size.y,size.x) {
                repeat(size.x) { x -> repeat(size.y) { y ->
                    paddingBox {
                        defaultOption.padding = 0.04

                        option.gridPoint1 = IntPoint(x,y)
                        option.gridPoint2 = IntPoint(x+1,y+1)

                        board(Material.GRAY_CONCRETE.createBlockData())

                        val itemBoard = itemBoard(ItemStack(Material.AIR,0)) {
                            compo.localFloatingLevel = 0.03
                            compo.itemDisplay.itemDisplayTransform = ItemDisplay.ItemDisplayTransform.GUI
                        }
                        button(onClicked = {
                            if (it.hand != EquipmentSlot.HAND) return@button

                            val me = itemBoard.itemDisplay.itemStack?.clone() ?: ItemStack(Material.AIR,0)
                            val you = it.player.inventory.itemInMainHand.clone()

                            itemBoard.itemDisplay.itemStack = you
                            it.player.inventory.setItemInMainHand(me)

                            it.originEvent.isCancelled = true
                        })
                    }
                } }
            }
        }
}

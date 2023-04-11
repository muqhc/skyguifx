package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.component.SkyItemBoard
import io.github.muqhc.skyguifx.component.SkyLabel
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.util.Alignment
import io.github.muqhc.skyguifx.util.IntPoint
import io.github.muqhc.skyguifx.util.times
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.block.Action
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
                        lateinit var countLabel: SkyLabel
                        aligningBox {
                            countLabel = label(Component.text("")) {
                                option.alignment = Alignment.BottomRight
                                option.width = itemBoard.width / 2

                                compo.textDisplay.backgroundColor = Color.fromARGB(0)
                                compo.scale = Point(0.5,0.5)
                                compo.localFloatingLevel = 0.04
                            }
                        }
                        button {
                            option.padding = 0.0

                            compo.onClicked = onClicked@ {
                                if (it.hand != EquipmentSlot.HAND) return@onClicked

                                val me = itemBoard.itemDisplay.itemStack?.clone()
                                val you = it.player.inventory.itemInMainHand.clone()

                                if (it.action == Action.LEFT_CLICK_AIR || it.action == Action.LEFT_CLICK_BLOCK) {
                                    if (me != null && me.amount != 0 && me.type != Material.AIR) {
                                        if (it.player.isSneaking) {
                                            it.player.inventory.addItem(me)
                                            itemBoard.itemDisplay.itemStack = null
                                        } else {
                                            it.player.inventory.addItem(me.asOne())
                                            itemBoard.itemDisplay.apply {
                                                itemStack = itemStack!!.subtract(1)
                                            }
                                        }
                                    }
                                }

                                if (it.action == Action.RIGHT_CLICK_AIR || it.action == Action.RIGHT_CLICK_BLOCK) {
                                    if (it.player.inventory.itemInMainHand.amount > 0) {
                                        if (me == null || me.amount == 0 || me.type == Material.AIR) {
                                            if (it.player.isSneaking) {
                                                itemBoard.itemDisplay.itemStack = you
                                                it.player.inventory.setItemInMainHand(null)
                                            } else {
                                                itemBoard.itemDisplay.itemStack = you.asOne()
                                                it.player.inventory.itemInMainHand.subtract(1)
                                            }
                                        } else if (me.type == it.player.inventory.itemInMainHand.type) {
                                            if (it.player.isSneaking) {
                                                itemBoard.itemDisplay.apply {
                                                    itemStack = itemStack!!.add(you.amount)
                                                }
                                                it.player.inventory.setItemInMainHand(null)
                                            } else {
                                                itemBoard.itemDisplay.apply {
                                                    itemStack = itemStack!!.add(1)
                                                }
                                                it.player.inventory.itemInMainHand.subtract(1)
                                            }
                                        }
                                    }
                                }

                                if (itemBoard.itemDisplay.itemStack == null ||
                                    itemBoard.itemDisplay.itemStack!!.type == Material.AIR ||
                                    itemBoard.itemDisplay.itemStack!!.amount == 0
                                ) countLabel.textDisplay.text(Component.text(""))
                                else countLabel.textDisplay.text(Component.text(itemBoard.itemDisplay.itemStack!!.amount))

                                it.originEvent.isCancelled = true
                            }
                        }
                    }
                } }
            }
        }
}

package io.github.muqhc.skyguifx.debug.plugin

import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXSimpleDisplay
import io.github.muqhc.skyguifx.application.SkyApplicationCloser
import io.github.muqhc.skyguifx.component.SkyBoard
import io.github.muqhc.skyguifx.component.SkyLabel
import io.github.muqhc.skyguifx.component.SkyPanel
import io.github.muqhc.skyguifx.dsl.*
import io.github.muqhc.skyguifx.layout.SkyGridFieldLayout
import io.github.muqhc.skyguifx.layout.SkyGridFieldOption
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxLayout
import io.github.muqhc.skyguifx.layout.SkyPaddingBoxOption
import io.github.muqhc.skyguifx.util.*
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.block.Action
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

/**
 * <h2>My Little Inventory App</h2>
 * A small inventory that can store items.
 */
class TestDisplay7(location: Location, normalVector: Vector, val size: IntPoint = IntPoint(9,4)) : SkyFXSimpleDisplay(location, normalVector) {
    init {
        add(app())
    }

    fun app() =
        buildSkyPanel(Point(size.x,size.y)*0.4) {

            simpleGridField(size.y, size.x) {
                val invApp = inventoryApplication(this, this@TestDisplay7)
                lateinit var invAppAPI: SkyApplicationCloser

                val isOpenedProp = Observable(SimpleProp(false))
                var isOpened by isOpenedProp
                paddingBox {
                    option.gridPoint2 = IntPoint(3,3)
                    val myBoard = board(Material.CHEST.createBlockData())
                    isOpenedProp.onUpdate += {
                        if (it) {
                            option.gridPoint2 = IntPoint(1,1)
                            myBoard.entity { block = Material.REDSTONE_BLOCK.createBlockData() }
                            invAppAPI = invApp.show()
                        }
                        else {
                            invAppAPI.close()
                            myBoard.entity { block = Material.CHEST.createBlockData() }
                            option.gridPoint2 = IntPoint(3,3)
                        }
                    }
                    button(onClicked = {
                        isOpened = !isOpened
                    })
                }
            }
        }

    private fun inventoryApplication(
        containerConfigureScope: ContainerConfigureScope<SkyPanel<SkyGridFieldOption, SkyGridFieldLayout>, SkyPaddingBoxOption, SkyGridFieldOption, SkyGridFieldLayout>,
        testDisplay7: TestDisplay7
    ) = containerConfigureScope.application {
        simpleGridField(size.y, size.x) {
            option.gridPoint1 = IntPoint(1,1)
            option.gridPoint2 = size

            board(Material.LIGHT_GRAY_CONCRETE.createBlockData()) { option.gridPoint2 = testDisplay7.size }

            repeat(size.x) { x ->
                repeat(size.y) { y ->
                    paddingBox {
                        defaultOption.padding = 0.04

                        option.gridPoint1 = IntPoint(x, y)
                        option.gridPoint2 = IntPoint(x + 1, y + 1)

                        board(Material.GRAY_CONCRETE.createBlockData())

                        val itemStackProp = Observable<ItemStack?>(remember(null, "$namespace($x,$y)"))
                        var itemStack by itemStackProp

                        val itemBoard = itemBoard(ItemStack(Material.AIR, 0)) {
                            compo.localFloatingLevel = 0.03
                            entity {
                                itemDisplayTransform = ItemDisplay.ItemDisplayTransform.GUI
                                setItemStack(itemStack)
                                itemStackProp.onUpdate += { setItemStack(itemStack) }
                            }
                        }
                        lateinit var countLabel: SkyLabel
                        aligningBox {
                            countLabel = label(Component.text("")) {
                                option.alignment = Alignment.BottomRight
                                option.width = itemBoard.width / 2

                                entity { backgroundColor = Color.fromARGB(0) }
                                compo.scale = Point(0.5, 0.5)
                                compo.localFloatingLevel = 0.04
                            }
                        }
                        button {
                            option.padding = 0.0

                            compo.onClicked = onClicked@{
                                itemBoard.entity {
                                    if (it.hand != EquipmentSlot.HAND) return@entity

                                    val me = itemStack?.clone()
                                    val you = it.player.inventory.itemInMainHand.clone()

                                    if (it.action == Action.LEFT_CLICK_AIR || it.action == Action.LEFT_CLICK_BLOCK) {
                                        if (me != null && me.amount != 0 && me.type != Material.AIR) {
                                            if (it.player.isSneaking) {
                                                it.player.inventory.addItem(me)
                                                itemStack = null
                                            } else {
                                                it.player.inventory.addItem(me.asOne())
                                                itemStack = itemStack!!.subtract(1)
                                            }
                                        }
                                    }

                                    if (it.action == Action.RIGHT_CLICK_AIR || it.action == Action.RIGHT_CLICK_BLOCK) {
                                        if (it.player.inventory.itemInMainHand.amount > 0) {
                                            if (me == null || me.amount == 0 || me.type == Material.AIR) {
                                                if (it.player.isSneaking) {
                                                    itemStack = you
                                                    it.player.inventory.setItemInMainHand(null)
                                                } else {
                                                    itemStack = you.asOne()
                                                    it.player.inventory.itemInMainHand.subtract(1)
                                                }
                                            } else if (me.type == it.player.inventory.itemInMainHand.type) {
                                                if (it.player.isSneaking) {
                                                    itemStack = itemStack!!.add(you.amount)
                                                    it.player.inventory.setItemInMainHand(null)
                                                } else {
                                                    itemStack = itemStack!!.add(1)
                                                    it.player.inventory.itemInMainHand.subtract(1)
                                                }
                                            }
                                        }
                                    }

                                    if (itemStack == null ||
                                        itemStack!!.type == Material.AIR ||
                                        itemStack!!.amount == 0
                                    ) countLabel.entity { text(Component.text("")) }
                                    else countLabel.entity { text(Component.text(itemStack!!.amount)) }

                                    it.originEvent.isCancelled = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package io.github.muqhc.skyguifx.debug.plugin

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.manager.SimpleSkyguiManager
import io.github.muqhc.skygui.util.Point
import io.github.muqhc.skyguifx.SkyFXDisplay
import io.github.muqhc.skyguifx.component.SkyContainer
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector

class SkyguifxDebugPlugin: JavaPlugin() {
    val myGuiManager = SimpleSkyguiManager()
    var guiManagingTask: BukkitTask? = null

    override fun onEnable() {

        kommand {
            register("skyguifx_debug") {
                then("restart_gui") {
                    executes {
                        guiManagingTask?.cancel()
                        guiManagingTask = null
                        guiManagingTask = myGuiManager.manageStart(this@SkyguifxDebugPlugin)
                    }
                }

                then("stop_gui") {
                    executes {
                        guiManagingTask?.cancel()
                        guiManagingTask = null
                    }
                }

                then("clear") {
                    executes {
                        myGuiManager.displays.sumOf {
                            it.components.sumOf {
                                if (it is SkyContainer<*,*>) it.components.sumOf { (it,_) ->
                                    if (it is SkyContainer<*,*>) it.components.count() else 1
                                } else 1

                            }
                        }.let {
                            sender.sendMessage("$it superficial components removed")
                        }
                        myGuiManager.displays.forEach {
                            if (it is SkyFXDisplay) {
                                it.clear()
                            }
                        }
                        myGuiManager.displays.clear()
                    }
                }

                then("test1") {
                    requires { isPlayer && isOp }
                    executes {
                        myGuiManager.displays +=
                            TestDisplay1(player.location.clone(),player.location.direction.normalize().multiply(-1))

                    }
                }

                then("test2") {
                    requires { isPlayer && isOp }
                    executes {
                        myGuiManager.displays +=
                            TestDisplay2(
                                player.location.clone(),player.location.direction.normalize().multiply(-1),
                                Point(4,4)
                            )
                    }

                    then("game_width" to double(), "game_height" to double()) {
                        requires { isPlayer && isOp }
                        executes {
                            val game_width: Double by it
                            val game_height: Double by it

                            val display = TestDisplay2(
                                player.location.clone(),player.location.direction.normalize().multiply(-1),
                                Point(game_width,game_height)
                            )
                            myGuiManager.displays += display
                        }
                    }
                }

                then("test3") {
                    requires { isPlayer && isOp }
                    executes {
                        myGuiManager.displays +=
                            TestDisplay3(
                                player.location.clone(), player.location.direction.normalize().multiply(-1)
                            )
                    }
                }

                then("test4") {
                    requires { isPlayer && isOp }
                    executes {
                        myGuiManager.displays +=
                            TestDisplay4(
                                player.location.clone(), player.location.direction.normalize().multiply(-1)
                            )
                    }
                }
            }
        }

        guiManagingTask = myGuiManager.manageStart(this)
    }

    override fun onDisable() {
        guiManagingTask?.cancel()
        guiManagingTask = null
    }
}
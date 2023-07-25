package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skyguifx.util.LazyMutable
import org.bukkit.entity.Entity

abstract class SkyEntityComponent<E:Entity,EO:EntityOption> : SimpleSkyFXComponent() {
    protected lateinit var entityLazy: LazyMutable<E>
    var entity by entityLazy

    lateinit var entityOption: EO
        protected set

    fun prepare(option: EO, display: SkyDisplay) {
        entityOption = option
        onPrepare(option, display)
    }
    abstract fun onPrepare(option: EO, display: SkyDisplay)
}

interface EntityOption


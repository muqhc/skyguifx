package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skyguifx.util.LazyMutable
import org.bukkit.entity.Entity

abstract class SkyEntityComponent<T:Entity,EO:EntityOption> : SkyFXComponent {
    protected lateinit var entityLazy: LazyMutable<T>
    var entity by entityLazy

    abstract fun prepare(option: EO, display: SkyDisplay)
}

interface EntityOption


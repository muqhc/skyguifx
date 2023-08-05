package io.github.muqhc.skyguifx.component

import io.github.muqhc.skygui.SkyDisplay
import io.github.muqhc.skyguifx.util.LazyMutable
import io.github.muqhc.skyguifx.util.promise
import org.bukkit.entity.Entity

abstract class SkyEntityComponent<E:Entity,EO:EntityOption> : SimpleSkyFXComponent() {
    protected lateinit var entityLazy: LazyMutable<E>
    protected var entity: E
        get() = entityLazy.getValue(this,::entity)
        set(value) {
            entityLazy.setValue(this,::entity,value)
        }

    lateinit var entityOption: EO
        protected set

    fun prepare(option: EO, display: SkyDisplay) {
        entityOption = option
        onPrepare(option, display)
    }
    abstract fun onPrepare(option: EO, display: SkyDisplay)

    fun entity(configure: E.() -> Unit) {
        onBeforeRender promise {
            entity.configure()
        }
    }
}

interface EntityOption


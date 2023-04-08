package io.github.muqhc.skyguifx.layout

import io.github.muqhc.skyguifx.component.SkyContainer

open class SkyPlainFieldLayout: SkyLayoutManager<SkyPlainFieldOption,SkyPlainFieldLayout> {
    override var defaultLayoutOption: SkyPlainFieldOption = SkyPlainFieldOption()

    override fun manage(container: SkyContainer<SkyPlainFieldOption,SkyPlainFieldLayout>) {}
}

open class SkyPlainFieldOption: SkyLayoutOption {
    override fun clone(): SkyLayoutOption = SkyPlainFieldOption()
}
package io.github.muqhc.skyguifx.layout

import io.github.muqhc.skyguifx.component.SkyContained
import io.github.muqhc.skyguifx.component.SkyContainer

interface SkyLayoutManager<O:SkyLayoutOption,L:SkyLayoutManager<O,L>> {
    var defaultLayoutOption: O

    fun manage(container: SkyContainer<O,L>)

    fun onPreAdd(container: SkyContainer<O,L>, addedComponent: SkyContained<O>) {}
    fun onAdd(container: SkyContainer<O,L>, addedComponent: SkyContained<O>) {}
}
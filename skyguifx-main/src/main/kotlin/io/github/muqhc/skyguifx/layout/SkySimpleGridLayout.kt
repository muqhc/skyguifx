package io.github.muqhc.skyguifx.layout

open class SkySimpleGridLayout(rows: Int, columns: Int): SkyGridFieldLayout() {
    var rows = rows
        set(value) {
            verticalGridPoints = (0..value).map { it.toDouble() / rows.toDouble() }.toMutableList()
            field = value
        }
    var columns = columns
        set(value) {
            horizontalGridPoints = (0..value).map { it.toDouble() / columns.toDouble() }.toMutableList()
            field = value
        }

    init {
        horizontalGridPoints = (0..columns).map { it.toDouble() / columns.toDouble() }.toMutableList()
        verticalGridPoints = (0..rows).map { it.toDouble() / rows.toDouble() }.toMutableList()
    }
}
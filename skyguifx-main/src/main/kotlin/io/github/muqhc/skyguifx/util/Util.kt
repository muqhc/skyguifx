package io.github.muqhc.skyguifx.util

import io.github.muqhc.skygui.component.SkyComponent
import io.github.muqhc.skygui.util.Point

operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)
operator fun Point.times(other: Point) = Point(x * other.x, y * other.y)
operator fun Point.div(other: Point) = Point(x / other.x, y / other.y)
operator fun Point.times(other: Double) = Point(x * other, y * other)
operator fun Point.div(other: Double) = Point(x / other, y / other)

fun SkyComponent.isPointInReversed(target: Point) =
    (point1.x <= target.x) and (point2.x >= target.x) and
        (point1.y <= target.y) and (point2.y >= target.y)


package io.github.muqhc.skyguifx.util


data class IntPoint(val x: Int, val y: Int)


operator fun IntPoint.plus(other: IntPoint) = IntPoint(x + other.x, y + other.y)
operator fun IntPoint.minus(other: IntPoint) = IntPoint(x - other.x, y - other.y)
operator fun IntPoint.times(other: IntPoint) = IntPoint(x * other.x, y * other.y)
operator fun IntPoint.div(other: IntPoint) = IntPoint(x / other.x, y / other.y)
operator fun IntPoint.times(other: Int) = IntPoint(x * other, y * other)
operator fun IntPoint.div(other: Int) = IntPoint(x / other, y / other)

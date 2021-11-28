package org.igye.graph2d

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double) {
    val length by lazy { sqrt(x*x+y*y) }
    operator fun minus(p: Point): Point = Point(x-p.x, y-p.y)
    operator fun plus(p: Point): Point = Point(x+p.x, y+p.y)
    operator fun times(factor: Double): Point = Point(x*factor, y*factor)
    operator fun div(factor: Double): Point = Point(x/factor, y/factor)
    fun rotate(deg:Deg): Point {
        val angle = deg.toRad().doubleValue
        return Point(
            x * cos(angle) - y * sin(angle),
            x * sin(angle) + y * cos(angle)
        )
    }
}
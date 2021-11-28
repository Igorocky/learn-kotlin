package org.igye.graph2d

import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.acos

data class Vector2D(val begin: Point, val end: Point) {
    val length by lazy {
        (end - begin).length
    }

    operator fun times(factor: Double): Vector2D = Vector2D(begin = begin, end = begin + (end - begin) * factor)
    operator fun times(factor: Int): Vector2D = Vector2D(begin = begin, end = begin + (end - begin) * factor)
    operator fun div(factor: Double): Vector2D = Vector2D(begin = begin, end = begin + (end - begin) / factor)
    operator fun div(factor: Int): Vector2D = Vector2D(begin = begin, end = begin + (end - begin) / factor)

    fun rotate(deg: Deg): Vector2D = Vector2D(begin = begin, end = begin + (end - begin).rotate(deg))
    fun normalize(): Vector2D = this / length
    fun swapEnds(): Vector2D = Vector2D(begin = end, end = begin)
    fun beginAt(p:Point): Vector2D = Vector2D(begin = p, end = p + this)
    fun endAt(p:Point): Vector2D = swapEnds().beginAt(p).swapEnds()
    fun deg(): Deg = rad().toDeg()
    fun rad(): Rad {
        val dx = end.x - begin.x
        val dy = begin.y - end.y
        val dxAbs = dx.absoluteValue
        val dyAbs = dy.absoluteValue
        val dxGreaterDy = dxAbs >= dyAbs
        val res = if (dx > 0) {
            if (dy > 0) {
                if (dxGreaterDy) {
                    rad(cat = dxAbs, hyp = length)
                } else {
                    PI/2 - rad(cat = dyAbs, hyp = length)
                }
            } else {
                if (dxGreaterDy) {
                    -rad(cat = dxAbs, hyp = length)
                } else {
                    -PI/2 + rad(cat = dyAbs, hyp = length)
                }
            }
        } else {
            if (dy > 0) {
                if (dxGreaterDy) {
                    PI - rad(cat = dxAbs, hyp = length)
                } else {
                    PI/2 + rad(cat = dyAbs, hyp = length)
                }
            } else {
                if (dxGreaterDy) {
                    -PI + rad(cat = dxAbs, hyp = length)
                } else {
                    -PI/2 - rad(cat = dyAbs, hyp = length)
                }
            }
        }
        return Rad(res)
    }

    private fun rad(cat: Double, hyp: Double): Double = acos(cat/hyp)
}
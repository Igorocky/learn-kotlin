package org.igye.svg

import kotlin.math.*

val ex = Vector2D(start = Point(0.0, 0.0), end = Point(1.0, 0.0))
val ey = Vector2D(start = Point(0.0, 0.0), end = Point(0.0, 1.0))

fun degToRad(deg:Double): Double = deg/180.0*Math.PI

class Point(val x: Double, val y: Double) {
    val length by lazy { sqrt(x*x+y*y) }
    operator fun minus(p: Point): Point = Point(x-p.x, y-p.y)
    operator fun plus(p: Point): Point = Point(x+p.x, y+p.y)
    operator fun times(factor: Double): Point = Point(x*factor, y*factor)
    operator fun div(factor: Double): Point = Point(x/factor, y/factor)
    fun rotate(deg:Double): Point {
        val rad = -degToRad(deg)
        return Point(
            x* cos(rad) - y* sin(rad),
            x* sin(rad) + y* cos(rad)
        )
    }
}

data class Vector2D(val start: Point, val end: Point) {
    val length by lazy {
        (end - start).length
    }

    fun rotate(deg: Double): Vector2D = Vector2D(
        start,
        start + (end - start).rotate(deg)
    )
}



data class Boundaries(val minX: Double, val maxX: Double, val minY: Double, val maxY: Double) {
    init {
        if (!(minX <= maxX && minY <= maxY)) {
            throw SvgException("!(minX <= maxX && minY <= maxY)")
        }
    }

    val width by lazy { maxX - minX }
    val height by lazy { maxY - minY }

    fun addPoint(p: Point): Boundaries = Boundaries(
        minX = if (minX <= p.x) minX else p.x,
        maxX = if (p.x <= maxX) maxX else p.x,
        minY = if (minY <= p.y) minY else p.y,
        maxY = if (p.y <= maxY) maxY else p.y,
    )

    fun addPoints(vararg points: Point): Boundaries {
        val allPoints = ArrayList<Point>()
        allPoints.add(Point(minX, minY))
        allPoints.add(Point(maxX, maxY))
        allPoints.addAll(points)
        return fromPoints(allPoints)
    }

    fun addAbsoluteMargin(m: Double): Boundaries = Boundaries(
        minX = minX - m,
        maxX = maxX + m,
        minY = minY - m,
        maxY = maxY + m,
    )

    fun toRect(): Rect = Rect(x = minX, y = minY, width = width, height = height)

    companion object {
        fun fromPoints(points: List<Point>): Boundaries {
            if (points.isEmpty()) {
                throw SvgException("points.isEmpty()")
            }
            var minX = points[0].x
            var maxX = points[0].x
            var minY = points[0].y
            var maxY = points[0].y
            for (i in 1 until points.size) {
                val point = points[i]
                minX = min(minX, point.x)
                maxX = max(point.x, maxX)
                minY = min(minY, point.y)
                maxY = max(point.y, maxY)
            }
            return Boundaries(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }

        fun fromPoints(vararg points: Point): Boundaries {
            return fromPoints(points.toList())
        }
    }
}

class SvgException(msg: String): RuntimeException(msg)
package org.igye.graph2d

import kotlin.math.max
import kotlin.math.min

data class Boundaries2D(val minX: Double, val maxX: Double, val minY: Double, val maxY: Double) {
    init {
        if (!(minX <= maxX && minY <= maxY)) {
            throw Graph2DException("!(minX <= maxX && minY <= maxY)")
        }
    }

    val width by lazy { maxX - minX }
    val height by lazy { maxY - minY }

    fun addPoint(p: Point): Boundaries2D = Boundaries2D(
        minX = min(minX, p.x),
        maxX = max(maxX, p.x),
        minY = min(minY, p.y),
        maxY = max(maxY, p.y)
    )

    fun addPoints(vararg points: Point): Boundaries2D {
        val allPoints = ArrayList<Point>()
        allPoints.add(Point(minX, minY))
        allPoints.add(Point(maxX, maxY))
        allPoints.addAll(points)
        return fromPoints(listOf(
            Point(minX, minY),
            Point(maxX, maxY),
            *points
        ))
    }

    fun addAbsoluteMargin(m: Double): Boundaries2D = Boundaries2D(
        minX = minX - m,
        maxX = maxX + m,
        minY = minY - m,
        maxY = maxY + m,
    )

    fun addAbsoluteMargin(m: Long): Boundaries2D = addAbsoluteMargin(m.toDouble())

    fun merge(other: Boundaries2D): Boundaries2D = Boundaries2D(
        minX = min(minX, other.minX),
        maxX = max(maxX, other.maxX),
        minY = min(minY, other.minY),
        maxY = max(maxY, other.maxY)
    )

    companion object {
        fun fromPoints(points: Collection<Point>): Boundaries2D {
            if (points.isEmpty()) {
                throw Graph2DException("points.isEmpty()")
            }
            val iterator = points.iterator()
            var point = iterator.next()
            var minX = point.x
            var maxX = point.x
            var minY = point.y
            var maxY = point.y
            while (iterator.hasNext()) {
                point = iterator.next()
                minX = min(minX, point.x)
                maxX = max(maxX, point.x)
                minY = min(minY, point.y)
                maxY = max(maxY, point.y)
            }
            return Boundaries2D(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }

        fun fromPoints(vararg points: Point): Boundaries2D {
            return fromPoints(points.toList())
        }

        fun merge(boundaries: List<Boundaries2D>): Boundaries2D {
            if (boundaries.isEmpty()) {
                throw Graph2DException("boundaries.isEmpty()")
            }
            val iterator = boundaries.iterator()
            var b = iterator.next()
            var minX = b.minX
            var maxX = b.maxX
            var minY = b.minY
            var maxY = b.maxY
            while (iterator.hasNext()) {
                b = iterator.next()
                minX = min(minX, b.minX)
                maxX = max(maxX, b.maxX)
                minY = min(minY, b.minY)
                maxY = max(maxY, b.maxY)
            }
            return Boundaries2D(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }
    }
}


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
        return fromList(listOf(
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

    fun merge(vararg others: Any): Boundaries2D {
        if (others[0] is Collection<*>) {
            return mergeList(others[0] as Collection<*>)
        } else {
            return mergeList(others.toList())
        }
    }

    private fun mergeList(others: Collection<*>): Boundaries2D {
        val other = fromList(others)
        return Boundaries2D(
            minX = min(minX, other.minX),
            maxX = max(maxX, other.maxX),
            minY = min(minY, other.minY),
            maxY = max(maxY, other.maxY)
        )
    }

    companion object {
        fun from(vararg objs: Any): Boundaries2D {
            if (objs[0] is Collection<*>) {
                return fromList(objs[0] as Collection<*>)
            } else {
                return fromList(objs.toList())
            }
        }

        private fun fromList(objs: Collection<*>): Boundaries2D {
            if (objs.isEmpty()) {
                throw Graph2DException()
            }
            val iterator = objs.iterator()
            var obj = iterator.next()
            var minX = getMinX(obj!!)
            var maxX = getMaxX(obj!!)
            var minY = getMinY(obj!!)
            var maxY = getMaxY(obj!!)
            while (iterator.hasNext()) {
                obj = iterator.next()
                minX = min(minX, getMinX(obj!!))
                maxX = max(maxX, getMaxX(obj!!))
                minY = min(minY, getMinY(obj!!))
                maxY = max(maxY, getMaxY(obj!!))
            }
            return Boundaries2D(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }

        private fun getMinX(a: Any): Double {
            if (a is Point) {
                return a.x
            } else if (a is Vector2D) {
                return min(a.begin.x, a.end.x)
            } else if (a is Boundaries2D) {
                return a.minX
            } else {
                throw Graph2DException()
            }
        }

        private fun getMinY(a: Any): Double {
            if (a is Point) {
                return a.y
            } else if (a is Vector2D) {
                return min(a.begin.y, a.end.y)
            } else if (a is Boundaries2D) {
                return a.minY
            } else {
                throw Graph2DException()
            }
        }

        private fun getMaxX(a: Any): Double {
            if (a is Point) {
                return a.x
            } else if (a is Vector2D) {
                return max(a.begin.x, a.end.x)
            } else if (a is Boundaries2D) {
                return a.maxX
            } else {
                throw Graph2DException()
            }
        }

        private fun getMaxY(a: Any): Double {
            if (a is Point) {
                return a.y
            } else if (a is Vector2D) {
                return max(a.begin.y, a.end.y)
            } else if (a is Boundaries2D) {
                return a.maxY
            } else {
                throw Graph2DException()
            }
        }
    }
}


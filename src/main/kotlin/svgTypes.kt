package org.igye.svg

class Point(val x: Double, val y: Double)

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

    fun addAbsoluteMargin(m: Double): Boundaries = Boundaries(
        minX = minX - m,
        maxX = maxX + m,
        minY = minY - m,
        maxY = maxY + m,
    )

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
                minX = if (minX <= point.x) minX else point.x
                maxX = if (point.x <= maxX) maxX else point.x
                minY = if (minY <= point.y) minY else point.y
                maxY = if (point.y <= maxY) maxY else point.y
            }
            return Boundaries(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }

        fun fromPoints(vararg points: Point): Boundaries {
            return fromPoints(points.toList())
        }
    }
}

class SvgException(msg: String): RuntimeException(msg)
package org.igye.graph2d

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
}
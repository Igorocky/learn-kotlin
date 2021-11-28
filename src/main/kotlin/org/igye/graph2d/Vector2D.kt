package org.igye.graph2d

data class Vector2D(val begin: Point, val end: Point) {
    val length by lazy {
        (end - begin).length
    }

    fun rotate(deg: Deg): Vector2D = Vector2D(
        begin = begin,
        end = begin + (end - begin).rotate(deg)
    )
}
package org.igye.svg

fun sceneGraphOfFunction(time: Double) : Frame {
    val a = Point(0.0, 0.0)
    val b = Point(5.0, 0.0)
    val dya = -3.0
    val dyb = 4.0
    val maxTime = 5.0
    val minY = a.y + maxTime*dya
    val maxY = b.y + maxTime*dyb
    val sceneBoundaries = Boundaries.fromPoints(a,b,Point(a.x,minY),Point(b.x,maxY)).addAbsoluteMargin(2.0)

    return Frame(
        boundaries = sceneBoundaries,
        elems = listOf(
            Line(x1 = a.x, y1 = a.y, x2 = b.x, y2 = b.y, stroke = "red"),
            Line(x1 = a.x, y1 = a.y + time*dya, x2 = b.x, y2 = b.y + time*dyb, stroke = "red"),
        )
    )
}
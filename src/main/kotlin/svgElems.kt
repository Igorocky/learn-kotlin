package org.igye.svg

class Svg(width: Int, height: Int, boundaries: Boundaries): SvgElem("svg") {
    init {
        attrs.put("xmlns", "http://www.w3.org/2000/svg")
        attrs.put("width", width)
        attrs.put("height", height)
        attrs.put("viewBox", "${boundaries.minX} ${boundaries.minY} ${boundaries.width} ${boundaries.height}")
    }
}

class Rect(
    val x: Double?, val y: Double?, val width: Double?, val height: Double?,
    val fill: String?): SvgElem("rect") {
    init {
        if (x != null) attrs.put("x", x)
        if (y != null) attrs.put("y", y)
        if (width != null) attrs.put("width", width)
        if (height != null) attrs.put("height", height)
        if (fill != null) attrs.put("fill", fill)
    }
}
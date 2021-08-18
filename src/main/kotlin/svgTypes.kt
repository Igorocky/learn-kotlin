package org.igye.svg

import org.w3c.dom.Document
import org.w3c.dom.Element

open class SvgElem(val name:String) {
    protected val attrs: MutableMap<String, Any?> = mutableMapOf()

    fun toXmlElem(document: Document): Element {
        val elem = document.createElement(name)
        for (attr in attrs) {
            val xmlAttr = document.createAttribute(attr.key)
            xmlAttr.setValue(attr.value?.toString())
            elem.setAttributeNode(xmlAttr)
        }
        return elem
    }
}

class Point(val x: Double, val y: Double)

class Boundaries(val minX: Double, val maxX: Double, val minY: Double, val maxY: Double) {
    init {
        if (!(minX <= maxX && minY <= maxY)) {
            throw SvgException("!(minX <= maxX && minY <= maxY)")
        }
    }

    val width by lazy { maxX - minX }
    val height by lazy { maxY - minY }

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
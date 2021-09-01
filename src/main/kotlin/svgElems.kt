package org.igye.svg

import org.w3c.dom.Document
import org.w3c.dom.Element

abstract class SvgElem(val name:String) {
    fun toXmlElem(document: Document): Element {
        val elem = document.createElement(name)
        for (attr in getAttrs()) {
            if (attr.value != null) {
                val xmlAttr = document.createAttribute(attr.key)
                xmlAttr.setValue(attr.value.toString())
                elem.setAttributeNode(xmlAttr)
            }
        }
        return elem
    }

    protected abstract fun getAttrs(): Map<String, Any?>
}

data class Svg(val width: Int, val height: Int, val boundaries: Boundaries): SvgElem("svg") {
    override fun getAttrs(): Map<String, Any?> {
        return mapOf(
            "xmlns" to "http://www.w3.org/2000/svg",
            "width" to width,
            "height" to height,
            "viewBox" to "${boundaries.minX} ${boundaries.minY} ${boundaries.width} ${boundaries.height}",
        )
    }
}

data class Line(
    val x1: Double?, val y1: Double?, val x2: Double?, val y2: Double?,
    val stroke: String? = null): SvgElem("line") {

    override fun getAttrs(): Map<String, Any?> {
        return mapOf("x1" to x1, "y1" to y1, "x2" to x2, "y2" to y2, "stroke" to stroke)
    }
}

data class Rect(
    val x: Double?, val y: Double?, val width: Double?, val height: Double?,
    val fill: String? = null): SvgElem("rect") {

    override fun getAttrs(): Map<String, Any?> {
        return mapOf("x" to x, "y" to y, "width" to width, "height" to height, "fill" to fill)
    }
}
package org.igye.svg

import org.w3c.dom.Document
import org.w3c.dom.Element

abstract class SvgElem(val name:String) {
    fun toXmlElem(document: Document): Element {
        val elem = document.createElement(name)
        for (attr in getAttrs()) {
            val xmlAttr = document.createAttribute(attr.key)
            xmlAttr.setValue(attr.value?.toString())
            elem.setAttributeNode(xmlAttr)
        }
        return elem
    }

    protected abstract fun getAttrs(): Map<String, Any?>
}

class Svg(val width: Int, val height: Int, val boundaries: Boundaries): SvgElem("svg") {
    override fun getAttrs(): Map<String, Any?> {
        return mapOf(
            "xmlns" to "http://www.w3.org/2000/svg",
            "width" to width,
            "height" to height,
            "viewBox" to "${boundaries.minX} ${boundaries.minY} ${boundaries.width} ${boundaries.height}",
        )
    }
}

class Rect(
    val x: Double?, val y: Double?, val width: Double?, val height: Double?,
    val fill: String?): SvgElem("rect") {

    override fun getAttrs(): Map<String, Any?> {
        return mapOf(
            "x" to x,
            "y" to y,
            "width" to width,
            "height" to height,
            "fill" to fill,
        )
    }
}
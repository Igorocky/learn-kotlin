package org.igye.svg

import org.w3c.dom.Document
import org.w3c.dom.Element

data class SvgElem(
    val name:String,
    val attrs: Map<SvgAttr,Any>,
    val children: List<SvgElem> = emptyList()
) {
    fun toXmlElem(document: Document): Element {
        val elem = document.createElement(name)
        for ((attr, attrValue) in attrs) {
            if (attrValue != null) {
                val xmlAttr = document.createAttribute(attr.attrName)
                xmlAttr.setValue(attrValue.toString())
                elem.setAttributeNode(xmlAttr)
            }
        }
        children.forEach { elem.appendChild(it.toXmlElem(document)) }
        return elem
    }
}

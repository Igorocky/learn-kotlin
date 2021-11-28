package org.igye.svg

import org.w3c.dom.Document
import org.w3c.dom.Element

data class SvgElem(
    val name:String,
    val attrs: Map<SvgAttr,Any>,
    val children: List<Any> = emptyList()
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
        for (child in children) {
            if (child is SvgElem) {
                elem.appendChild(child.toXmlElem(document))
            } else if (child is String) {
                elem.textContent = child
            } else {
                throw SvgException()
            }
        }
        return elem
    }
}

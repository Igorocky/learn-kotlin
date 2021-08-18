package org.igye.svg

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun createSvgFile(filePath:String, width: Int, height: Int, boundaries: Boundaries, elems: List<SvgElem>) {
    val documentFactory = DocumentBuilderFactory.newInstance()
    val documentBuilder = documentFactory.newDocumentBuilder()
    val document = documentBuilder.newDocument()

    val svg = Svg(width, height, boundaries)
    val svgElem = svg.toXmlElem(document)
    document.appendChild(svgElem)

    elems.forEach { svgElem.appendChild(it.toXmlElem(document)) }

    val transformerFactory = TransformerFactory.newInstance()
    val transformer: Transformer = transformerFactory.newTransformer()
    val domSource = DOMSource(document)
    val streamResult = StreamResult(File(filePath))

    transformer.transform(domSource, streamResult)
}

fun createSvgFile(filePath:String, width: Int, height: Int, boundaries: Boundaries, vararg elems: SvgElem) {
    createSvgFile(filePath, width, height, boundaries, elems.toList())
}
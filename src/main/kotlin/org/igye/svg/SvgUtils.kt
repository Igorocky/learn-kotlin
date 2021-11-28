package org.igye.svg

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.igye.graph2d.Boundaries2D
import org.igye.graph2d.Vector2D
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object SvgUtils {

    fun writePngFile(elems: List<SvgElem>, boundaries: Boundaries2D, width: Int, height: Int, svgFile: File, pngFile: File) {
        writeSvgFile(elems = elems, boundaries = boundaries, width = width, height = height, file = svgFile)
        svgToPng(svgFile = svgFile, pngFile = pngFile, width = width, height = height)
    }

    fun writeSvgFile(elems: List<SvgElem>, boundaries: Boundaries2D, width: Int, height: Int, file: File) {
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()

        val svg = SvgElem(
            name = "svg",
            attrs = mapOf(
                SvgAttr.XMLNS to "http://www.w3.org/2000/svg",
                SvgAttr.WIDTH to width,
                SvgAttr.HEIGHT to height,
                SvgAttr.VIEWBOX to "${boundaries.minX} ${boundaries.minY} ${boundaries.width} ${boundaries.height}",
            ),
            children = elems
        )
        document.appendChild(svg.toXmlElem(document))


        val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        val domSource = DOMSource(document)
        val streamResult = StreamResult(file)

        transformer.transform(domSource, streamResult)
    }

    fun svgToPng(svgFile: File, pngFile: File, width: Int = 400, height: Int = 400) {
        val svg_URI_input = svgFile.toURI().toString()
        val input_svg_image = TranscoderInput(svg_URI_input)

        FileOutputStream(pngFile).use { png_ostream: OutputStream ->
            val output_png_image = TranscoderOutput(png_ostream)

            val converter = PNGTranscoder()
            converter.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width.toFloat())
            converter.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height.toFloat())

            converter.transcode(input_svg_image, output_png_image)
        }
    }

    fun line(
        vector2D: Vector2D? = null,
        stroke: Color? = null,
        strokeWidth: Any? = null,
    ):SvgElem {
        val x1 = vector2D!!.begin.x
        val x2 = vector2D.end.x
        val y1 = vector2D!!.begin.y
        val y2 = vector2D!!.end.y
        val attrs: EnumMap<SvgAttr, Any> = EnumMap(SvgAttr::class.java)
        attrs[SvgAttr.X1] = x1
        attrs[SvgAttr.X2] = x2
        attrs[SvgAttr.Y1] = y1
        attrs[SvgAttr.Y2] = y2
        attrs[SvgAttr.STROKE] = stroke?.strValue
        attrs[SvgAttr.STROKE_WIDTH] = strokeWidth
        return SvgElem(name = "line", attrs = attrs)
    }
}
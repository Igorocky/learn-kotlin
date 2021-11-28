package org.igye

import org.igye.graph2d.Boundaries2D
import org.igye.graph2d.Point
import org.igye.graph2d.Vector2D
import org.igye.svg.Color
import org.igye.svg.SvgUtils
import java.io.File

fun main() {
    val vector = Vector2D(begin = Point(0.0, 0.0), end = Point(5.0, 0.0))
    val line = SvgUtils.line(vector2D = vector, stroke = Color.black, strokeWidth = 0.1)
    val imgDir = File("C:\\igye\\temp")
    val imgName = "img-1"
    SvgUtils.writePngFile(
        elems = listOf(line),
        boundaries = Boundaries2D.fromPoints(vector.begin, vector.end).addAbsoluteMargin(5),
        width = 300,
        height = 300,
        svgFile = File(imgDir, imgName + ".svg"),
        pngFile = File(imgDir, imgName + ".png")
    )
}
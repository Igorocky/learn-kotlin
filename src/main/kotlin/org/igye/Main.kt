package org.igye

import org.igye.graph2d.Boundaries2D
import org.igye.graph2d.DistBetweenPoints
import org.igye.graph2d.Point
import org.igye.graph2d.Vector2D
import org.igye.svg.Color
import org.igye.svg.SvgUtils
import org.igye.svg.SvgUtils.circle
import java.io.File

fun main() {
    val a = Point(3.0, 4.0)
    val b = Point(15.0, 20.0)
//    val vector = Vector2D(begin = Point(0.0, 0.0), end = Point(5.0, 0.0))
//    val line = SvgUtils.line(vector = vector, strokeWidth = 0.1)

    val dist = DistBetweenPoints(
        base = a..b,
        color = Color.green,
        baseDist = 3.0,
        arrowSize = 1.0,
        lineWidth = 0.1
    ).toSvg()


    val boundaries = Boundaries2D.fromPoints(a,b)
        .merge(dist.boundaries)
        .addAbsoluteMargin(5)

    val imgDir = File("C:\\igye\\temp")
    val imgName = "img-1"
    SvgUtils.writePngFile(
        elems = listOf(
            circle(center = a, radius = 1.0, color = Color.red),
            circle(center = b, radius = 1.0, color = Color.red),
        ).plus(dist.elems),
        boundaries = boundaries,
        width = 300,
        height = 300,
        svgFile = File(imgDir, imgName + ".svg"),
        pngFile = File(imgDir, imgName + ".png")
    )
}
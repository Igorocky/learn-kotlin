package org.igye

import org.igye.graph2d.*
import org.igye.svg.Color
import org.igye.svg.SvgElem
import org.igye.svg.SvgUtils
import org.igye.svg.SvgUtils.circle
import org.igye.svg.SvgUtils.line
import org.igye.svg.SvgUtils.text
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
//    testAngles()
    testSizes()
}

fun testSizes() {
    val a = Point(3.0, 4.0)
    val b = Point(15.0, -20.0)
//    val vector = Vector2D(begin = Point(0.0, 0.0), end = Point(5.0, 0.0))
//    val line = SvgUtils.line(vector = vector, strokeWidth = 0.1)

    val dist = DistBetweenPoints(
        base = a..b,
        color = Color.green,
        baseDist = 3.0,
        arrowSize = 1.0,
        lineWidth = 0.1
    ).toSvg()


    val boundaries = Boundaries2D.from(a,b)
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

fun testAngles() {
    fun ang(vec: Vector2D): String = BigDecimal(vec.deg().doubleValue).setScale(1, RoundingMode.HALF_UP).toString()

    val elems = ArrayList<SvgElem>()
    var vec = Point(10.0, 20.0)..Point(100.0, 20.0)
    var boundary = Boundaries2D.from(vec.begin, vec.end)
    val strokeWidth = 1.0

    elems.add(line(vector = vec, strokeWidth = strokeWidth))
    elems.add(text(begin = vec.end, text = ang(vec)))

    for (i in 1..11) {
        vec = vec.rotate(30.deg())
        boundary = boundary.addPoints(vec.begin, vec.end)
        elems.add(line(vector = vec, strokeWidth = strokeWidth))
        elems.add(text(begin = vec.end, text = ang(vec)))
    }

    vec = (Point(10.0, 20.0)..Point(100.0, 20.0)).beginAt(Point(300.0, 20.0))
    boundary = boundary.addPoints(vec.begin, vec.end)
    elems.add(line(vector = vec, strokeWidth = strokeWidth))
    elems.add(text(begin = vec.end, text = ang(vec)))

    for (i in 1..11) {
        vec = vec.rotate(-30.deg())
        boundary = boundary.addPoints(vec.begin, vec.end)
        elems.add(line(vector = vec, strokeWidth = strokeWidth))
        elems.add(text(begin = vec.end, text = ang(vec)))
    }



    val imgDir = File("C:\\igye\\temp")
    val imgName = "img-1"
    SvgUtils.writePngFile(
        elems = elems,
        boundaries = boundary.addAbsoluteMargin(50),
        width = 600,
        height = 600,
        svgFile = File(imgDir, imgName + ".svg"),
        pngFile = File(imgDir, imgName + ".png")
    )
}
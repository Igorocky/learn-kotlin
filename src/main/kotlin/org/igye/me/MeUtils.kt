package org.igye.me

import org.igye.graph2d.Boundaries2D
import org.igye.graph2d.DistBetweenPoints
import org.igye.graph2d.Vector2D
import org.igye.graph2d.deg
import org.igye.svg.Color
import org.igye.svg.SvgElems
import org.igye.svg.SvgUtils

fun size(
    base: Vector2D, color: Color = Color.blue, baseDist: Int = 0,
    arrowSize: Double, lineWidth: Double, fontSize: Double, external: Boolean = false,
    textHorShift: Double = arrowSize, textVerShift: Double = arrowSize / 2,
    renderBorders: Boolean = true, onlyLeftBorder: Boolean = false, onlyRightBorder: Boolean = false,
) =
    DistBetweenPoints(
        base, color, baseDist.toDouble(), arrowSize, lineWidth,
        fontSize, external,
        textHorShift, textVerShift,
        renderBorders, onlyLeftBorder, onlyRightBorder
    ).toSvg()

open class Box(
    val ex: Vector2D, val width: Double, val depth: Double, val text: String,
    val arrowSize: Double, val lineWidth: Double, val fontSize: Double,
    val textHorShift: Int = 5, val textVerShift: Int = 4,
    val widthSizeDist: Int = 10, val widthSizeTextHorShift: Int = 5, val widthSizeTextVerShift: Int = 1, val widthSizeInvert: Boolean = false,
    val depthSizeDist: Int = (arrowSize+1).toInt(), val depthSizeTextHorShift: Int = 1, val depthSizeTextVerShift: Int = 1, val depthSizeInvert: Boolean = false,
) {
    val bottom = ex*width
    val ey = ex.rotate(90.deg())
    val top = bottom.translate(ey*depth)
    val corners = listOf(bottom.begin, top.begin, top.end, bottom.end)

    open fun toSvg(): SvgElems {
        val widthSize = size(base = if (widthSizeInvert) top.swapEnds() else top, baseDist = widthSizeDist * (if (widthSizeInvert) -1 else 1),
            renderBorders = false, arrowSize=arrowSize, lineWidth=lineWidth, fontSize=fontSize,
            textHorShift = widthSizeTextHorShift.toDouble(), textVerShift = widthSizeTextVerShift.toDouble(),
        )
        val vert = bottom.end..top.end
        val depthSize = size(base = if (depthSizeInvert) vert.swapEnds() else vert, baseDist = -depthSizeDist * (if (depthSizeInvert) -1 else 1),
            renderBorders = false, arrowSize=arrowSize, lineWidth=lineWidth, fontSize=fontSize,
            textHorShift = depthSizeTextHorShift.toDouble(), textVerShift = depthSizeTextVerShift.toDouble(),
        )
        return SvgElems(
            elems = listOf(
                SvgUtils.polygon(points = corners, color = Color.lightgrey),
                SvgUtils.text(
                    begin = ex.begin.translate(ex, textHorShift.toDouble()).translate(ey, textVerShift.toDouble()),
                    text = text,
                    color = Color.black,
                    rotate = ex.deg()
                )
            ),
            boundaries = Boundaries2D.from(corners)
        ) + depthSize + widthSize
    }
}


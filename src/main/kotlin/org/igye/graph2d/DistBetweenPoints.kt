package org.igye.graph2d

import org.igye.svg.Color
import org.igye.svg.SvgElems
import org.igye.svg.SvgUtils
import org.igye.svg.SvgUtils.line
import java.math.BigDecimal
import java.math.RoundingMode

data class DistBetweenPoints(
    val base: Vector2D, val color: Color, val baseDist: Double, val arrowSize: Double, val lineWidth: Double,
    val fontSize: Double = arrowSize*1.5
) {
    fun toSvg(): SvgElems {
        val beginArrowHeight = (base.normalize()*arrowSize).swapEnds()
        val endArrowHeight = beginArrowHeight.swapEnds().endAt(base.end)
        val beginArrow = triangle(beginArrowHeight)
        val endArrow = triangle(endArrowHeight)

        val textBegin: Point = beginArrowHeight.begin + endArrowHeight
        return SvgElems(
            boundaries = Boundaries2D.fromPoints(base.begin, base.end),
            elems = listOf(
                line(begin = beginArrowHeight.begin, end = endArrowHeight.begin, color = color, strokeWidth = lineWidth),
                SvgUtils.text(
                    begin = textBegin,
                    text = BigDecimal(base.length).setScale(1, RoundingMode.HALF_UP).toString(),
                    color = color,
                    style = "font-size: $fontSize",
                    transform = "rotate(45, ${textBegin.x}, ${textBegin.y})"
                )
            )
        )
            .merge(beginArrow, endArrow)
    }

    private fun triangle(height: Vector2D): SvgElems {
        val halfWidth = height.length / 5
        val a = height.end
        val b = (height.normalize().rotate(90.deg())*halfWidth).end
        val c = (height.normalize().rotate(-90.deg())*halfWidth).end
        return SvgElems(
            boundaries = Boundaries2D.fromPoints(a,b,c),
            elems = listOf(
                SvgUtils.polygon(
                    points = listOf(a,b,c),
                    color = color,
                )
            )
        )
    }
}
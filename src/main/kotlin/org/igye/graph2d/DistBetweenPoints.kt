package org.igye.graph2d

import org.igye.svg.Color
import org.igye.svg.SvgElems
import org.igye.svg.SvgUtils
import org.igye.svg.SvgUtils.line

data class DistBetweenPoints(
    val base: Vector2D, val color: Color, val baseDist: Double, val arrowSize: Double, val lineWidth: Double
) {
    fun toSvg(): SvgElems {
        return arrows(base)
    }

    private fun arrows(base: Vector2D): SvgElems {
        val beginArrowHeight = (base.normalize()*arrowSize).swapEnds()
        val endArrowHeight = beginArrowHeight.swapEnds().endAt(base.end)
        val beginArrow = triangle(beginArrowHeight)
        val endArrow = triangle(endArrowHeight)
        val line = SvgElems(
            boundaries = Boundaries2D.fromPoints(base.begin, base.end),
            elems = listOf(
                line(begin = beginArrowHeight.begin, end = endArrowHeight.begin, color = color, strokeWidth = lineWidth)
            )
        )
        return line.merge(beginArrow, endArrow)
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
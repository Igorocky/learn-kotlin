package org.igye.graph2d

import org.igye.svg.Color
import org.igye.svg.SvgElems
import org.igye.svg.SvgUtils
import org.igye.svg.SvgUtils.line

data class Arrow(val base: Vector2D, val color: Color, val arrowSize: Double, val lineWidth: Double) {
    fun toSvg(): SvgElems {
        val beginArrowHeight = (base.normalize()*arrowSize).swapEnds()
        val endArrowHeight = beginArrowHeight.swapEnds().endAt(base.end)
        val endArrow = triangle(endArrowHeight)

        return SvgElems(
            boundaries = Boundaries2D.from(base.begin, base.end),
            elems = listOf(
                line(begin = beginArrowHeight.end, end = endArrowHeight.begin, color = color, strokeWidth = lineWidth),
            )
        )
            .merge(endArrow)
    }

    private fun triangle(height: Vector2D): SvgElems {
        val halfWidth = height.length / 5
        val a = height.end
        val b = (height.normalize().rotate(90.deg())*halfWidth).end
        val c = (height.normalize().rotate(-90.deg())*halfWidth).end
        return SvgElems(
            boundaries = Boundaries2D.from(a,b,c),
            elems = listOf(
                SvgUtils.polygon(
                    points = listOf(a,b,c),
                    color = color,
                )
            )
        )
    }
}
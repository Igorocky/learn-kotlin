package org.igye.graph2d

import org.igye.svg.Color
import org.igye.svg.SvgElems
import org.igye.svg.SvgUtils
import org.igye.svg.SvgUtils.line
import java.math.BigDecimal
import java.math.RoundingMode

data class DistBetweenPoints(
    val base: Vector2D, val color: Color, val baseDist: Double = 0.0,
    val arrowSize: Double, val lineWidth: Double, val fontSize: Double, val external: Boolean = false,
    val textHorShift: Double = arrowSize, val textVerShift: Double = arrowSize/2,
    val renderBorders: Boolean = true, val onlyLeftBorder: Boolean = false, val onlyRightBorder: Boolean = false,
) {
    fun toSvg(): SvgElems {
        val boundaryBegin: Vector2D = base.normalize().rotate(-90.deg()).times(baseDist)
        val boundaryEnd: Vector2D = base.swapEnds().normalize().rotate(90.deg()).times(baseDist)
        var result = arrowsAndSize(base = boundaryBegin.end..boundaryEnd.end)
        if (onlyLeftBorder) {
            result = result + SvgElems(
                boundaries = Boundaries2D.from(boundaryBegin),
                elems = listOf(
                    line(vector = boundaryBegin, color = color, strokeWidth = lineWidth),
                )
            )
        } else if (onlyRightBorder) {
            result = result + SvgElems(
                boundaries = Boundaries2D.from(boundaryEnd),
                elems = listOf(
                    line(vector = boundaryEnd, color = color, strokeWidth = lineWidth),
                )
            )
        } else if (renderBorders) {
            result = result + SvgElems(
                boundaries = Boundaries2D.from(boundaryBegin, boundaryEnd),
                elems = listOf(
                    line(vector = boundaryBegin, color = color, strokeWidth = lineWidth),
                    line(vector = boundaryEnd, color = color, strokeWidth = lineWidth),
                )
            )
        }
        return result
    }

    fun arrowsAndSize(base: Vector2D): SvgElems {
        var beginArrowHeight = (base.normalize()*arrowSize).swapEnds()
        var endArrowHeight = beginArrowHeight.swapEnds().endAt(base.end)
        if (external) {
            beginArrowHeight = beginArrowHeight.rotate(180.deg()).translate(beginArrowHeight*2)
            endArrowHeight = endArrowHeight.rotate(180.deg()).translate(endArrowHeight*2)
        }

        val textBegin: Point = if (!external) {
            beginArrowHeight.begin.translate(endArrowHeight, textHorShift).translate(beginArrowHeight.rotate(-90.deg()), textVerShift)
        } else {
            endArrowHeight.begin.translate(beginArrowHeight, textHorShift).translate(endArrowHeight.rotate(-90.deg()), textVerShift)
//            endArrowHeight.end.translate(endArrowHeight, -textHorShift) + (endArrowHeight.rotate(-90.deg()).normalize()*endArrowHeight.length/3)
        }
        return SvgElems(
            boundaries = Boundaries2D.from(base.begin, base.end),
            elems = listOf(
                line(begin = beginArrowHeight.begin, end = endArrowHeight.begin, color = color, strokeWidth = lineWidth),
                SvgUtils.text(
                    begin = textBegin,
                    text = BigDecimal(base.length).setScale(1, RoundingMode.HALF_UP).toString(),
                    color = color,
                    style = "font-size: $fontSize",
                    transform = "rotate(${-base.deg()}, ${textBegin.x}, ${textBegin.y})"
                ),
                line(
                    begin = beginArrowHeight.begin,
                    end = if (external) (beginArrowHeight.begin..textBegin).proj(beginArrowHeight).end else beginArrowHeight.begin,
                    color = color,
                    strokeWidth = lineWidth
                ),
            )
        )
            .merge(
                triangle(beginArrowHeight),
                triangle(endArrowHeight)
            )
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
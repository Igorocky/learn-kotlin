package org.igye.graph2d

import java.lang.Math.PI

@JvmInline
value class Deg(val doubleValue: Double) {
    fun toRad(): Rad = Rad(doubleValue/180.0 * PI)
    operator fun unaryMinus(): Deg = Deg(-doubleValue)
}
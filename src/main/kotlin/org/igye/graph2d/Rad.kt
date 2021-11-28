package org.igye.graph2d

import java.lang.Math.PI

@JvmInline
value class Rad(val doubleValue: Double) {
    fun toDeg(): Deg = Deg(doubleValue/ PI * 180.0)
    operator fun unaryMinus(): Rad = Rad(-doubleValue)
}
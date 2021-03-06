package org.igye.graph2d

import java.lang.Math.PI

@JvmInline
value class Deg(val doubleValue: Double) {
    fun toRad(): Rad = Rad(doubleValue/180.0 * PI)
    operator fun unaryMinus(): Deg = Deg(-doubleValue)
    operator fun minus(deg: Deg): Deg = Deg(doubleValue - deg.doubleValue)
    operator fun plus(deg: Deg): Deg = Deg(doubleValue + deg.doubleValue)
    override fun toString(): String {
        return doubleValue.toString()
    }
}

inline fun Double.deg(): Deg = Deg(this)
inline fun Int.deg(): Deg = Deg(this.toDouble())

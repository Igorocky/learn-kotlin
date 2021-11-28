package org.igye.svg

@JvmInline
value class Color(val strValue: String) {
    companion object {
        val black = Color("black")
        val red = Color("red")
    }
}
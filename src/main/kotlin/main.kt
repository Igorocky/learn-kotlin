fun main(args: Array<String>) {
    val obj = object {
        val str = "ssdgsdfsdg"
    }
    println(strLen(str = obj.str))
}

fun strLen(str: String) = str.length
package pr1.control

// Контрольное задание 7. Разворот строки

/**
 * Возвращает строку с символами в обратном порядке.
 * Идём с конца по кодовым точкам, а не по char: эмодзи занимает два char (суррогатную пару),
 * и посимвольный разворот поменял бы их местами, испортив символ.
 */
fun reverseString(text: String): String {
    val result = StringBuilder(text.length)
    var index = text.length
    while (index > 0) {
        val codePoint = text.codePointBefore(index)
        result.appendCodePoint(codePoint)
        index -= Character.charCount(codePoint)
    }
    return result.toString()
}

fun demoStringReverse() {
    for (text in listOf("Kotlin", "А роза упала на лапу Азора", "Привет 😀!", "")) {
        println("\"$text\" -> \"${reverseString(text)}\"")
    }
}

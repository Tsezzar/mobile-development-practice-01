package pr1.control

import kotlin.math.abs

// Контрольное задание 4. Сумма цифр числа

/** Сумма цифр целого числа; знак числа не учитывается. */
fun digitSum(number: Long): Int {
    var rest = number
    var sum = 0
    // do-while: тело выполняется хотя бы раз, поэтому для 0 результат тоже 0
    do {
        // Берём модуль остатка, а не модуль всего числа:
        // abs(Long.MIN_VALUE) переполняется и остаётся отрицательным
        sum += abs((rest % 10).toInt())
        rest /= 10
    } while (rest != 0L)
    return sum
}

fun digitSum(number: Int): Int = digitSum(number.toLong())

fun demoDigitSum() {
    for (n in listOf(12345, 0, -987, Int.MIN_VALUE)) {
        println("$n -> ${digitSum(n)}")
    }
}

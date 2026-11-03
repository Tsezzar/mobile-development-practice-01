package pr1.control

// Контрольное задание 8. Проверка на простое число

/** Простое число — натуральное число больше 1, делящееся только на 1 и на себя. */
fun isPrime(n: Long): Boolean {
    if (n < 2) return false
    if (n < 4) return true          // 2 и 3
    if (n % 2 == 0L) return false
    var divisor = 3L
    // Делители достаточно проверять до √n. Условие divisor <= n / divisor
    // не переполняется, в отличие от divisor * divisor <= n
    while (divisor <= n / divisor) {
        if (n % divisor == 0L) return false
        divisor += 2
    }
    return true
}

fun isPrime(n: Int): Boolean = isPrime(n.toLong())

fun demoPrimes() {
    for (n in listOf(-7, 0, 1, 2, 17, 21, 97, 2_147_483_647)) {
        println("$n -> ${if (isPrime(n)) "простое" else "не простое"}")
    }
}

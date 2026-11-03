package pr1.control

// Контрольное задание 3. Фильтрация списка

/** Возвращает только чётные элементы (для отрицательных чисел тоже: -4 % 2 == 0). */
fun evenNumbers(numbers: List<Int>): List<Int> = numbers.filter { it % 2 == 0 }

fun demoEvenFilter() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, -7, -8, 0)
    println("$numbers -> ${evenNumbers(numbers)}")
    println("[] -> ${evenNumbers(emptyList())}")
}

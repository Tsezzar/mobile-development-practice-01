package pr1.control

// Контрольное задание 6. Поиск дубликатов

/** Элементы, встречающиеся в списке более одного раза, в порядке первого появления. */
fun <T> findDuplicates(items: List<T>): List<T> =
    items
        .groupingBy { it }
        .eachCount()               // элемент -> сколько раз встретился (порядок первого появления сохраняется)
        .filterValues { it > 1 }
        .keys
        .toList()

fun demoDuplicates() {
    val numbers = listOf(3, 1, 3, 2, 5, 1, 3, 4)
    println("$numbers -> ${findDuplicates(numbers)}")
    val names = listOf("Анна", "Иван", "Анна", "Олег")
    println("$names -> ${findDuplicates(names)}")
    println("[] -> ${findDuplicates(emptyList<Int>())}")
}

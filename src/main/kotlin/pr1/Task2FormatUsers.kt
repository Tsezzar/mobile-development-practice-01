package pr1

import java.text.Collator
import java.util.Locale

// Задача 2. Обработка списка пользователей

/** В результат попадают пользователи строго старше этого возраста. */
private const val AGE_THRESHOLD = 30

private val RUSSIAN: Locale = Locale.forLanguageTag("ru-RU")

/**
 * Готовит список к отображению цепочкой функций высшего порядка:
 * filter (старше 30 лет) → sortedBy (по имени по алфавиту) → map (строка «Имя (email)»).
 */
fun formatUsers(users: List<User>): List<String> {
    // Collator сравнивает строки по правилам русского алфавита. Обычное сравнение строк идёт
    // по кодам символов: «Ёлкин» оказался бы раньше «Анны», а имя со строчной буквы — после всех
    // имён с заглавной. CollationKey реализует Comparable, поэтому подходит для sortedBy.
    val collator = Collator.getInstance(RUSSIAN)
    return users
        .filter { it.age > AGE_THRESHOLD }
        .sortedBy { collator.getCollationKey(it.name) }
        .map { "${it.name} (${it.email})" }
}

fun demoTask2() {
    val users = listOf(
        User("Сергей", "sergey@mail.ru", 42),
        User("Анна", "anna@yandex.ru", 35),
        User("Борис", "boris@gmail.com", 28),
        User("Ёлкин", "elkin@mail.ru", 51),
        User("Вера", "vera@mail.ru", 30),     // ровно 30 — не «старше 30», в результат не попадёт
        User("Дмитрий", "dmitry@list.ru", 33),
    )
    println("Исходный список:")
    users.forEach { println("  $it") }
    println("Результат formatUsers (старше 30, по алфавиту):")
    formatUsers(users).forEach { println("  $it") }
    println("Пустой список -> ${formatUsers(emptyList())}")
}

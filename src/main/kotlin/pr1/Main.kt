package pr1

import pr1.control.demoDigitSum
import pr1.control.demoDuplicates
import pr1.control.demoEvenFilter
import pr1.control.demoPasswordGenerator
import pr1.control.demoPasswordValidation
import pr1.control.demoPhoneFormatter
import pr1.control.demoPrimes
import pr1.control.demoStringReverse
import pr1.control.demoTemperatureConverter

/** Запускает демонстрации всех задач практической работы №1 по порядку. */
fun main() {
    section("Задача 1. Валидация пользовательских данных", ::demoTask1)
    section("Задача 2. Обработка списка пользователей", ::demoTask2)
    section("Задача 3. Расчёт дней до дня рождения", ::demoTask3)
    section("Задача 4. Учёт личных расходов", ::demoTask4)
    section("Контрольное задание 1. Валидация пароля", ::demoPasswordValidation)
    section("Контрольное задание 2. Форматирование номера телефона", ::demoPhoneFormatter)
    section("Контрольное задание 3. Фильтрация списка", ::demoEvenFilter)
    section("Контрольное задание 4. Сумма цифр числа", ::demoDigitSum)
    section("Контрольное задание 5. Генератор пароля", ::demoPasswordGenerator)
    section("Контрольное задание 6. Поиск дубликатов", ::demoDuplicates)
    section("Контрольное задание 7. Разворот строки", ::demoStringReverse)
    section("Контрольное задание 8. Проверка на простое число", ::demoPrimes)
    section("Контрольное задание 9. Конвертер температуры", ::demoTemperatureConverter)
}

/** Функция высшего порядка: печатает заголовок и выполняет переданную демонстрацию. */
private fun section(title: String, demo: () -> Unit) {
    println("=== $title ===")
    demo()
    println()
}

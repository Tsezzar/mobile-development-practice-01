package pr1.control

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ControlTasksTest {

    // 1. Валидация пароля
    @Test
    fun `валидация пароля`() {
        assertTrue(isValidPassword("Qwerty1!"))
        assertTrue(isValidPassword("Пароль2026#"))
        assertEquals(listOf("нет заглавной буквы", "нет специального символа"), passwordProblems("qwerty12"))
        assertFalse(isValidPassword("Short1!"))      // 7 символов
        assertFalse(isValidPassword("Qwerty 12"))    // пробел — не спецсимвол
        assertEquals(4, passwordProblems("").size)
    }

    // 2. Форматирование номера телефона
    @Test
    fun `форматирование номера телефона`() {
        val expected = "+7 (912) 345-67-89"
        assertEquals(expected, formatPhoneNumber("89123456789"))
        assertEquals(expected, formatPhoneNumber("79123456789"))
        assertEquals(expected, formatPhoneNumber("+7 912 345-67-89"))
        assertEquals(expected, formatPhoneNumber(" 8 (912) 345-67-89 "))
        assertNull(formatPhoneNumber("8912345678"))     // 10 цифр
        assertNull(formatPhoneNumber("891234567890"))   // 12 цифр
        assertNull(formatPhoneNumber("99123456789"))    // код страны не 7/8
        assertNull(formatPhoneNumber("+89123456789"))   // после + только 7
        assertNull(formatPhoneNumber("8912abc6789"))
        assertNull(formatPhoneNumber(""))
    }

    // 3. Фильтрация списка
    @Test
    fun `чётные элементы`() {
        assertEquals(listOf(2, 4, -8, 0), evenNumbers(listOf(1, 2, 3, 4, -7, -8, 0)))
        assertTrue(evenNumbers(emptyList()).isEmpty())
        assertTrue(evenNumbers(listOf(1, 3, 5)).isEmpty())
    }

    // 4. Сумма цифр числа
    @Test
    fun `сумма цифр`() {
        assertEquals(15, digitSum(12345))
        assertEquals(0, digitSum(0))
        assertEquals(24, digitSum(-987))
        assertEquals(47, digitSum(Int.MIN_VALUE))   // -2147483648
        assertEquals(89, digitSum(Long.MIN_VALUE))  // -9223372036854775808
    }

    // 5. Генератор пароля
    @Test
    fun `сгенерированный пароль нужной длины и проходит проверку из задания 1`() {
        repeat(200) {
            val password = generatePassword(8)
            assertEquals(8, password.length)
            assertTrue(isValidPassword(password), "пароль $password")
        }
        assertEquals(4, generatePassword(4).length)
    }

    @Test
    fun `слишком короткая длина пароля отклоняется`() {
        assertFailsWith<IllegalArgumentException> { generatePassword(3) }
        assertFailsWith<IllegalArgumentException> { generatePassword(-1) }
    }

    // 6. Поиск дубликатов
    @Test
    fun `дубликаты в порядке первого появления`() {
        assertEquals(listOf(3, 1), findDuplicates(listOf(3, 1, 3, 2, 5, 1, 3, 4)))
        assertEquals(listOf("Анна"), findDuplicates(listOf("Анна", "Иван", "Анна")))
        assertTrue(findDuplicates(listOf(1, 2, 3)).isEmpty())
        assertTrue(findDuplicates(emptyList<Int>()).isEmpty())
    }

    // 7. Разворот строки
    @Test
    fun `разворот строки`() {
        assertEquals("niltoK", reverseString("Kotlin"))
        assertEquals("", reverseString(""))
        assertEquals("!😀 тевирП", reverseString("Привет 😀!"))
        val text = "abc😀де"
        assertEquals(text, reverseString(reverseString(text)))
    }

    // 8. Проверка на простое число
    @Test
    fun `простые числа`() {
        for (n in listOf(2, 3, 5, 17, 97, 7919, Int.MAX_VALUE)) assertTrue(isPrime(n), "$n")
        for (n in listOf(-7, 0, 1, 4, 9, 21, 7917)) assertFalse(isPrime(n), "$n")
        assertTrue(isPrime(1_000_000_007L))
        assertFalse(isPrime(Long.MAX_VALUE)) // 2^63 - 1 = 7^2 · 73 · 127 · 337 · 92737 · 649657
    }

    // 9. Конвертер температуры
    @Test
    fun `конвертер температуры`() {
        assertEquals(32.0, celsiusToFahrenheit(0.0), 1e-9)
        assertEquals(212.0, celsiusToFahrenheit(100.0), 1e-9)
        assertEquals(-40.0, celsiusToFahrenheit(-40.0), 1e-9)
        assertEquals(37.0, fahrenheitToCelsius(98.6), 1e-9)
        assertEquals(25.0, fahrenheitToCelsius(celsiusToFahrenheit(25.0)), 1e-9)
    }

    @Test
    fun `температура ниже абсолютного нуля отклоняется`() {
        assertFailsWith<IllegalArgumentException> { celsiusToFahrenheit(-273.16) }
        assertFailsWith<IllegalArgumentException> { fahrenheitToCelsius(-460.0) }
        assertFailsWith<IllegalArgumentException> { celsiusToFahrenheit(Double.NaN) }
    }
}

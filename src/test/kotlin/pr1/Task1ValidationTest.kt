package pr1

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Task1ValidationTest {

    @Test
    fun `корректные данные проходят проверку`() {
        assertTrue(validateUserInput("Иван", "ivan@mail.com", 25))
        assertTrue(validateUserInput("John", "john@example.org", 99))
        assertTrue(User("Алёна", "alena@yandex.ru", 18).isValid())
    }

    @Test
    fun `имя с буквой Ё допустимо`() {
        assertTrue(isNameValid("Пётр"))
        assertTrue(isNameValid("Ёжиков"))
    }

    @Test
    fun `некорректные имена отклоняются`() {
        for (name in listOf("", "Я", "A1", "Анна-Мария", " Иван", "Иван Петров", "Jürgen")) {
            assertFalse(isNameValid(name), "имя \"$name\" должно быть отклонено")
        }
    }

    @Test
    fun `проверка email`() {
        assertTrue(isEmailValid("a@b.c"))                 // после @ ровно 3 символа и точка
        assertFalse(isEmailValid("invalid.email"))        // нет @
        assertFalse(isEmailValid("a@b@c.ru"))             // две @
        assertFalse(isEmailValid("@mail.ru"))             // пустая часть до @
        assertFalse(isEmailValid("petr@mail"))            // нет точки после @
        assertFalse(isEmailValid("a@.r"))                 // точка в начале домена
        assertFalse(isEmailValid("a@ru."))                // точка в конце домена
        assertFalse(isEmailValid("a@r."))
        assertFalse(isEmailValid(""))
    }

    @Test
    fun `границы возраста`() {
        assertFalse(isAgeValid(17))
        assertTrue(isAgeValid(18))
        assertTrue(isAgeValid(99))
        assertFalse(isAgeValid(100))
        assertFalse(isAgeValid(-1))
    }

    @Test
    fun `достаточно одной ошибки, чтобы данные были некорректны`() {
        assertFalse(validateUserInput("A1", "ivan@mail.com", 25))
        assertFalse(validateUserInput("Иван", "ivan@mail", 25))
        assertFalse(validateUserInput("Иван", "ivan@mail.com", 17))
    }
}

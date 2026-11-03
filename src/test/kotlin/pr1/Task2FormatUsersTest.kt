package pr1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Task2FormatUsersTest {

    @Test
    fun `фильтр оставляет только тех, кто старше 30, и форматирует строку`() {
        val users = listOf(
            User("Сергей", "sergey@mail.ru", 42),
            User("Борис", "boris@gmail.com", 28),
            User("Вера", "vera@mail.ru", 30),
            User("Анна", "anna@yandex.ru", 35),
        )
        assertEquals(listOf("Анна (anna@yandex.ru)", "Сергей (sergey@mail.ru)"), formatUsers(users))
    }

    @Test
    fun `сортировка по русскому алфавиту учитывает Ё и регистр`() {
        val users = listOf(
            User("Жанна", "z@mail.ru", 40),
            User("Ёлкин", "e@mail.ru", 40),
            User("борис", "b@mail.ru", 40),
            User("Анна", "a@mail.ru", 40),
            User("Вера", "v@mail.ru", 40),
        )
        val names = formatUsers(users).map { it.substringBefore(" (") }
        assertEquals(listOf("Анна", "борис", "Вера", "Ёлкин", "Жанна"), names)
    }

    @Test
    fun `пустой список и список без подходящих пользователей`() {
        assertTrue(formatUsers(emptyList()).isEmpty())
        assertTrue(formatUsers(listOf(User("Иван", "i@mail.ru", 20))).isEmpty())
    }
}

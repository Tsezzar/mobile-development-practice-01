package pr1

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Task3BirthdayTest {

    // Фиксированные даты в тестах — это входные данные сценариев, а не хардкод решения:
    // функция принимает текущую дату параметром.
    private val today = LocalDate.of(2026, 9, 27)

    @Test
    fun `день рождения сегодня - ноль дней`() {
        assertEquals(0, daysUntilBirthday("27.09.2000", today))
    }

    @Test
    fun `день рождения завтра - один день`() {
        assertEquals(1, daysUntilBirthday("28.09.1990", today))
    }

    @Test
    fun `прошедший в этом году день рождения переносится на следующий год`() {
        // 26.09.2027 минус 27.09.2026 = 364 дня (февраль 2027 не високосный)
        assertEquals(364, daysUntilBirthday("26.09.1995", today))
    }

    @Test
    fun `переход через Новый год`() {
        assertEquals(1, daysUntilBirthday("01.01.1985", LocalDate.of(2026, 12, 31)))
    }

    @Test
    fun `29 февраля в невисокосный год отмечается 28 февраля`() {
        assertEquals(1, daysUntilBirthday("29.02.2000", LocalDate.of(2027, 2, 27)))
    }

    @Test
    fun `29 февраля, уже прошедшее, переносится на 29 февраля високосного года`() {
        // от 01.03.2027 до 29.02.2028 — 365 дней; наивный plusYears(1) дал бы 28.02.2028 и 364
        assertEquals(365, daysUntilBirthday("29.02.2000", LocalDate.of(2027, 3, 1)))
    }

    @Test
    fun `некорректные строки отклоняются`() {
        for (input in listOf("31.02.2000", "2000-01-15", "15.13.2000", "1.1.2000", "", "abc")) {
            assertFailsWith<IllegalArgumentException>("строка \"$input\"") {
                daysUntilBirthday(input, today)
            }
        }
    }

    @Test
    fun `дата рождения в будущем отклоняется`() {
        assertFailsWith<IllegalArgumentException> { daysUntilBirthday("28.09.2026", today) }
    }
}

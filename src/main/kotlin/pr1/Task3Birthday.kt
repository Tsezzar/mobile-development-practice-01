package pr1

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import java.time.temporal.ChronoUnit

// Задача 3. Расчёт дней до дня рождения

/**
 * Формат «дд.ММ.гггг». В строгом режиме (STRICT) год задаётся шаблоном uuuu,
 * а несуществующие даты вроде 31.02 отклоняются, а не «подгоняются» к 29.02.
 */
private val BIRTHDAY_FORMAT: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT)

/**
 * Возвращает количество дней до ближайшего дня рождения (0 — если он сегодня).
 *
 * @param birthdayStr дата рождения в формате «дд.ММ.гггг»
 * @param today текущая дата; параметр нужен, чтобы тесты не зависели от дня запуска
 * @throws IllegalArgumentException если строка не является корректной датой или дата ещё не наступила
 */
fun daysUntilBirthday(birthdayStr: String, today: LocalDate = LocalDate.now()): Long {
    // 1. Преобразуем строку в LocalDate с помощью DateTimeFormatter
    val birthday = try {
        LocalDate.parse(birthdayStr.trim(), BIRTHDAY_FORMAT)
    } catch (e: DateTimeParseException) {
        throw IllegalArgumentException("некорректная дата «$birthdayStr», ожидается формат дд.ММ.гггг", e)
    }
    require(!birthday.isAfter(today)) { "дата рождения $birthdayStr ещё не наступила" }

    // 2. Заменяем год даты рождения на текущий (29.02 в невисокосном году становится 28.02)
    var nextBirthday = birthday.withYear(today.year)

    // 3. Если день рождения в этом году уже прошёл — переносим на следующий год.
    // Берём год от исходной даты, а не plusYears(1) от уже сдвинутой: иначе родившийся 29.02
    // получил бы 28.02 и в високосном следующем году.
    if (nextBirthday.isBefore(today)) {
        nextBirthday = birthday.withYear(today.year + 1)
    }

    // 4. Разница в днях
    return ChronoUnit.DAYS.between(today, nextBirthday)
}

fun demoTask3() {
    val today = LocalDate.now()
    val display = DateTimeFormatter.ofPattern("dd.MM.uuuu")
    println("Сегодня: ${today.format(display)}")

    // Даты вычисляются от текущего дня, поэтому пример корректен в любой день запуска
    val examples = listOf(
        "день рождения был вчера" to today.minusDays(1).minusYears(20),
        "день рождения сегодня" to today.minusYears(20),
        "день рождения через 10 дней" to today.plusDays(10).minusYears(30),
    )
    for ((label, date) in examples) {
        val text = date.format(display)
        println("$text ($label) -> дней до ближайшего: ${daysUntilBirthday(text)}")
    }

    val invalidInputs = listOf("31.02.2000", "2000-01-15", today.plusYears(1).format(display))
    for (input in invalidInputs) {
        try {
            daysUntilBirthday(input)
        } catch (e: IllegalArgumentException) {
            println("«$input» -> ошибка: ${e.message}")
        }
    }
}

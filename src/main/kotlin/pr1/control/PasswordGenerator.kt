package pr1.control

import java.security.SecureRandom

// Контрольное задание 5. Генератор пароля

private const val LOWER = "abcdefghijklmnopqrstuvwxyz"
private const val UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val DIGITS = "0123456789"
private const val SPECIAL = "!@#\$%^&*()-_=+[]{};:,.?/"
private val CHAR_GROUPS = listOf(LOWER, UPPER, DIGITS, SPECIAL)

/** SecureRandom, а не Random: пароль должен быть непредсказуемым. */
private val secureRandom = SecureRandom()

/**
 * Генерирует случайный пароль заданной длины из букв, цифр и специальных символов.
 * В пароле гарантированно есть символ каждой группы, поэтому длина не меньше числа групп.
 */
fun generatePassword(length: Int): String {
    require(length >= CHAR_GROUPS.size) { "длина пароля должна быть не меньше ${CHAR_GROUPS.size}" }
    val allChars = CHAR_GROUPS.joinToString("")
    val chars = mutableListOf<Char>()
    // По одному символу из каждой группы...
    for (group in CHAR_GROUPS) {
        chars += group[secureRandom.nextInt(group.length)]
    }
    // ...остальные — из всех групп сразу
    repeat(length - CHAR_GROUPS.size) {
        chars += allChars[secureRandom.nextInt(allChars.length)]
    }
    // Перемешиваем, чтобы обязательные символы не стояли всегда в начале
    chars.shuffle(secureRandom)
    return chars.joinToString("")
}

fun demoPasswordGenerator() {
    for (length in listOf(8, 12, 16)) {
        val password = generatePassword(length)
        println("длина $length -> $password (проверка из задания 1: ${if (isValidPassword(password)) "подходит" else "не подходит"})")
    }
    try {
        generatePassword(3)
    } catch (e: IllegalArgumentException) {
        println("длина 3 -> ошибка: ${e.message}")
    }
}

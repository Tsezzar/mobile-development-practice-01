package pr1.control

// Контрольное задание 1. Валидация пароля

private const val MIN_PASSWORD_LENGTH = 8

/** Спецсимвол — любой символ, который не буква, не цифра и не пробел. */
fun Char.isSpecialChar(): Boolean = !isLetterOrDigit() && !isWhitespace()

/** Возвращает список нарушенных требований; пустой список означает, что пароль подходит. */
fun passwordProblems(password: String): List<String> = buildList {
    if (password.length < MIN_PASSWORD_LENGTH) add("длина меньше $MIN_PASSWORD_LENGTH символов")
    if (password.none { it.isDigit() }) add("нет цифры")
    if (password.none { it.isUpperCase() }) add("нет заглавной буквы")
    if (password.none { it.isSpecialChar() }) add("нет специального символа")
}

fun isValidPassword(password: String): Boolean = passwordProblems(password).isEmpty()

fun demoPasswordValidation() {
    for (password in listOf("Qwerty1!", "Пароль2026#", "qwerty12", "Short1!", "")) {
        val problems = passwordProblems(password)
        val verdict = if (problems.isEmpty()) "подходит" else "не подходит: ${problems.joinToString()}"
        println("\"$password\" -> $verdict")
    }
}

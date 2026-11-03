package pr1

// Задача 1. Валидация пользовательских данных

/** Имя: только русские или латинские буквы (включая Ё и ё), не короче 2 символов. */
private val NAME_REGEX = Regex("^[A-Za-zА-Яа-яЁё]{2,}$")

/** Минимальная длина части email после символа @. */
private const val MIN_DOMAIN_LENGTH = 3

/** Допустимый возраст пользователя (включительно). */
private val ALLOWED_AGE = 18..99

fun isNameValid(name: String): Boolean = NAME_REGEX.matches(name)

fun isEmailValid(email: String): Boolean {
    // Ровно один символ @ и непустая часть до него
    if (email.count { it == '@' } != 1) return false
    val local = email.substringBefore('@')
    val domain = email.substringAfter('@')
    if (local.isEmpty()) return false
    // После @: длина не менее 3 символов и точка, стоящая не с краю
    // (иначе прошли бы адреса вида "a@.ru" или "a@ru.")
    return domain.length >= MIN_DOMAIN_LENGTH &&
        domain.contains('.') &&
        !domain.startsWith('.') &&
        !domain.endsWith('.')
}

fun isAgeValid(age: Int): Boolean = age in ALLOWED_AGE

/** Проверяет данные пользователя: возвращает true, только если пройдены все три проверки. */
fun validateUserInput(name: String, email: String, age: Int): Boolean =
    isNameValid(name) && isEmailValid(email) && isAgeValid(age)

/** Расширенная функция: та же проверка, вызываемая прямо у объекта User. */
fun User.isValid(): Boolean = validateUserInput(name, email, age)

fun demoTask1() {
    val testUsers = listOf(
        User("Иван", "ivan@mail.com", 25),     // корректный
        User("Алёна", "alena@yandex.ru", 18),  // корректный: буква «ё» и нижняя граница возраста
        User("A1", "invalid.email", 17),       // всё неверно: цифра в имени, нет @, возраст < 18
        User("Пётр", "petr@mail", 30),         // неверный email: после @ нет точки
        User("Anna", "anna@ya.ru", 100),       // неверный возраст: больше 99
    )
    for (user in testUsers) {
        val details = "имя: ${mark(isNameValid(user.name))}, " +
            "email: ${mark(isEmailValid(user.email))}, " +
            "возраст: ${mark(isAgeValid(user.age))}"
        val result = validateUserInput(user.name, user.email, user.age)
        println("$user -> $details => ${if (result) "КОРРЕКТНО" else "НЕКОРРЕКТНО"}")
    }
}

private fun mark(ok: Boolean) = if (ok) "ок" else "ошибка"

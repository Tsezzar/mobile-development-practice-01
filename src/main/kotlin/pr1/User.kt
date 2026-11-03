package pr1

/**
 * Данные пользователя (используются в задачах 1 и 2).
 * data class автоматически генерирует equals(), hashCode(), toString() и copy().
 */
data class User(
    val name: String,
    val email: String,
    val age: Int
)

package pr1.control

// Контрольное задание 2. Форматирование номера телефона

/** Количество цифр в российском номере вместе с кодом страны. */
private const val PHONE_DIGITS = 11

/** Разделители, которые пользователь может ввести между цифрами. */
private const val ALLOWED_SEPARATORS = " ()-"

/**
 * Приводит российский номер к виду +7 (912) 345-67-89.
 * Принимает 89123456789, 79123456789, +7 912 345-67-89 и подобные записи.
 * Возвращает null, если строка не является таким номером.
 */
fun formatPhoneNumber(raw: String): String? {
    val trimmed = raw.trim()
    val hasPlus = trimmed.startsWith('+')
    val body = if (hasPlus) trimmed.substring(1) else trimmed

    // Кроме цифр допустимы только разделители; буквы и прочие символы — ошибка
    if (body.any { it !in '0'..'9' && it !in ALLOWED_SEPARATORS }) return null
    val digits = body.filter { it in '0'..'9' }
    if (digits.length != PHONE_DIGITS) return null

    // После «+» код страны только 7; без «+» принята и «восьмёрка»
    val countryCodeOk = if (hasPlus) digits[0] == '7' else digits[0] == '7' || digits[0] == '8'
    if (!countryCodeOk) return null

    val n = digits.substring(1) // 10 цифр после кода страны
    return "+7 (${n.substring(0, 3)}) ${n.substring(3, 6)}-${n.substring(6, 8)}-${n.substring(8, 10)}"
}

fun demoPhoneFormatter() {
    for (raw in listOf("89123456789", "+7 912 345 67 89", "7(912)345-67-89", "8912345678", "+8 912 345-67-89", "8912abc6789")) {
        println("\"$raw\" -> ${formatPhoneNumber(raw) ?: "некорректный номер"}")
    }
}

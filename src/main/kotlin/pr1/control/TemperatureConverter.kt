package pr1.control

// Контрольное задание 9. Конвертер температуры

/** Абсолютный ноль: ниже него температура физически невозможна. */
private const val ABSOLUTE_ZERO_C = -273.15
private const val ABSOLUTE_ZERO_F = -459.67

fun celsiusToFahrenheit(celsius: Double): Double {
    require(celsius >= ABSOLUTE_ZERO_C) { "температура ниже абсолютного нуля: $celsius °C" }
    return celsius * 9 / 5 + 32
}

fun fahrenheitToCelsius(fahrenheit: Double): Double {
    require(fahrenheit >= ABSOLUTE_ZERO_F) { "температура ниже абсолютного нуля: $fahrenheit °F" }
    return (fahrenheit - 32) * 5 / 9
}

fun demoTemperatureConverter() {
    for (c in listOf(-40.0, 0.0, 36.6, 100.0)) {
        println("%.2f °C -> %.2f °F".format(c, celsiusToFahrenheit(c)))
    }
    for (f in listOf(-40.0, 32.0, 98.6, 212.0)) {
        println("%.2f °F -> %.2f °C".format(f, fahrenheitToCelsius(f)))
    }
    try {
        celsiusToFahrenheit(-300.0)
    } catch (e: IllegalArgumentException) {
        println("-300 °C -> ошибка: ${e.message}")
    }
}

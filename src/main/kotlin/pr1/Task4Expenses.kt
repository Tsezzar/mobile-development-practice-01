package pr1

import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Задача 4. Приложение для учёта личных расходов

/** Категории расходов: фиксированный справочник вместо произвольных строк исключает опечатки. */
enum class Category(val displayName: String) {
    FOOD("Продукты"),
    TRANSPORT("Транспорт"),
    ENTERTAINMENT("Развлечения"),
    HEALTH("Здоровье"),
}

private val RUSSIAN: Locale = Locale.forLanguageTag("ru-RU")
private val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu")

/** Сумма в рублях, например «1 250,40 руб.» (знак ₽ отсутствует в кодировке консоли Windows Cp1251). */
fun formatMoney(amount: BigDecimal): String {
    val format = NumberFormat.getNumberInstance(RUSSIAN).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return "${format.format(amount)} руб."
}

/**
 * Один расход: сумма, категория, дата.
 * Сумма хранится в BigDecimal: Double не умеет точно хранить копейки (0.1 + 0.2 != 0.3).
 */
data class Expense(
    val amount: BigDecimal,
    val category: Category,
    val date: LocalDate = LocalDate.now()
) {
    // Вторичный конструктор: сумма передаётся строкой, например из поля ввода
    constructor(amount: String, category: Category, date: LocalDate = LocalDate.now()) :
        this(BigDecimal(amount), category, date)

    init {
        require(amount > BigDecimal.ZERO) { "сумма расхода должна быть положительной: $amount" }
    }

    /** Строка с информацией о расходе. */
    fun describe(): String =
        "${date.format(DATE_FORMAT)}  ${category.displayName.padEnd(12)} ${formatMoney(amount)}"

    /** Выводит информацию о конкретном расходе. */
    fun printInfo() = println(describe())
}

/** Список всех расходов: добавление, вывод и подсчёт сумм. */
class ExpenseTracker {
    private val expenses = mutableListOf<Expense>()

    /** Копия списка только для чтения: изменить расходы можно лишь через addExpense. */
    val all: List<Expense> get() = expenses.toList()

    fun addExpense(expense: Expense) {
        expenses.add(expense)
    }

    /** Выводит все расходы. */
    fun printAll() {
        if (expenses.isEmpty()) {
            println("  Расходов пока нет")
            return
        }
        for ((index, expense) in expenses.withIndex()) {
            print("  ${index + 1}. ")
            expense.printInfo()
        }
    }

    /** Сумма расходов по каждой категории — через цикл и условие. */
    fun totalsByCategory(): Map<Category, BigDecimal> {
        val totals = linkedMapOf<Category, BigDecimal>()
        for (expense in expenses) {
            val current = totals[expense.category]
            if (current == null) {
                totals[expense.category] = expense.amount
            } else {
                totals[expense.category] = current + expense.amount
            }
        }
        return totals
    }

    /** Дополнительно: то же самое через groupBy и sumOf. */
    fun totalsByCategoryGrouped(): Map<Category, BigDecimal> =
        expenses.groupBy { it.category }.mapValues { (_, items) -> items.sumOf { it.amount } }

    /** Дополнительно: общая сумма всех расходов. */
    fun total(): BigDecimal = expenses.sumOf { it.amount }
}

private fun printTotals(totals: Map<Category, BigDecimal>) {
    for ((category, sum) in totals) {
        println("  ${category.displayName.padEnd(12)} ${formatMoney(sum)}")
    }
}

fun demoTask4() {
    val today = LocalDate.now()
    val tracker = ExpenseTracker()
    tracker.addExpense(Expense(BigDecimal("1250.40"), Category.FOOD, today.minusDays(6)))
    tracker.addExpense(Expense(BigDecimal("65.00"), Category.TRANSPORT, today.minusDays(5)))
    tracker.addExpense(Expense(BigDecimal("890.00"), Category.ENTERTAINMENT, today.minusDays(4)))
    tracker.addExpense(Expense(BigDecimal("432.10"), Category.FOOD, today.minusDays(2)))
    tracker.addExpense(Expense(BigDecimal("65.00"), Category.TRANSPORT, today.minusDays(1)))
    tracker.addExpense(Expense("1500", Category.ENTERTAINMENT)) // вторичный конструктор, дата — сегодня

    println("Все расходы:")
    tracker.printAll()
    println("Суммы по категориям (цикл):")
    printTotals(tracker.totalsByCategory())
    println("Суммы по категориям (groupBy + sumOf):")
    printTotals(tracker.totalsByCategoryGrouped())
    println("Общая сумма: ${formatMoney(tracker.total())}")

    try {
        Expense(BigDecimal("-10"), Category.FOOD, today)
    } catch (e: IllegalArgumentException) {
        println("Попытка добавить расход -10 -> ошибка: ${e.message}")
    }
    println("Пустой трекер:")
    ExpenseTracker().printAll()
}

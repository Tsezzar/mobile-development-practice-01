package pr1

import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Task4ExpensesTest {

    private val day = LocalDate.of(2026, 9, 1)

    private fun trackerWithSamples() = ExpenseTracker().apply {
        addExpense(Expense(BigDecimal("0.10"), Category.FOOD, day))
        addExpense(Expense(BigDecimal("0.20"), Category.FOOD, day))
        addExpense(Expense(BigDecimal("65.00"), Category.TRANSPORT, day))
        addExpense(Expense(BigDecimal("890.00"), Category.ENTERTAINMENT, day))
        addExpense(Expense("35.50", Category.TRANSPORT, day))
    }

    @Test
    fun `суммы по категориям считаются точно`() {
        val totals = trackerWithSamples().totalsByCategory()
        assertEquals(BigDecimal("0.30"), totals[Category.FOOD]) // с Double здесь было бы 0.30000000000000004
        assertEquals(BigDecimal("100.50"), totals[Category.TRANSPORT])
        assertEquals(BigDecimal("890.00"), totals[Category.ENTERTAINMENT])
        assertEquals(3, totals.size)
    }

    @Test
    fun `цикл и groupBy-sumOf дают одинаковый результат`() {
        val tracker = trackerWithSamples()
        assertEquals(tracker.totalsByCategory(), tracker.totalsByCategoryGrouped())
    }

    @Test
    fun `общая сумма`() {
        assertEquals(BigDecimal("990.80"), trackerWithSamples().total())
    }

    @Test
    fun `пустой трекер`() {
        val tracker = ExpenseTracker()
        assertTrue(tracker.totalsByCategory().isEmpty())
        assertEquals(0, tracker.total().compareTo(BigDecimal.ZERO))
    }

    @Test
    fun `нулевая или отрицательная сумма отклоняется`() {
        assertFailsWith<IllegalArgumentException> { Expense(BigDecimal.ZERO, Category.FOOD, day) }
        assertFailsWith<IllegalArgumentException> { Expense(BigDecimal("-1"), Category.FOOD, day) }
        assertFailsWith<NumberFormatException> { Expense("сто", Category.FOOD, day) }
    }

    @Test
    fun `список расходов снаружи не изменить`() {
        val tracker = trackerWithSamples()
        (tracker.all as MutableList<Expense>).clear() // очищается копия, а не внутренний список
        assertEquals(5, tracker.all.size)
    }
}

package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus(other: RepeatedTimeInterval): MyDate = addTimeIntervals(other.interval, other.times)

operator fun MyDate.plus(other: TimeInterval): MyDate = addTimeIntervals(other, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(times: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, times)
data class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current: MyDate = start
        override fun hasNext(): Boolean = current in this@DateRange
        override fun next(): MyDate {
            val old = current
            current = current.nextDay()
            return old
        }
    }
}

operator fun DateRange.contains(d: MyDate): Boolean = start <= d && d <= endInclusive

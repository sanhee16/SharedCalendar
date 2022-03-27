package com.example.sharedcalendar

import org.threeten.bp.LocalDate
import org.threeten.bp.Month

data class DayItem(var date: LocalDate, var isCurrentMonth: Boolean)

data class MonthItem(
    var list: MutableList<DayItem>,
    var year: Int,
    var month: Int,
    var totalWeek: Int
)
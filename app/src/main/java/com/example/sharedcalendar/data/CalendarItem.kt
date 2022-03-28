package com.example.sharedcalendar.data

import org.threeten.bp.LocalDate

data class DayItem(var date: LocalDate, var isCurrentMonth: Boolean)

data class MonthItem(
    var firstDate: LocalDate,
    var list: MutableList<DayItem>,
    var year: Int,
    var month: Int,
    var totalWeek: Int
)
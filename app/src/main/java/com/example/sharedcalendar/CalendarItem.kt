package com.example.sharedcalendar

import org.threeten.bp.LocalDate

data class CalendarItem(var date: LocalDate, var isCurrentMonth: Boolean)
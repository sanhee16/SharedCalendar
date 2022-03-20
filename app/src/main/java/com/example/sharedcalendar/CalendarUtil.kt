package com.example.sharedcalendar

import android.util.Log
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.lang.String.format
import java.util.*


// 싱글톤으로 만들어야 할 듯 ㅠㅠ'class Singleton private constructor() {

class CalendarUtil private constructor() {
    companion object {
        val DAYS_PER_WEEK = 7
        var list = mutableListOf<CalendarItem>()
        var totalWeek = 5
        var month = 0
        var year = 0

        @Volatile
        private var instance: CalendarUtil? = null

        @JvmStatic
        fun getInstance(): CalendarUtil =
            instance ?: synchronized(this) {
                instance ?: CalendarUtil().also {
                    instance = it
                }
            }

        fun makeCalendar(year: Int, month: Int) {
            // 필요한 정보
            val firstDate = LocalDate.of(year, month, 1)
            val lastDate = firstDate.withDayOfMonth(firstDate.lengthOfMonth())

            // 월 : 1 ~ 일 : 7
            val firstDayOfWeek = firstDate.dayOfWeek.value
            val lastDayOfWeek = lastDate.dayOfWeek.value

            this.year = firstDate.year
            this.month = firstDate.monthValue

            var calcDate = LocalDate.of(year, month, 1)
            while (calcDate.dayOfWeek.value != 6)
                calcDate = calcDate.plusDays(1)
            var week = 1
            while (calcDate < lastDate) {
                week++
                calcDate = calcDate.plusWeeks(1)
            }
            this.totalWeek = week

            if (firstDayOfWeek < 7) {
                for (i in firstDayOfWeek downTo 1) {
                    val calendarDate = CalendarItem(firstDate.minusDays(i.toLong()), false)
                    list.add(calendarDate)
                }
            }
            for (i in 0 until firstDate.lengthOfMonth()) {
                val calendarDate = CalendarItem(firstDate.plusDays(i.toLong()), true)
                list.add(calendarDate)
            }
            if (lastDayOfWeek != 6) {
                var left = 6 - lastDayOfWeek
                if (left < 0) left = 6
                for (i in 1..left) {
                    val calendarDate = CalendarItem(lastDate.plusDays(i.toLong()), false)
                    list.add(calendarDate)
                }
            }
        }
    }
}
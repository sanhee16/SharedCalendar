package com.sandy.sharedcalendar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sandy.sharedcalendar.utils.calendar.CalendarAdapter
import com.sandy.sharedcalendar.utils.calendar.CalendarUtil
import com.sandy.sharedcalendar.data.MonthItem
import org.threeten.bp.LocalDate

class CalendarViewModel : BaseViewModel() {
    private val _initList = MutableLiveData<MutableList<MonthItem>>()
    val initList: LiveData<MutableList<MonthItem>> = _initList
    private val list = mutableListOf<MonthItem>()


    private val LIMIT_NUM = 10
    private val MAX_POS = LIMIT_NUM * 2
    private val MIN_POS = 0
    private val LIMIT_MAX_POS = MAX_POS - 1
    private val LIMOT_MIN_POS = MIN_POS + 1

    init {
        val currentLocalDate = LocalDate.now()
        for (i in LIMIT_NUM downTo 1)
            list.add(CalendarUtil.makeCalendar(currentLocalDate.minusMonths(i.toLong())))

        list.add(CalendarUtil.makeCalendar(currentLocalDate))
        for (i in 1..LIMIT_NUM)
            list.add(CalendarUtil.makeCalendar(currentLocalDate.plusMonths(i.toLong())))

        _initList.postValue(list)
    }

    fun renewList(position: Int, adapter: CalendarAdapter) {
        if (position == LIMIT_MAX_POS) {
            list[MAX_POS].firstDate.let {
                for (i in 1..LIMIT_NUM)
                    list.add(CalendarUtil.makeCalendar(it.plusMonths(i.toLong())))
                for (i in 0 until LIMIT_NUM)
                    list.removeAt(i)
                adapter.notifyItemRangeInserted(MAX_POS, LIMIT_NUM)
                adapter.notifyItemRangeRemoved(MIN_POS, LIMIT_NUM)
            }
        } else if (position == LIMOT_MIN_POS) {
            list[MIN_POS].firstDate.let {
                for (i in 0 until LIMIT_NUM) {
                    list.removeAt(MAX_POS - i)
                }
                adapter.notifyItemRangeRemoved(
                    MAX_POS - LIMIT_NUM + 1,
                    LIMIT_NUM
                )
                for (i in LIMIT_NUM downTo 1)
                    list.add(
                        MIN_POS - i + LIMIT_NUM,
                        CalendarUtil.makeCalendar(it.minusMonths(i.toLong()))
                    )
                adapter.notifyItemRangeInserted(0, LIMIT_NUM)
            }
        }
    }


}
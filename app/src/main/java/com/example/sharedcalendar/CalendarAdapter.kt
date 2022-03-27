package com.example.sharedcalendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sharedcalendar.databinding.PagerItemBinding
import org.threeten.bp.LocalDate

// https://woochan-dev.tistory.com/27
class CalendarAdapter(private var list: MutableList<MonthItem>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val b = PagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }
    val LIMIT_MAX_POS = 5
    val LIMOT_MIN_POS = 1
    val MAX_POS = 6 // nextMonth : 4 5 6
    val MIN_POS = 0 // previousMonth : 0 1 2
    val LIMIT_NUM = 3

    // 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.b.calendarItem.makeCalendar(list[position])
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int = list.size

    // https://thdev.tech/androiddev/2020/05/25/Android-RecyclerView-Adapter-Use-DataBinding/
    class ViewHolder(val b: PagerItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun onBindViewHolder(viewModel: Any?, item: Any?) {
            // data set (ex. b.data = ...)\
        }
    }

}

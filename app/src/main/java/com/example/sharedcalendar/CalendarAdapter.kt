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

    // 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v("sandy","onBindViewHolder")
//        holder.b.calendarItem.makeCalendar()
//        holder.b.textTitle.text = list[position]
        Log.v("sandy","pos[$position] : ${list[position].year} - ${list[position].month}")
        holder.b.calendarItem.makeCalendar(list[position])

    }

    override fun getItemCount(): Int = list.size

    // https://thdev.tech/androiddev/2020/05/25/Android-RecyclerView-Adapter-Use-DataBinding/
    class ViewHolder(val b: PagerItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun onBindViewHolder(viewModel: Any?, item: Any?) {
            // data set (ex. b.data = ...)


        }
    }

}

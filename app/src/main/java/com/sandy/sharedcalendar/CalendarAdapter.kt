package com.sandy.sharedcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandy.sharedcalendar.data.MonthItem
import com.sandy.sharedcalendar.databinding.PagerItemBinding

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
        holder.b.data = list[position]
        holder.onBindViewHolder(Unit, list[position])
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int = list.size

    // https://thdev.tech/androiddev/2020/05/25/Android-RecyclerView-Adapter-Use-DataBinding/
    class ViewHolder(val b: PagerItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun onBindViewHolder(viewModel: Any?, item: MonthItem) {
            b.calendarItem.makeCalendar(item)
        }
    }

}

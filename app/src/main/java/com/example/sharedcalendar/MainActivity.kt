package com.example.sharedcalendar

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.sharedcalendar.databinding.ActivityMainBinding
import org.threeten.bp.LocalDate
import java.util.function.LongFunction

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val list = mutableListOf<MonthItem>()
        val currentLocalDate = LocalDate.now()
        for (i in 3 downTo 1)
            list.add(CalendarUtil.makeCalendar(currentLocalDate.minusMonths(i.toLong())))

        list.add(CalendarUtil.makeCalendar(currentLocalDate))
        for (i in 1..3)
            list.add(CalendarUtil.makeCalendar(currentLocalDate.plusMonths(i.toLong())))

        val adapter = CalendarAdapter(list)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.setCurrentItem(3, false)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == adapter.LIMIT_MAX_POS) {
                    val currentDate = list[adapter.MAX_POS].firstDate
                    for (i in 1..adapter.LIMIT_NUM)
                        list.add(CalendarUtil.makeCalendar(currentDate.plusMonths(i.toLong())))
                    for (i in 0 until adapter.LIMIT_NUM)
                        list.removeAt(i)
                    adapter.notifyItemRangeInserted(adapter.MAX_POS, adapter.LIMIT_NUM)
                    adapter.notifyItemRangeRemoved(adapter.MIN_POS, adapter.LIMIT_NUM)
                } else if (position == adapter.LIMOT_MIN_POS) {
                    val currentDate = list[adapter.MIN_POS].firstDate

                    for (i in 0 until adapter.LIMIT_NUM) {
                        list.removeAt(adapter.MAX_POS - i)
                    }
                    adapter.notifyItemRangeRemoved(
                        adapter.MAX_POS - adapter.LIMIT_NUM + 1,
                        adapter.LIMIT_NUM
                    )
                    for (i in adapter.LIMIT_NUM downTo 1)
                        list.add(
                            adapter.MIN_POS - i + adapter.LIMIT_NUM,
                            CalendarUtil.makeCalendar(currentDate.minusMonths(i.toLong()))
                        )
                    adapter.notifyItemRangeInserted(0, adapter.LIMIT_NUM)
                }
            }
        })
    }
}
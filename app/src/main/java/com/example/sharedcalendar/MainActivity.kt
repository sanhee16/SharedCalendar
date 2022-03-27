package com.example.sharedcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.sharedcalendar.databinding.ActivityMainBinding
import org.threeten.bp.LocalDate

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        CalendarUtil.makeCalendar(2022, 3)
//        binding.calendar.makeCalendar()
        val localDate = LocalDate.now()

        val list = mutableListOf<MonthItem>()
        val currentLocalDate = LocalDate.now()
        for (i in 0..4) {
            list.add(CalendarUtil.makeCalendar(currentLocalDate.plusMonths(i.toLong())))
        }
        val adapter = CalendarAdapter(list)

        binding.viewPager2.adapter = adapter
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}
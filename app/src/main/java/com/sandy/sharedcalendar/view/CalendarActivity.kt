package com.sandy.sharedcalendar.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.sandy.sharedcalendar.CalendarAdapter
import com.sandy.sharedcalendar.R
import com.sandy.sharedcalendar.databinding.ActivityCalendarBinding
import com.sandy.sharedcalendar.viewModel.CalendarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarActivity : BaseActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private val vm: CalendarViewModel by viewModel()
    private lateinit var adapter: CalendarAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                vm.renewList(position, adapter)
            }
        })
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = this.vm
    }

    private fun observeLiveData() {
        vm.initList.observe(this) {
            adapter = CalendarAdapter(it)
            binding.viewPager2.adapter = adapter
            binding.viewPager2.setCurrentItem(3, false)
            binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }
}
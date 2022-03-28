package com.example.sharedcalendar.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.sharedcalendar.CalendarAdapter
import com.example.sharedcalendar.CalendarUtil
import com.example.sharedcalendar.data.MonthItem
import com.example.sharedcalendar.R
import com.example.sharedcalendar.databinding.ActivityMainBinding
import com.example.sharedcalendar.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModel()
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
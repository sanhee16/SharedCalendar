package com.sandy.sharedcalendar.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.sandy.sharedcalendar.R
import com.sandy.sharedcalendar.databinding.ActivityMainBinding
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel.Companion.LOGOUT_SUCCESS
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel.Companion.START_ACTIVITY_CREATE_ROOM
import com.sandy.sharedcalendar.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = this.vm
    }

    private fun observeLiveData() {
        vm.viewEvent.observe(this) { it ->
            when (it.getContentIfNotHandled()) {
                START_ACTIVITY_CREATE_ROOM -> {

                }
                LOGOUT_SUCCESS -> {
                    startActivity(LoginActivity::class.java)
                }
            }
        }
    }
}
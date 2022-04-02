package com.sandy.sharedcalendar.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.sandy.sharedcalendar.R
import com.sandy.sharedcalendar.databinding.ActivityCreateRoomBinding
import com.sandy.sharedcalendar.viewModel.CreateRoomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateRoomActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateRoomBinding
    private val vm: CreateRoomViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_room)
        binding.lifecycleOwner = this
        binding.vm = this.vm
    }

    private fun observeLiveData() {

    }
}
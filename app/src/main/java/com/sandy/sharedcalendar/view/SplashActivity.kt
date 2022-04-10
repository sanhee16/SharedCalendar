package com.sandy.sharedcalendar.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.sandy.sharedcalendar.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sandy.sharedcalendar.databinding.ActivitySplashBinding
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel.Companion.LOGIN_FAIL
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel.Companion.LOGIN_SUCCESS
import com.sandy.sharedcalendar.viewModel.SplashViewModel

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val vm: SplashViewModel by viewModel()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.vm = this.vm
    }

    private fun observeLiveData() {

        vm.viewEvent.observe(this) { it ->
            when (it.getContentIfNotHandled()) {
                LOGIN_SUCCESS -> {
//                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(MainActivity::class.java)
                }
                LOGIN_FAIL -> {
                    startActivity(LoginActivity::class.java)
                }
            }
        }
    }
}
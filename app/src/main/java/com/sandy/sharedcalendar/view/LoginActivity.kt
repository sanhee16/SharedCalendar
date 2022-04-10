package com.sandy.sharedcalendar.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.sandy.sharedcalendar.R
import com.sandy.sharedcalendar.databinding.ActivityLoginBinding
import com.sandy.sharedcalendar.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel.Companion.LOGIN_FAIL
import com.sandy.sharedcalendar.viewModel.common.BaseViewModel.Companion.LOGIN_SUCCESS

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val vm: LoginViewModel by viewModel()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.vm = this.vm
        vm.setContext(context = this)
    }

    private fun observeLiveData() {

        vm.viewEvent.observe(this) { it ->
            when (it.getContentIfNotHandled()) {
                LOGIN_SUCCESS -> {
//                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(MainActivity::class.java)
                }
                LOGIN_FAIL -> {
                    binding.textStatus.text = "login 실패"
                    showToast("로그인이 안됨")
                }
            }
        }
    }
}
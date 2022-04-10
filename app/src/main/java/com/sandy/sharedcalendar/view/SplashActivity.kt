package com.sandy.sharedcalendar.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sandy.sharedcalendar.R
import com.sandy.sharedcalendar.data.Test
import com.sandy.sharedcalendar.databinding.ActivityLoginBinding
import com.sandy.sharedcalendar.remote.RetrofitClass
import com.sandy.sharedcalendar.viewModel.LoginViewModel
import org.json.JSONArray
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.kakao.sdk.common.model.KakaoSdkError
import com.sandy.sharedcalendar.databinding.ActivitySplashBinding
import com.sandy.sharedcalendar.viewModel.BaseViewModel
import com.sandy.sharedcalendar.viewModel.BaseViewModel.Companion.LOGIN_FAIL
import com.sandy.sharedcalendar.viewModel.BaseViewModel.Companion.LOGIN_SUCCESS
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
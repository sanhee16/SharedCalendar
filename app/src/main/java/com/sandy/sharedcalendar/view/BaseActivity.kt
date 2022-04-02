package com.sandy.sharedcalendar.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

open class BaseActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    fun Log.v(tag: String, item: Any) = Log.v(tag, item as String)

}
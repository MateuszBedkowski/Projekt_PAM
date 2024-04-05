package com.example.projekt.ui.system

import android.os.Build
import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.R

class SystemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
//        val os = R.string.os.toString()
//        val host = R.string.host.toString()
//        val kernel = R.string.kernel.toString()
        value = "OS: ${System.getProperty("os.name")} ${System.getProperty("os.version")}\n" + "Host: ${Build.DEVICE}\n" + "Kernel: ${System.getProperty("os.arch")}\n"
    }
    val text: LiveData<String> = _text
}
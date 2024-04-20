package com.example.projekt.ui.system

import android.os.Build
import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.R

class SystemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

        var kernel = System.getProperty("os.name")
        val osVersionTemp = System.getProperty("os.version")
        val architecture = System.getProperty("os.arch")
        val device = Build.DEVICE

        var osVersion = ""

        val endIndex = osVersionTemp.indexOf('-')
        if (endIndex != -1) {
            osVersionTemp.substring(endIndex+1, endIndex+10).capitalize().also { osVersion = it }
        }

        if (endIndex != -1) {
            kernel = kernel + " " + osVersionTemp.substring(0, endIndex)
        }

        value = "OS: $osVersion\n" +
                "Kernel: $kernel\n" +
                "Host: $device\n" +
                "Architecture: $architecture\n"

    }
    val text: LiveData<String> = _text
}
package com.example.projekt.ui.system

import android.content.Context
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.R

class SystemViewModel(private val context: Context) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

        var kernel = System.getProperty("os.name")
        val osVersionTemp = System.getProperty("os.version")
        val architecture = System.getProperty("os.arch")
        val device = Build.DEVICE

        var osVersion = ""

        val endIndex = osVersionTemp.indexOf('-')
        if (endIndex != -1) {
            osVersionTemp.substring(endIndex + 1, endIndex + 10).capitalize().also { osVersion = it }
        }

        if (endIndex != -1) {
            kernel = kernel + " " + osVersionTemp.substring(0, endIndex)
        }

        val osString = context.getString(R.string.os)
        val kernelString = context.getString(R.string.kernel)
        val hostString = context.getString(R.string.host)
        val architectureString = context.getString(R.string.architecture)

        value = "$osString $osVersion\n" +
                "$kernelString $kernel\n" +
                "$hostString $device\n" +
                "$architectureString $architecture\n"
    }
    val text: LiveData<String> = _text
}

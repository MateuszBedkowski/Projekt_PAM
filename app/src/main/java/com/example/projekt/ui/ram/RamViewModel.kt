package com.example.projekt.ui.ram

import android.app.ActivityManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RamViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    fun getRamInfo(context: Context) {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val totalMemory = memoryInfo.totalMem
        val availableMemory = memoryInfo.availMem
        val usedMemory = totalMemory - availableMemory

        val ramInfoText = "RAM: ${usedMemory / (1024 * 1024 * 1024)}GB/${totalMemory / (1024 * 1024 * 1024)}GB"
        _text.value = ramInfoText
    }

    val text: LiveData<String>
        get() = _text
}

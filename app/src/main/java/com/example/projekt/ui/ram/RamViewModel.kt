package com.example.projekt.ui.cpu

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
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

        val ramInfoText = "RAM: ${usedMemory / (1024 * 1024)}MB/${totalMemory / (1024 * 1024)}MB\n\n"
        val processesText = getProcessesInfo(context)

        _text.value = ramInfoText + processesText
    }

    private fun getProcessesInfo(context: Context): String {
        val memoryInfo = Debug.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processes = activityManager.runningAppProcesses

        processes.sortByDescending { processInfo ->
            activityManager.getProcessMemoryInfo(intArrayOf(processInfo.pid))[0].totalPss
        }

        val processesText = StringBuilder("Top processes by RAM usage:\n\n")
        processes.take(10).forEachIndexed { index, processInfo ->
            val memory = activityManager.getProcessMemoryInfo(intArrayOf(processInfo.pid))[0].totalPss
            processesText.append("${index + 1}. ${processInfo.processName}: ${memory / 1024} KB\n")
        }

        return processesText.toString()
    }

    val text: LiveData<String>
        get() = _text
}

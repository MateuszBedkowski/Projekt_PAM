package com.example.projekt.ui.cpu

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log
import android.os.Process

class CpuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    private val _processesInfo = MutableLiveData<List<ProcessInfo>>()
    val text: LiveData<String> = _text
    val processesInfo: LiveData<List<ProcessInfo>> = _processesInfo

    init {
        refreshInfo()
    }

    private fun refreshInfo() {
        val cpu = getCpuInfo()
        val processes = getTopProcessesInfo(10)

        _text.value = "Model: ${cpu.model}\n" +
                "Cache Size: ${cpu.cacheSize}\n" +
                "CPU MHz: ${cpu.cpuMhz}\n" +
                "CPU Cores: ${cpu.cpuCores}\n"

        _processesInfo.value = processes

        // Add logging statements
        Log.d("CpuViewModel", "CPU Info: $cpu")
        Log.d("CpuViewModel", "Top Processes: $processes")
    }

    private fun getCpuInfo(): CpuInfo {
        val cpuInfo = CpuInfo(null, null, null, null)
        val command = "cat /proc/cpuinfo"
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            when {
                line!!.startsWith("model") -> cpuInfo.model = line!!.substringAfter(":").trim()
                line!!.startsWith("cache size") -> cpuInfo.cacheSize = line!!.substringAfter(":").trim()
                line!!.startsWith("cpu MHz") -> cpuInfo.cpuMhz = line!!.substringAfter(":").trim()
                line!!.startsWith("processor") -> cpuInfo.cpuCores = cpuInfo.cpuCores?.plus(1) ?: 1
            }
        }
        reader.close()
        return cpuInfo
    }
    

    private fun getTopProcessesInfo(count: Int): List<ProcessInfo> {
        val processList = mutableListOf<ProcessInfo>()
        val command = "top -n 1 -b"
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        var line: String?
        var countSkipped = 0
        while (reader.readLine().also { line = it } != null) {
            if (line!!.startsWith("PID")) {
                countSkipped++
                if (countSkipped == 5) break // Skip first 5 lines including the header
            } else {
                val columns = line!!.trim().split("\\s+".toRegex())
                if (columns.size >= 12) {
                    val pid = columns[0]
                    val cpuUsage = columns[8].toDoubleOrNull()
                    val appName = columns[11]

                    if (pid != null && cpuUsage != null && appName != null) {
                        processList.add(ProcessInfo(pid, appName, cpuUsage))
                    }
                }
            }
        }
        reader.close()
        return processList.take(count)
    }



    private fun getProcessesInfo(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processes = activityManager.runningAppProcesses

        processes.sortByDescending { processInfo ->
            Process.getElapsedCpuTime()
        }

        val processesText = StringBuilder("Top processes by CPU usage:\n\n")
        processes.take(10).forEachIndexed { index, processInfo ->
            val cpuTime = Process.getElapsedCpuTime()
            processesText.append("${index + 1}. ${processInfo.processName}: ${cpuTime / 1000} seconds\n")
        }

        return processesText.toString()
    }


}

data class ProcessInfo(
    val pid: String,
    val appName: String,
    val cpuUsage: Double
)

data class CpuInfo(
    var model: String?,
    var cacheSize: String?,
    var cpuMhz: String?,
    var cpuCores: Int?
)

//Working regex. Leaving here in case of any need to use it: ^\\s+(\\d+)(.+?)(\\b\\d{1,2}\\.\\d{1,2}\\b)(.+)(\\s\\S+)$
package com.example.projekt.ui.cpu

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.R
import java.io.BufferedReader
import java.io.InputStreamReader

class CpuViewModel(private val context: Context) : ViewModel() {

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

        val model = context.getString(R.string.model)
        val cacheSize = context.getString(R.string.cache_size)
        val cpuMhz = context.getString(R.string.cpu_mhz)
        val cpuCores = context.getString(R.string.cpu_cores)


        _text.value = "$model ${cpu.model}\n" +
                "$cacheSize ${cpu.cacheSize}\n" +
                "$cpuMhz ${cpu.cpuMhz}\n" +
                "$cpuCores ${cpu.cpuCores}\n"

        _processesInfo.value = processes
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
                if (countSkipped == 5) break // Skip first 5 lines including the header -> leave only list of processes
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

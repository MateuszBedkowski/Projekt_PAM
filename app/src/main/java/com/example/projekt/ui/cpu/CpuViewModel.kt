package com.example.projekt.ui.cpu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log

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
//        val processList = mutableListOf<ProcessInfo>()
//        val command = "top -b -n 1 -o +%CPU | head -n $count"
//        val process = Runtime.getRuntime().exec(command)
//        val reader = BufferedReader(InputStreamReader(process.inputStream))
//        reader.readLine() // Skip the first line (header)
//        var line: String?
//        while (reader.readLine().also { line = it } != null) {
//            val columns = line?.trim()?.split("\\s+".toRegex())
//            if (columns != null && columns.size >= 10) {
//                val processInfo = ProcessInfo(
//                    columns[0],  // PID
//                    columns[9].toDouble()  // CPU usage percentage
//                )
//                processList.add(processInfo)
//            }
//        }
//        reader.close()

        val processList = mutableListOf<ProcessInfo>(
            ProcessInfo("1234", "App 1", 5.0),
            ProcessInfo("5678","App 2", 11.0),
            ProcessInfo("91011", "App 3",4.0))

        return processList
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

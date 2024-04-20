package com.example.projekt.ui.cpu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader

class CpuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

        data class CpuInfo(
            var model: String?,
            var cacheSize: String?,
            var cpuMhz: String?,
            var cpuCores: Int?
        )

        fun getCpuInfo(): CpuInfo {
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


        val cpu = getCpuInfo();
        value = "Model: ${cpu.model}\n" +
                "Cache Size: ${cpu.cacheSize}\n" +
                "CPU MHz: ${cpu.cpuMhz}\n" +
                "CPU Cores: ${cpu.cpuCores}\n"

    }
    val text: LiveData<String> = _text
}
package com.example.projekt.ui.ram

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.R
import java.io.BufferedReader
import java.io.InputStreamReader

class RamViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    private val _processesInfo = MutableLiveData<List<RamProcessInfo>>()
    val text: LiveData<String> = _text
    val RamprocessesInfo: LiveData<List<RamProcessInfo>> = _processesInfo

    fun refreshRamInfo(context: Context) {
        val ram = getRamInfo()
        val processes = getTopRamProcessesInfo(10)

        val memoryInfo = context.getString(R.string.memory)

        _text.value = "$memoryInfo ${ram.used} / ${ram.total}"
        _processesInfo.value = processes
    }

    private fun getRamInfo(): RamInfo {
        val ramInfo = RamInfo(null, null)
        val command = "cat /proc/meminfo"
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        var totalKb: Double? = null
        var availableKb: Double? = null
        while (reader.readLine().also { line = it } != null) {
            when {
                line!!.startsWith("MemTotal") -> totalKb = line!!.substringAfter(":").trim().replace(" kB", "").toDoubleOrNull()
                line!!.startsWith("MemFree") -> availableKb = line!!.substringAfter(":").trim().replace(" kB", "").toDoubleOrNull()
            }
        }
        reader.close()
        if (totalKb != null && availableKb != null) {
            val totalMb = totalKb.div(1024)
            val availableMb = availableKb.div(1024)
            val usedMb = totalMb - availableMb
            ramInfo.total = "${totalMb.toInt()} MB"
            ramInfo.used = "${usedMb.toInt()} MB"
        }
        return ramInfo
    }

    private fun getTopRamProcessesInfo(count: Int): List<RamProcessInfo> {
        val processList = mutableListOf<RamProcessInfo>()
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
                    val ramUsage = columns[9].toDouble()
                    val appName = columns[11]

                    if (pid != null && ramUsage != null && appName != null) {
                        processList.add(RamProcessInfo(pid, appName, ramUsage))
                    }
                }
            }
        }
        reader.close()
        return processList.take(count)
    }
}

data class RamProcessInfo(
    val pid: String,
    val appName: String,
    val ramUsage: Double
)

data class RamInfo(
    var total: String?,
    var used: String?
)

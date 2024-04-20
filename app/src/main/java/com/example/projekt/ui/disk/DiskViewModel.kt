package com.example.projekt.ui.disk

import android.os.Environment
import android.os.StatFs
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiskViewModel : ViewModel() {

    private val _diskSpaceInfo = MutableLiveData<String>()

    val diskSpaceInfo: LiveData<String>
        get() = _diskSpaceInfo

    init {
        updateDiskSpaceInfo()
    }

    fun updateDiskSpaceInfo() {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpaceGB = totalBlocks * blockSize / (1024 * 1024 * 1024)
        val usedSpaceGB = (totalBlocks - availableBlocks) * blockSize / (1024 * 1024 * 1024)

        _diskSpaceInfo.value = "Disk space: $usedSpaceGB GB / $totalSpaceGB GB"
    }
}

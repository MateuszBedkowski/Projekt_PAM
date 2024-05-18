package com.example.projekt.ui.disk

import android.content.Context
import android.os.Environment
import android.os.StatFs
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.R
import java.io.File

data class FileInfo(val fileName: String, val fullPath: String, val size: Int)

class DiskViewModel : ViewModel() {

    private lateinit var _context: Context

    private val _diskSpaceInfo = MutableLiveData<String>()
    private val _fileInfoList = MutableLiveData<List<FileInfo>>()

    val diskSpaceInfo: LiveData<String> = _diskSpaceInfo
    val fileInfoList: LiveData<List<FileInfo>> = _fileInfoList

    fun setContext(context: Context) {
        _context = context
        updateDiskSpaceInfo()
        updateFileInfoList(10)
    }

    fun updateDiskSpaceInfo() {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpaceGB = totalBlocks * blockSize / (1024 * 1024 * 1024)
        val usedSpaceGB = (totalBlocks - availableBlocks) * blockSize / (1024 * 1024 * 1024)

        val diskSpace = _context.getString(R.string.disk_space)

        _diskSpaceInfo.postValue("$diskSpace $usedSpaceGB GB / $totalSpaceGB GB")
    }

    fun updateFileInfoList(count: Int) {
        val fileList = mutableListOf<FileInfo>()

        val rootDirectory = File("/storage/emulated/0")
        if (rootDirectory.exists() && rootDirectory.isDirectory) {
            val files = rootDirectory.listFiles()
            files?.let {
                val sortedFiles = it.sortedByDescending { file -> file.length() }
                sortedFiles.take(count).forEach { file ->
                    val size = file.length().toInt()
                    val fileName = file.name
                    val fullPath = file.path
                    fileList.add(FileInfo(fileName, fullPath, size))
                }
            }
        }

        _fileInfoList.postValue(fileList)
    }
}

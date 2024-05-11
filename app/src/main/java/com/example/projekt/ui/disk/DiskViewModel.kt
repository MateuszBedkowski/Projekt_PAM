package com.example.projekt.ui.disk

import android.os.Environment
import android.os.StatFs
import android.renderscript.ScriptGroup.Input
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader

data class FileInfo(val fileName: String, val fullPath: String, val size: Int)

class DiskViewModel : ViewModel() {

    private val _diskSpaceInfo = MutableLiveData<String>()
    private val _fileInfoList = MutableLiveData<List<FileInfo>>()

    val diskSpaceInfo: LiveData<String> = _diskSpaceInfo
    val fileInfoList: LiveData<List<FileInfo>> = _fileInfoList

    init {
        updateDiskSpaceInfo()
        updateFileInfoList(10)
    }

//    private fun updateFileInfoList() {
//        val command = "du -ah /Android | sort -rh | head -n 10"
//        val process = Runtime.getRuntime().exec(command)
//        val reader = BufferedReader(InputStreamReader(process.inputStream))
//
//        val fileList = mutableListOf<FileInfo>()
//
//        reader.useLines { lines ->
//            lines.take(10).forEach { line ->
//                val (sizeStr, path) = line.split("\\s+".toRegex(), 2)
//                val size = sizeStr.substringBeforeLast('k').toIntOrNull() ?: return@forEach
//                val fileName = path.substringAfterLast('/')
//                fileList.add(FileInfo(fileName, path, size))
//            }
//        }
//
//        _fileInfoList.postValue(fileList)
//        Log.d("DiskViewModel", "fileList: $fileList")
//    }

    private fun updateFileInfoList(count: Int): List<FileInfo> {
        val fileList = mutableListOf<FileInfo>()
        val command = "ls -l \$pwd"
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val columns = line!!.trim().split("\\s+".toRegex())
            val size = columns[0].toInt()
            val fullPath = columns[1]
            val fileName = "test"

            fileList.add(FileInfo(fileName, fullPath, size))
        }

        reader.close()

        Log.d("DiskViewModel", "fileList: $fileList")

        return fileList.take(count)
    }


    private fun updateDiskSpaceInfo() {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpaceGB = totalBlocks * blockSize / (1024 * 1024 * 1024)
        val usedSpaceGB = (totalBlocks - availableBlocks) * blockSize / (1024 * 1024 * 1024)

        _diskSpaceInfo.postValue("Disk space: $usedSpaceGB GB / $totalSpaceGB GB")
    }
}
